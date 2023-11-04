package model;

import view.Obstaculo;

import java.util.LinkedList;
import java.util.Random;

public class ObstaculoManager implements Runnable {
    private LinkedList<Obstaculo> obstaculos;
    private int cantidadObstaculos;
    private int tiempo;
    private Random random;

    public ObstaculoManager(LinkedList<Obstaculo> obstaculos, int cantidadObstaculos,int tiempo) {
        this.obstaculos = obstaculos;
        this.cantidadObstaculos = cantidadObstaculos;
        this.random = new Random();
        this.tiempo=tiempo;
    }

    @Override
    public void run() {
        while (true) {
            if (obstaculos.size() < cantidadObstaculos) {
                int x = random.nextInt(50);
                int y = random.nextInt(50);
                Obstaculo nuevoObstaculo = new Obstaculo(x, y);
                if (!obstaculos.contains(nuevoObstaculo)) {
                    obstaculos.add(nuevoObstaculo);
                }
            }
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
