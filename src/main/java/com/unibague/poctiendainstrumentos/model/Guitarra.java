/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unibague.poctiendainstrumentos.model;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author jorge
 */
@Data
public class Guitarra extends Instrumento {

    private String tipo;
    private String materialCuerpo;
    private List<Funda> fundas;

    //Constructor
    public Guitarra(String codigo, String nombre, String marca, double precio, int stock, LocalDate fechaIngreso,
            String tipo, String materialCuerpo) {
        super(codigo, nombre, marca, precio, stock, fechaIngreso);
        this.tipo = tipo;
        this.materialCuerpo = materialCuerpo;
        this.fundas = new ArrayList<>();
    }

    //metodos
    @Override
    public double calcularValor() {
        double valor = getPrecio();
        for (Funda funda : fundas) {
            valor += funda.getPrecio();
        }
        return valor;
    }

    public void agregarFunda(Funda funda) {
        if (funda == null) {
            throw new IllegalArgumentException("La funda no puede ser nula");
        }
        if (buscarFunda(funda.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una funda con este código");
        }
        fundas.add(funda);
    }

    public void eliminarFunda(String codigo) {
         Funda funda = buscarFunda(codigo);
            fundas.remove(funda);
    }

    public Funda buscarFunda(String codigo) {
        for (Funda funda : fundas) {
            if (funda.getCodigo().equalsIgnoreCase(codigo)) {
                return funda;
            }
        }
        throw new NoSuchElementException("No se encontró una funda con el código: " + codigo);
    }

    public void editarFunda(String codigo, Funda funda) {
        if (funda == null) {
            throw new IllegalArgumentException("La funda nueva no puede ser nula");
        }
        Funda fundaAEditar = buscarFunda(codigo);
        if (fundaAEditar == null) {
            throw new NoSuchElementException("No se encontró una funda con el código: " + codigo);
        }
        fundas.set(fundas.indexOf(fundaAEditar), funda);
    }

    //getters y setters
    public List<Funda> getFundas() {
        return Collections.unmodifiableList(fundas);
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", tipo=" + tipo +
                ", materialCuerpo=" + materialCuerpo +
                ", fundas=" + fundas +
                '}';
    }

}
