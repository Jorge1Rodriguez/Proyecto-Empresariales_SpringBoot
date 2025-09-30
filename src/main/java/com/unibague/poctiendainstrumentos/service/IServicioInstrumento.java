/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unibague.poctiendainstrumentos.service;

import com.unibague.poctiendainstrumentos.dto.FiltroInstrumentoDTO;
import com.unibague.poctiendainstrumentos.model.Funda;
import com.unibague.poctiendainstrumentos.model.Guitarra;
import com.unibague.poctiendainstrumentos.model.Instrumento;
import com.unibague.poctiendainstrumentos.model.Teclado;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Interface que define las operaciones de servicio para la gestión de instrumentos musicales.
 *
 * <p>Provee métodos para crear, leer, actualizar y eliminar (CRUD) instrumentos y fundas asociadas,
 * así como para filtrar instrumentos según criterios dinámicos.</p>
 *
 * <p>Incluye operaciones específicas para listar por tipos concretos: guitarras y teclados.</p>
 *
 * <p>
 * Cada método define claramente sus contratos incluyendo posibles excepciones en implementación.
 * </p>
 *
 * <p>Esta interfaz se implementa típicamente en una clase de servicio para desacoplar la lógica de negocio
 * y facilitar pruebas o futuras extensiones.</p>
 *
 * @author Jorge
 */
public interface IServicioInstrumento {

    /**
     * Agrega un nuevo instrumento.
     *
     * @param instrumento instrumento a agregar, no puede ser nulo
     * @throws IllegalArgumentException si el instrumento es nulo o inválido
     * @throws IllegalStateException si ya existe un instrumento con el mismo código
     */
    void agregarInstrumento(Instrumento instrumento);

    /**
     * Obtiene la lista completa de instrumentos existentes.
     *
     * @return lista inmutable de instrumentos para solo lectura.
     */
    List<Instrumento> listarInstrumentos();

    /**
     * Obtiene la lista de guitarras disponibles.
     *
     * @return lista de guitarras.
     */
    List<Guitarra> listarGuitarras();

    /**
     * Obtiene la lista de teclados disponibles.
     *
     * @return lista de teclados.
     */
    List<Teclado> listarTeclados();

    /**
     * Busca un instrumento mediante su código único.
     *
     * @param codigo código alfanumérico identificador
     * @return Optional con el instrumento, o vacío si no existe
     */
    Optional<Instrumento> buscarInstrumento(String codigo);

    /**
     * Edita un instrumento existente identificado por código.
     *
     * @param codigo código del instrumento a modificar
     * @param instrumento nuevo objeto con la información actualizada
     * @throws NoSuchElementException si no existe instrumento con ese código
     */
    void editarInstrumento(String codigo, Instrumento instrumento);

    /**
     * Elimina un instrumento identificado por código.
     *
     * @param codigo código del instrumento a eliminar
     * @throws NoSuchElementException si no existe instrumento con ese código
     */
    void eliminarInstrumento(String codigo);

    /**
     * Agrega una lista de fundas a la guitarra con el código dado.
     *
     * @param codigoGuitarra código de la guitarra
     * @param fundas lista de fundas a agregar
     * @throws NoSuchElementException si no existe la guitarra
     * @throws IllegalArgumentException si el código no corresponde a una guitarra
     */
    void agregarFundas(String codigoGuitarra, List<Funda> fundas);

    /**
     * Edita una funda identificada dentro de una guitarra dada.
     *
     * @param codigoGuitarra código de la guitarra
     * @param codigoFunda código de la funda a editar
     * @param funda datos actualizados de la funda
     * @throws NoSuchElementException si no se encuentra la guitarra o funda
     * @throws IllegalArgumentException si el código no corresponde a una guitarra
     */
    void editarFunda(String codigoGuitarra, String codigoFunda, Funda funda);

    /**
     * Elimina una funda identificada dentro de una guitarra dada.
     *
     * @param codigoGuitarra código de la guitarra
     * @param codigoFunda código de la funda a eliminar
     * @throws NoSuchElementException si no se encuentra la guitarra o funda
     * @throws IllegalArgumentException si el código no corresponde a una guitarra
     */
    void eliminarFunda(String codigoGuitarra, String codigoFunda);

    /**
     * Filtra la lista de instrumentos según los parámetros especificados
     * en un DTO de filtros.
     *
     * @param filtro objeto DTO con criterios de filtrado (nombre, marca, precios, stock, tipo, sensibilidad)
     * @return lista con instrumentos que cumplen todos los criterios
     */
    List<Instrumento> filtrarInstrumentos(FiltroInstrumentoDTO filtro);
}
