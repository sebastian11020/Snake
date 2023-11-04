package model;
import java.io.Serializable;

public class Dificultad implements Serializable {
    private int velocidadSerpiente;
    private int tiempoReaparicionFruta;
    private int cantidadObstaculos;
    private int tiempoObstaculos;
    public Dificultad() {
    }

    public Dificultad(int velocidadSerpiente, int tiempoReaparicionFruta, int cantidadObstaculos,int tiempoObstaculos) {
        this.velocidadSerpiente = velocidadSerpiente;
        this.tiempoReaparicionFruta = tiempoReaparicionFruta;
        this.cantidadObstaculos = cantidadObstaculos;
        this.tiempoObstaculos=tiempoObstaculos;
    }

    public int getVelocidadSerpiente() {
        return velocidadSerpiente;
    }

    public int getTiempoObstaculos() {
        return tiempoObstaculos;
    }
    public int getTiempoReaparicionFruta() {
        return tiempoReaparicionFruta;
    }
    public int getCantidadObstaculos() {
        return cantidadObstaculos;
    }
}

