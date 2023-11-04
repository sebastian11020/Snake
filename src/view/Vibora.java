package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import com.fasterxml.jackson.databind.*;
import model.Dificultad;
import model.ObstaculoManager;
import model.PuntajeManager;
import java.util.LinkedList;

public class Vibora extends JFrame implements Runnable, KeyListener,MenuListener {
    private LinkedList<Punto> lista = new LinkedList<Punto>();
    private LinkedList<Obstaculo> obstaculos = new LinkedList<Obstaculo>();
    private JButton volverAlMenuButton;
    private int columna, fila, colfruta, filfruta;
    private int crecimientoSerpiente = 0;
    private int crecimiento = 0;
    private int puntuacion = 0;
    private int velocidadSerpiente;
    private int tiempoReaparicionFruta;
    private int cantidadObstaculos;
    private int tiempoObstaculos;
    private boolean activo = true;
    private Direccion direccion = Direccion.DERECHA;
    private Image imagen;
    private String nombre;
    private Graphics bufferGraphics;
    private Color colorFondo = new Color(159, 226, 100);
    private ObstaculoManager obstaculoManager;
    private View menu = new View();
    private Font fuentePuntaje;
    private PuntajeManager puntajeManager;
    private Thread puntajeThread;
    private Thread hilo;
    private Thread frutaThread;
    private Thread obstaculoThread;
    private Imagenes imagenes = new Imagenes();

    public int getPuntuacion() {
        return puntuacion;
    }

    private enum Direccion {
        IZQUIERDA, DERECHA, SUBE, BAJA
    }

    class Punto {
        int x, y;

        public Punto(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Vibora(String dificultadSeleccionada) {
        fuentePuntaje = new Font("Arial", Font.PLAIN, 18);
        this.addKeyListener(this);

        lista.add(new Punto(4, 25));
        lista.add(new Punto(3, 25));
        lista.add(new Punto(2, 25));
        lista.add(new Punto(1, 25));
        columna = 4;
        fila = 25;

        Dificultad dificultad = cargarDificultad(dificultadSeleccionada);
        velocidadSerpiente = dificultad.getVelocidadSerpiente();
        hilo = new Thread(this);
        hilo.start();

        frutaThread = new Thread(() -> {
            while (activo) {
                colfruta = (int) (Math.random() * 50);
                filfruta = (int) (Math.random() * 50);
                repaint();
                try {
                    Thread.sleep(tiempoReaparicionFruta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        frutaThread.start();

        this.getContentPane().setBackground(colorFondo);

        ajustarParametrosDificultad(dificultad);

        obstaculoManager = new ObstaculoManager(obstaculos, cantidadObstaculos,tiempoObstaculos);
        obstaculoThread = new Thread(obstaculoManager);
        obstaculoThread.start();
        puntajeManager = new PuntajeManager(this);
        puntajeThread = new Thread(puntajeManager);
        puntajeThread.start();
        volverAlMenuButton = new JButton("Volver al Menú");
        volverAlMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenu();
            }
        });
        volverAlMenuButton.setVisible(false);
        this.getContentPane().add(BorderLayout.SOUTH, volverAlMenuButton);
    }

    private Dificultad cargarDificultad(String nombreDificultad) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(nombreDificultad + ".json"), Dificultad.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void run() {
        while (activo) {
            try {
                Thread.sleep(velocidadSerpiente);
                actualizarPosicion();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    private void actualizarPosicion() {
        switch (direccion) {
            case DERECHA:
                columna = (columna + 1) % 50;
                break;
            case IZQUIERDA:
                columna = (columna - 1 + 50) % 50;
                break;
            case SUBE:
                fila = (fila - 1 + 50) % 50;
                break;
            case BAJA:
                fila = (fila + 1) % 50;
                break;
        }

        repaint();
        sePisa();

        lista.addFirst(new Punto(columna, fila));

        if (!verificarComeFruta() && crecimiento == 0) {
            lista.removeLast();
        } else if (crecimiento > 0) {
            crecimiento--;
        }
    }

    private void sePisa() {
        for (Punto p : lista) {
            if (p.x == columna && p.y == fila) {
                activo = false;
                mostrarMensajePerdiste("Perdiste");
                puntajeManager.agregarPuntaje(nombre,puntuacion);
                volverAlMenuButton.setVisible(true);
            }
        }
        Obstaculo obstaculo = null;
        for (Obstaculo o : obstaculos) {
            if (columna == o.x && fila == o.y) {
                obstaculo = o;
                break;
            }
        }
        if (obstaculo != null) {
            activo = false;
            mostrarMensajePerdiste("Perdiste al chocar con un obstáculo");
            puntajeManager.agregarPuntaje(nombre, puntuacion);
            volverAlMenuButton.setVisible(true); // Muestra el botón "Volver al Menú"
        }
    }
    public void volverAlMenu() {
        this.setVisible(false);
        menu.menu();
    }
    private boolean verificarComeFruta() {
        if (columna == colfruta && fila == filfruta) {
            colfruta = (int) (Math.random() * 50);
            filfruta = (int) (Math.random() * 50);
            crecimiento = crecimientoSerpiente;
            incrementarPuntuacion(10);
            return true;
        } else {
            return false;
        }
    }

    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_RIGHT && direccion != Direccion.IZQUIERDA) {
            direccion = Direccion.DERECHA;
        }
        if (arg0.getKeyCode() == KeyEvent.VK_LEFT && direccion != Direccion.DERECHA) {
            direccion = Direccion.IZQUIERDA;
        }
        if (arg0.getKeyCode() == KeyEvent.VK_UP && direccion != Direccion.BAJA) {
            direccion = Direccion.SUBE;
        }
        if (arg0.getKeyCode() == KeyEvent.VK_DOWN && direccion != Direccion.SUBE) {
            direccion = Direccion.BAJA;
        }
    }


    public void keyReleased(KeyEvent arg0) {
    }

    public void keyTyped(KeyEvent arg0) {
    }

    public void dibujarObstaculos(Graphics g) {
        g.setColor(Color.gray);
        for (Obstaculo obstaculo : obstaculos) {
            g.fillRect(obstaculo.x * 10 + 20, obstaculo.y * 10 + 50, 10, 10);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (imagen == null) {
            imagen = createImage(this.getSize().width, this.getSize().height);
            bufferGraphics = imagen.getGraphics();
        }

        bufferGraphics.setColor(colorFondo);
        bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

        imagenes.dibujarFruta(bufferGraphics, colfruta * 10 + 20, filfruta * 10 + 50);

        for (Punto punto : lista) {
            imagenes.dibujarSerpiente(bufferGraphics, punto.x * 10 + 20, punto.y * 10 + 50);
        }

        dibujarObstaculos(bufferGraphics);

        bufferGraphics.setColor(Color.white);
        bufferGraphics.setFont(fuentePuntaje);
        bufferGraphics.drawString("Puntaje: " + getPuntuacion(), getWidth() - 150, 50);

        g.drawImage(imagen, 0, 0, this);
    }

    public synchronized void incrementarPuntuacion(int puntos) {
        puntuacion += puntos;
    }

    private void ajustarParametrosDificultad(Dificultad dificultad) {
        tiempoReaparicionFruta = dificultad.getTiempoReaparicionFruta();
        cantidadObstaculos = dificultad.getCantidadObstaculos();
        tiempoObstaculos = dificultad.getTiempoObstaculos();
        crecimientoSerpiente=dificultad.getCrecimiento();
    }

    private void mostrarMensajePerdiste(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
