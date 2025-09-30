/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unibague.poctiendainstrumentos.model;

/**
 * Interfaz que define la capacidad de programar presets en instrumentos electrónicos.
 *
 * <p>Los instrumentos que implementan esta interfaz pueden almacenar configuraciones
 * personalizadas ("presets") y recuperarlas para su uso posterior.</p>
 *
 * <p>Ejemplo típico de implementación: teclados digitales.</p>
 *
 * @author Jorge
 */
public interface IProgramable {

    /**
     * Guarda un preset con el nombre dado en el instrumento.
     *
     * @param nombre nombre del preset a guardar
     * @return mensaje de confirmación o resultado de la operación
     */
    String guardarPreset(String nombre);

    /**
     * Carga un preset previamente almacenado por nombre en el instrumento.
     *
     * @param nombre nombre del preset a cargar
     * @return mensaje de confirmación o resultado de la operación
     */
    String cargarPreset(String nombre);
}
