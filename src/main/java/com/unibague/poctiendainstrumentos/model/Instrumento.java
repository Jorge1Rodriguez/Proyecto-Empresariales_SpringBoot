/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unibague.poctiendainstrumentos.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 *
 * @author jorge
 */

@Data
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Guitarra.class, name = "guitarra"),
        @JsonSubTypes.Type(value = Teclado.class, name = "teclado")
})
public abstract class Instrumento {

    private String codigo;
    private String nombre;
    private String marca;
    private double precioBase;
    private int stock;
    private LocalDate fechaIngreso;


    public Instrumento(String codigo, String nombre, String marca, double precioBase, int stock, LocalDate fechaIngreso){
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        setPrecioBase(precioBase);
        setStock(stock);
        this.fechaIngreso = fechaIngreso;
    }

    public abstract double calcularValor(double precioBase);

    public void setPrecioBase(double precio){
        if (precio < 0) {
            throw new IllegalArgumentException("Precio invalido");
        } else {
            this.precioBase = precio;
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
                ", precio=" + precioBase +
                ", stock=" + stock +
                ", fechaIngreso=" + fechaStr +
                '}';
    }


}
