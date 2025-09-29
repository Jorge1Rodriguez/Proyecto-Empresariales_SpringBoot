/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unibague.poctiendainstrumentos.model;

import com.unibague.poctiendainstrumentos.model.enums.SensibilidadTeclado;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 *
 * @author jorge
 */
@Data
@NoArgsConstructor
public class Teclado extends Instrumento implements IProgramable {

    private int numeroTeclas;
    private boolean digital;
    private SensibilidadTeclado sensibilidad;

    //Constructor
    public Teclado(String codigo, String nombre, String marca, double precioBase, int stock, LocalDate fechaIngreso,
            int numeroTeclas, boolean digital, SensibilidadTeclado sensibilidad) {
        super(codigo, nombre, marca, precioBase, stock, fechaIngreso);
        setNumeroTeclas(numeroTeclas);
        this.digital = digital;
        this.sensibilidad = sensibilidad;
    }

    //Metodos
    @Override
    public double calcularValor(double precioBase) {
        return precioBase + (isDigital() ? precioBase * 0.15 : 0);
    }

    @Override
    public String guardarPreset(String nombre) {
        return "Preset " + nombre +  " guardado en el teclado " + getNombre() + "con codigo " + getCodigo();
    }

    @Override
    public String cargarPreset(String nombre) {
        return "Preset " + nombre + " cargado en el teclado " + getNombre() + "con codigo " + getCodigo();
    }

    // Getters y setters
    public void setNumeroTeclas(int numeroTeclas){
        if (numeroTeclas < 0) {
            throw new IllegalArgumentException("Numero de teclas invalido");
        } else {
            this.numeroTeclas = numeroTeclas;
        }
    }


    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", numeroTeclas=" + numeroTeclas +
                ", esDigital=" + digital +
                ", sensibilidad=" + sensibilidad +
                '}';
    }
}
