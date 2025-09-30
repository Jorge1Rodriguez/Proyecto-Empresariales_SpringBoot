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
 * Representa un instrumento musical genérico en la tienda.
 *
 * <p>Esta clase es abstracta y debe ser extendida por tipos específicos de instrumentos
 * como guitarras o teclados.</p>
 *
 * <p>Contiene atributos comunes a todos los instrumentos como código, nombre,
 * marca, precio base, stock disponible y fecha de ingreso.</p>
 *
 * <p>Incluye validaciones básicas para precio y stock, así como un método abstracto
 * para calcular el valor del instrumento, el cual debe ser implementado por las subclases
 * específicas.</p>
 *
 * <p>Se utiliza {@code @JsonTypeInfo} y {@code @JsonSubTypes} para permitir la deserialización
 * correcta de subtipos concretos desde JSON, usando el campo discriminador "type".</p>
 *
 * @author Jorge
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
public abstract class Instrumento
{
    /**
     * Código único que identifica el instrumento.
     */
    private String codigo;

    /**
     * Nombre o modelo del instrumento.
     */
    private String nombre;

    /**
     * Marca fabricante del instrumento.
     */
    private String marca;

    /**
     * Precio base del instrumento.
     * No puede ser negativo.
     */
    private double precioBase;

    /**
     * Cantidad disponible en stock.
     * No puede ser negativa.
     */
    private int stock;

    /**
     * Fecha en que se ingresó el instrumento al inventario.
     */
    private LocalDate fechaIngreso;

    /**
     * Constructor con parámetros para crear un instrumento completo.
     *
     * @param codigo código único
     * @param nombre nombre o modelo
     * @param marca marca fabricante
     * @param precioBase precio base inicial, no negativo
     * @param stock cantidad disponible, no negativa
     * @param fechaIngreso fecha de ingreso al inventario
     */
    public Instrumento(String codigo, String nombre, String marca, double precioBase, int stock, LocalDate fechaIngreso){
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        setPrecioBase(precioBase);
        setStock(stock);
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * Calcula el valor específico del instrumento.
     * Debe ser implementado por las subclases.
     *
     * @param precioBase precio base del instrumento
     * @return valor calculado (puede incluir impuestos, descuentos, etc.)
     */
    public abstract double calcularValor(double precioBase);

    /**
     * Establece el precio base del instrumento.
     *
     * @param precio nuevo precio base, no puede ser negativo
     * @throws IllegalArgumentException si el precio es negativo
     */
    public void setPrecioBase(double precio){
        if (precio < 0) {
            throw new IllegalArgumentException("Precio invalido");
        } else {
            this.precioBase = precio;
        }
    }

    /**
     * Establece la cantidad en stock.
     *
     * @param stock nueva cantidad disponible, no puede ser negativa
     * @throws IllegalArgumentException si el stock es negativo
     */
    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock invalido");
        } else {
            this.stock = stock;
        }
    }

    /**
     * Representación en cadena del instrumento con sus atributos principales.
     *
     * @return cadena con atributos clave del instrumento
     */
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
