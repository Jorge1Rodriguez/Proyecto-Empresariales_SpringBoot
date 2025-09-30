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
 * Representa un teclado musical, que es un tipo de instrumento.
 *
 * <p>Extiende la clase abstracta {@link Instrumento} e implementa la interfaz {@link IProgramable}
 * para añadir la capacidad de guardar y cargar preset o configuraciones.</p>
 *
 * <p>Contiene atributos específicos como el número de teclas, si es digital,
 * y la sensibilidad del teclado.</p>
 *
 * <p>Proporciona implementaciones para calcular el valor del teclado, aplicando un recargo
 * si es digital, y para la gestión de presets mediante los métodos de la interfaz.</p>
 *
 * <p>Valida que el número de teclas no sea negativo.</p>
 *
 * <p>Incluye representación en cadena que concatena atributos propios y heredados.</p>
 *
 * @author Jorge
 */
@Data
@NoArgsConstructor
public class Teclado extends Instrumento implements IProgramable
{
    /**
     * Número de teclas del teclado.
     */
    private int numeroTeclas;

    /**
     * Indica si el teclado es digital o no.
     */
    private boolean digital;

    /**
     * Indica la sensibilidad del teclado.
     */
    private SensibilidadTeclado sensibilidad;

    /**
     * Constructor con parámetros para crear un teclado completo.
     *
     * @param codigo código único
     * @param nombre nombre o modelo
     * @param marca marca fabricante
     * @param precioBase precio base
     * @param stock cantidad disponible
     * @param fechaIngreso fecha de ingreso al inventario
     * @param numeroTeclas número de teclas (no negativo)
     * @param digital verdadero si es digital
     * @param sensibilidad sensibilidad del teclado
     */
    public Teclado(String codigo, String nombre, String marca, double precioBase, int stock, LocalDate fechaIngreso,
            int numeroTeclas, boolean digital, SensibilidadTeclado sensibilidad) {
        super(codigo, nombre, marca, precioBase, stock, fechaIngreso);
        setNumeroTeclas(numeroTeclas);
        this.digital = digital;
        this.sensibilidad = sensibilidad;
    }

    /**
     * Calcula el valor del teclado considerando el precio base y
     * un recargo del 15% si es digital.
     *
     * @param precioBase precio base
     * @return precio calculado con recargo si aplica
     */
    @Override
    public double calcularValor(double precioBase) {
        return precioBase + (isDigital() ? precioBase * 0.15 : 0);
    }

    /**
     * Guarda un preset en el teclado.
     *
     * @param nombre nombre del preset
     * @return mensaje de confirmación
     */
    @Override
    public String guardarPreset(String nombre) {
        return "Preset " + nombre +  " guardado en el teclado " + getNombre() + "con codigo " + getCodigo();
    }

    /**
     * Carga un preset en el teclado.
     *
     * @param nombre nombre del preset
     * @return mensaje de confirmación
     */
    @Override
    public String cargarPreset(String nombre) {
        return "Preset " + nombre + " cargado en el teclado " + getNombre() + "con codigo " + getCodigo();
    }

    /**
     * Establece el número de teclas.
     *
     * @param numeroTeclas número de teclas, no negativo
     * @throws IllegalArgumentException si es negativo
     */
    public void setNumeroTeclas(int numeroTeclas){
        if (numeroTeclas < 0) {
            throw new IllegalArgumentException("Numero de teclas invalido");
        } else {
            this.numeroTeclas = numeroTeclas;
        }
    }

    /**
     * Representación en cadena del teclado con sus atributos propios
     * y los heredados de instrumento.
     *
     * @return cadena descriptiva del teclado
     */
    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", numeroTeclas=" + numeroTeclas +
                ", esDigital=" + digital +
                ", sensibilidad=" + sensibilidad +
                '}';
    }
}
