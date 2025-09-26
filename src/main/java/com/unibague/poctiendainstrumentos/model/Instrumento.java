/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unibague.poctiendainstrumentos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 *
 * @author jorge
 */

@Data
public abstract class Instrumento {

    private String codigo;
    private String nombre;
    private String marca;
    private double precio;
    private int stock;
    private LocalDate fechaIngreso;

    public Instrumento(String codigo, String nombre, String marca, double precio, int stock, LocalDate fechaIngreso){
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        setPrecio(precio);
        setStock(stock);
        this.fechaIngreso = fechaIngreso;
    }

    public abstract double calcularValor();

    public void setPrecio(double precio){
        if (precio < 0) {
            throw new IllegalArgumentException("Precio invalido");
        } else {
            this.precio = precio;
        }
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock invalido");
        } else {
            this.stock = stock;
        }
    }

    @Override
    public String toString() {
        String fechaStr = (fechaIngreso != null) ? fechaIngreso.toString() : "Fecha no disponible";
        return "{" +
                "codigo=" + codigo +
                ", nombre=" + nombre +
                ", marca=" + marca +
                ", precio=" + precio +
                ", stock=" + stock +
                ", fechaIngreso=" + fechaStr +
                '}';
    }


}
