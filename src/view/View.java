package view;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Puntaje;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class View {
    private JFrame menu = new JFrame("Snake");
    private JFrame menuDificultad = new JFrame("Selecciona la dificultad");
    private Color colorFondo = new Color(159, 226, 100);
    private Color colorFondo2 = new Color(103, 222, 236);
    private TextField nickname = new TextField(10);

    public void menu() {
        menu.setSize(400, 400);
        menu.setResizable(false);
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelmenu = new JPanel();
        JPanel panelTittle = new JPanel();
        panelTittle.setBackground(colorFondo2);
        panelmenu.setBackground(colorFondo);
        JLabel tittle = new JLabel("SNAKE");
        tittle.setForeground(Color.WHITE);
        Font font = new Font("Courier New", Font.BOLD, 24);
        tittle.setFont(font);
        JLabel nick = new JLabel("Nick name");
        JButton play = new JButton("Jugar");
        JButton verHistorial = new JButton("Ver Historial");
        verHistorial.setBackground(colorFondo2);
        play.setBackground(colorFondo2);
        panelTittle.add(tittle);
        panelmenu.add(nick);
        panelmenu.add(nickname);
        panelmenu.add(play);
        panelmenu.add(verHistorial);
        menu.getContentPane().add(BorderLayout.NORTH, panelTittle);
        menu.getContentPane().add(BorderLayout.CENTER, panelmenu);
        menu.setVisible(true);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreJugador = nickname.getText();
                if (nombreJugador.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debes ingresar un nickname antes de jugar.");
                } else {
                    menu.setVisible(false);
                    abrirMenuDificultad();
                }
            }
        });
        verHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarHistorialPuntajes();
            }
        });
    }

    private void mostrarHistorialPuntajes() {
        JFrame ventanaHistorial = new JFrame("Historial de Puntajes");
        ventanaHistorial.setSize(400, 400);
        ventanaHistorial.setResizable(false);
        ventanaHistorial.setLocationRelativeTo(null);
        ventanaHistorial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnas = {"Jugador", "Puntaje"};
        List<Puntaje> puntajes = cargarPuntajesDesdeArchivo();
        Object[][] datos = new Object[puntajes.size()][2];

        for (int i = 0; i < puntajes.size(); i++) {
            datos[i][0] = puntajes.get(i).getNombreJugador();
            datos[i][1] = puntajes.get(i).getPuntaje();
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        ventanaHistorial.add(scrollPane);

        ventanaHistorial.setVisible(true);
    }

    public List<Puntaje> cargarPuntajesDesdeArchivo() {
        try {
            File archivoPuntajes = new File("Resources/puntajes.json");
            if (!archivoPuntajes.exists()) {
                return new ArrayList<>();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Puntaje[] puntajesArray = objectMapper.readValue(archivoPuntajes, Puntaje[].class);
            return Arrays.asList(puntajesArray);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Aun no hay punatajes registrados");
            return new ArrayList<>();
        }
    }

    public void abrirMenuDificultad() {
        menuDificultad.setSize(400, 400);
        menuDificultad.setResizable(false);
        menuDificultad.setLocationRelativeTo(null);
        menuDificultad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelMenuDificultad = new JPanel();
        panelMenuDificultad.setBackground(colorFondo);

        JButton principiante = new JButton("Principiante");
        JButton medio = new JButton("Medio");
        JButton avanzado = new JButton("Avanzado");

        principiante.setBackground(colorFondo2);
        medio.setBackground(colorFondo2);
        avanzado.setBackground(colorFondo2);

        panelMenuDificultad.add(principiante);
        panelMenuDificultad.add(medio);
        panelMenuDificultad.add(avanzado);

        menuDificultad.getContentPane().add(BorderLayout.CENTER, panelMenuDificultad);
        menuDificultad.setVisible(true);

        principiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuDificultad.setVisible(false);
                Vibora vibora = new Vibora("Resources/Principiante");
                vibora.setNombre(nickname.getText());
                iniciarJuego(vibora);
            }
        });

        medio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Inicia el juego en modo medio
                menuDificultad.setVisible(false);
                Vibora vibora = new Vibora("Resources/Medio");
                vibora.setNombre(nickname.getText());
                iniciarJuego(vibora);
            }
        });
        avanzado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Inicia el juego en modo avanzado
                menuDificultad.setVisible(false);
                Vibora vibora = new Vibora("Resources/Avanzado");
                vibora.setNombre(nickname.getText());
                iniciarJuego(vibora);
            }
        });
    }

    public void iniciarJuego(Vibora vibora) {
        vibora.setSize(600, 600);
        vibora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vibora.setVisible(true);
    }
}
