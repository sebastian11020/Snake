package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class View extends Thread implements KeyListener {
    private JFrame menu = new JFrame("Snake");
    public void menu(){
        Color colorFondo = new Color(159, 226, 100);
        Color colorFondo2 = new Color(103, 222, 236);
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
        TextField nickname= new TextField(10);
        JButton play = new JButton("Jugar");
        play.setBackground(colorFondo2);
        panelTittle.add(tittle);
        panelmenu.add(nick);
        panelmenu.add(nickname);
        panelmenu.add(play);
        menu.getContentPane().add(BorderLayout.NORTH,panelTittle);
        menu.getContentPane().add(BorderLayout.CENTER,panelmenu);
        menu.setVisible(true);
    }
    public void play(){

    }
    public static void main(String[] args) {
        new View().menu();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
