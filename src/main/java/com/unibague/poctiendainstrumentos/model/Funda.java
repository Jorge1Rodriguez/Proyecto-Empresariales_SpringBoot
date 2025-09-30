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
 * Representa una funda o accesorio diseñado para una guitarra específica.
 *
 * <p>Incluye atributos básicos identificativos como código, nombre y precio.
 * Cada funda está asociada a una guitarra, referencia que se ignora en la serialización JSON
 * para evitar ciclos o redundancias en la transmisión de datos.</p>
 *
 * <p>El método {@link #setPrecio(double)} valida que el precio no sea negativo,
 * lanzando una excepción en ese caso.</p>
 *
 * <p>La igualdad y el hashCode de la clase se basan únicamente en el atributo {@code codigo},
 * lo que facilita comparar fundas por su identificador único.</p>
 *
 * @author Jorge
 */
@Data
@NoArgsConstructor
public class Funda
{
    /**
     * Código único de la funda.
     */
    private String codigo;

    /**
     * Nombre descriptivo de la funda.
     */
    private String nombre;

    /**
     * Precio de la funda.
     * Debe ser un valor no negativo.
     */
    private double precio;

    /**
     * Referencia a la guitarra propietaria de la funda.
     * Se ignora durante la serialización JSON para evitar referencias circulares.
     */
    @JsonIgnore
    private Guitarra guitarra;

    /**
     * Constructor completo para crear una funda.
     *
     * @param codigo código único de la funda
     * @param nombre nombre descriptivo
     * @param precio precio de la funda, no negativo
     * @param guitarra guitarra propietaria de la funda
     */
    public Funda(String codigo, String nombre, double precio, Guitarra guitarra) {
        this.codigo = codigo;
        this.nombre = nombre;
        setPrecio(precio);
        this.guitarra = guitarra;

    }

    /**
     * Establece el precio de la funda.
     *
     * @param precio nuevo precio, debe ser no negativo
     * @throws IllegalArgumentException si el precio es negativo
     */
    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("Precio invalido");
        } else {
            this.precio = precio;
        }
    }

    /**
     * Devuelve una representación en cadena de la funda
     * incluyendo el código de la guitarra asociada (o "N/A" si no tiene).
     *
     * @return cadena descriptiva de la funda
     */
    @Override
    public String toString() {
        return "Funda{" +
                "codigo=" + codigo +
                ", nombre=" + nombre +
                ", precio=" + precio +
                ", Id guitarra=" + (guitarra != null ? guitarra.getCodigo() : "N/A") +
                '}';
    }

    /**
     * Define la igualdad entre fundas sólo por su código.
     *
     * @param o objeto a comparar
     * @return true si los códigos son iguales, falso en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funda funda = (Funda) o;
        return Objects.equals(this.codigo, funda.codigo);
    }

    /**
     * Calcula el código hash de la funda en base a su código.
     *
     * @return valor hash basado en el código
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

}
