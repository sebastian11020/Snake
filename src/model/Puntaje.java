package model;
import java.io.Serializable;

public class Puntaje implements Serializable {
    private String nombreJugador;
    private int puntaje;
    public Puntaje(){

    }
    public Puntaje(String nombreJugador, int puntaje) {
        this.nombreJugador = nombreJugador;
        this.puntaje = puntaje;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getPuntaje() {
        return puntaje;
    }
}

