package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Puntaje;
import view.Vibora;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PuntajeManager implements Runnable {
    private Vibora vibora;
    private List<Puntaje> puntajes;
    private ObjectMapper objectMapper;

    public PuntajeManager(Vibora vibora) {
        this.vibora = vibora;
        this.puntajes = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        cargarPuntajes();  // Carga los puntajes existentes al inicio
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                guardarPuntajes();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void agregarPuntaje(String nombreJugador, int puntaje) {
        Puntaje nuevoPuntaje = new Puntaje(nombreJugador, puntaje);
        puntajes.add(nuevoPuntaje);
        guardarPuntajes();
    }

    public List<Puntaje> getPuntajes() {
        return puntajes;
    }

    private void guardarPuntajes() {
        try {
            objectMapper.writeValue(new File("Resources/puntajes.json"), puntajes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPuntajes() {
        try {
            Puntaje[] puntajesArray = objectMapper.readValue(new File("Resources/puntajes.json"), Puntaje[].class);
            puntajes.clear();
            for (Puntaje puntaje : puntajesArray) {
                puntajes.add(puntaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



