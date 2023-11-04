package view;
import java.awt.Color;
import java.awt.Graphics;

public class Imagenes {
    public static void dibujarSerpiente(Graphics g, int x, int y) {
        g.setColor(Color.blue);
        g.fillRect(x, y, 10, 10);
    }

    public static void dibujarFruta(Graphics g, int x, int y) {
        g.setColor(Color.red);
        g.fillOval(x, y, 10, 10);
    }
}

