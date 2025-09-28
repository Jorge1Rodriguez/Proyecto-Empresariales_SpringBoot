/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unibague.poctiendainstrumentos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 *
 * @author jorge
 */
@Data
@NoArgsConstructor
public class Funda {

    private String codigo;
    private String nombre;
    private double precio;

    @JsonIgnore
    private Guitarra guitarra;

    //constructor
    public Funda(String codigo, String nombre, double precio, Guitarra guitarra) {
        this.codigo = codigo;
        this.nombre = nombre;
        setPrecio(precio);
        this.guitarra = guitarra;

    }

    //metodos
    // Getters y setters
    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("Precio invalido");
        } else {
            this.precio = precio;
        }
    }

    @Override
    public String toString() {
        return "Funda{" +
                "codigo=" + codigo +
                ", nombre=" + nombre +
                ", precio=" + precio +
                ", Id guitarra=" + (guitarra != null ? guitarra.getCodigo() : "N/A") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funda funda = (Funda) o;
        return Objects.equals(this.codigo, funda.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

}
