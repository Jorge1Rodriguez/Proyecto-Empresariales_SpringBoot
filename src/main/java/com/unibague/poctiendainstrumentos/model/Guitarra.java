/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unibague.poctiendainstrumentos.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

/**
 *
 * @author jorge
 */
@Data
@NoArgsConstructor
public class Guitarra extends Instrumento {

    private String tipo;
    private String materialCuerpo;
    private List<Funda> fundas = new ArrayList<>();

    //Constructor
    public Guitarra(String codigo, String nombre, String marca, double precioBase, int stock, LocalDate fechaIngreso,
            String tipo, String materialCuerpo) {
        super(codigo, nombre, marca, precioBase, stock, fechaIngreso);
        this.tipo = tipo;
        this.materialCuerpo = materialCuerpo;
    }

    //metodos
    @Override
    public double calcularValor(double precioBase) {
        for (Funda funda : fundas) {
            precioBase += funda.getPrecio();
        }
        return precioBase;
    }

    public void agregarFundas(List<Funda> fundas) {
        if (fundas == null) return;
        else {
            this.fundas = new ArrayList<>(
                    Stream.concat(this.fundas.stream(), fundas.stream())
                            .distinct()
                            .toList()
            );
        }
    }

    public Optional<Funda> buscarFunda(String codigo) {
        return fundas.stream()
                .filter(i -> codigo.equalsIgnoreCase(i.getCodigo()))
                .findFirst();
    }

    public void eliminarFunda(String codigo) {
         Optional<Funda> funda = buscarFunda(codigo);
        if (funda.isPresent())
        {
            fundas.remove(funda.get());
        }
        else
        {
            throw new NoSuchElementException("No se encontró una funda con el código: " + codigo);
        }
    }


    public void editarFunda(String codigo, Funda funda)
    {
        Optional<Funda> fundaAEditar = buscarFunda(codigo);
        if (fundaAEditar.isPresent())
        {
            fundas.set(fundas.indexOf(fundaAEditar.get()), funda);
        }
        else
        {
            throw new NoSuchElementException("No se encontró una funda con el código: " + codigo);
        }
    }

    //getters y setters
    public List<Funda> getFundas() {
        return fundas == null ? Collections.emptyList() : Collections.unmodifiableList(fundas);
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
