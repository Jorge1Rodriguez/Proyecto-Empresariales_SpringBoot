/*
 * Clase: ServicioInstrumento
 * Proyecto: PoC Tienda de Instrumentos
 * Paquete: com.unibague.poctiendainstrumentos.service
 *
 * Descripción:
 *   Clase de servicio que gestiona la colección de instrumentos musicales en la tienda.
 *   Implementa el patrón Singleton para asegurar que solo exista una instancia
 *   de este servicio en toda la aplicación.
 */
package com.unibague.poctiendainstrumentos.service;

import com.unibague.poctiendainstrumentos.dto.FiltroInstrumentoDTO;
import com.unibague.poctiendainstrumentos.model.Funda;
import com.unibague.poctiendainstrumentos.model.Guitarra;
import com.unibague.poctiendainstrumentos.model.Instrumento;
import com.unibague.poctiendainstrumentos.model.Teclado;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

/**
 * Servicio central para la gestión de instrumentos musicales en la tienda.
 *
 * <p>
 * Implementa un <b>patrón Singleton</b> para garantizar que solo exista una instancia
 * activa en la aplicación, facilitando la administración y persistencia de los datos.
 * </p>
 *
 * <p>
 * Permite agregar, buscar, listar, editar y eliminar instrumentos, tanto de forma general
 * como específica por tipo (guitarras, teclados). Además, gestiona las fundas asociadas
 * a guitarras permitiendo su adición, edición y eliminación, y provee métodos para filtrar
 * instrumentos según criterios flexibles.
 * </p>
 *
 * <p>
 * Es compatible con el uso en aplicaciones Spring gracias a la anotación {@code @Service}.
 * </p>
 *
 * <p>
 * Las operaciones principales incluyen:
 * <ul>
 *   <li>Creación (agregar instrumento, agregar fundas)</li>
 *   <li>Lectura (listar todos, listar por tipo, buscar por código, filtrar por criterios)</li>
 *   <li>Actualización (editar instrumento, editar funda)</li>
 *   <li>Eliminación (eliminar instrumento, eliminar funda)</li>
 * </ul>
 * </p>
 *
 * <p>
 * Incluye validaciones y excepciones detalladas para evitar estados inconsistentes
 * o que se añadan elementos duplicados o inválidos.
 * </p>
 *
 * @author Jorge
 * @version 1.0
 * @since 2025
 */
@Service
public class ServicioInstrumento implements IServicioInstrumento {

    /**
     * Lista centralizada de todos los instrumentos gestionados.
     */
    private List<Instrumento> instrumentos;

    /**
     * Instancia única del servicio (patrón Singleton).
     */
    private static ServicioInstrumento instancia;

    /**
     * Constructor privado (patrón Singleton).
     * Inicializa la lista vacía de instrumentos.
     */
    private ServicioInstrumento() {
        this.instrumentos = new ArrayList<>();
    }

    /**
     * Devuelve la instancia única del servicio.
     * Usa sincronización para ser seguro en hilos.
     *
     * @return instancia única de ServicioInstrumento
     */
    public static synchronized ServicioInstrumento getInstance() {
        if (instancia == null) {
            instancia = new ServicioInstrumento();
        }
        return instancia;
    }

    /**
     * Agrega un instrumento a la colección.
     * Garantiza que no se repita el código.
     * Si la guitarra tiene fundas, asocia la guitarra (owner) a cada funda.
     *
     * @param instrumento Instrumento a agregar.
     * @throws IllegalArgumentException si el instrumento es nulo.
     * @throws IllegalStateException si ya existe un instrumento con ese código.
     */
    @Override
    public void agregarInstrumento(Instrumento instrumento) {
        if (instrumento == null) {
            throw new IllegalArgumentException("El instrumento no puede ser nulo");
        }

        buscarInstrumento(instrumento.getCodigo())
                .ifPresent(i -> {
                    throw new IllegalStateException("Ya existe un instrumento con este código");
                });

        if(instrumento instanceof Guitarra guitarra) {
            if(guitarra.getFundas() != null) {
                for(Funda funda : guitarra.getFundas()) {
                    funda.setGuitarra(guitarra);
                }
            }
            instrumentos.add(guitarra);
        } else {
            instrumentos.add(instrumento);
        }
    }

    /**
     * Lista todos los instrumentos disponibles.
     * Devuelve una vista inmutable, solo lectura.
     *
     * @return lista inmutable de instrumentos.
     */
    @Override
    public List<Instrumento> listarInstrumentos() {
        return Collections.unmodifiableList(instrumentos);
    }

    /**
     * Lista sólo los instrumentos que son guitarras.
     *
     * @return lista de guitarras.
     */
    @Override
    public List<Guitarra> listarGuitarras() {
        List<Guitarra> guitarras = new ArrayList<>();
        for (Instrumento instrumento : instrumentos) {
            if (instrumento instanceof Guitarra) {
                guitarras.add((Guitarra) instrumento);
            }
        }
        return guitarras;
    }

    /**
     * Lista sólo los instrumentos que son teclados.
     *
     * @return lista de teclados.
     */
    @Override
    public List<Teclado> listarTeclados() {
        List<Teclado> teclados = new ArrayList<>();
        for (Instrumento instrumento : instrumentos) {
            if (instrumento instanceof Teclado) {
                teclados.add((Teclado) instrumento);
            }
        }
        return teclados;
    }

    /**
     * Busca un instrumento específico por código.
     *
     * @param codigo código único a buscar
     * @return Optional con el instrumento si existe
     */
    @Override
    public Optional<Instrumento> buscarInstrumento(String codigo) {
        return instrumentos.stream()
                .filter(i -> codigo.equalsIgnoreCase(i.getCodigo()))
                .findFirst();
    }

    /**
     * Edita y reemplaza los datos de un instrumento, identificado por código.
     *
     * @param codigo código identificador
     * @param instrumento nuevo objeto con datos a actualizar
     * @throws NoSuchElementException si no existe instrumento con ese código
     */
    @Override
    public void editarInstrumento(String codigo, Instrumento instrumento) {
        Optional<Instrumento> instrumentoAEditar = buscarInstrumento(codigo);
        if (instrumentoAEditar.isPresent()) {
            instrumentos.set(instrumentos.indexOf(instrumentoAEditar.get()), instrumento);
        } else {
            throw new NoSuchElementException("No se encontró un instrumento con el código: " + codigo);
        }
    }

    /**
     * Elimina un instrumento por código único.
     *
     * @param codigo código identificador
     * @throws NoSuchElementException si no existe instrumento con ese código
     */
    @Override
    public void eliminarInstrumento(String codigo) {
        Optional<Instrumento> instrumentoAEliminar = buscarInstrumento(codigo);
        if (instrumentoAEliminar.isPresent()) {
            instrumentos.remove(instrumentoAEliminar.get());
        } else {
            throw new NoSuchElementException("No se encontró un instrumento con el código: " + codigo);
        }
    }

    /**
     * Agrega una lista de fundas a una guitarra por su código.
     *
     * @param codigoGuitarra código de la guitarra destino
     * @param fundas lista de fundas a agregar
     * @throws NoSuchElementException si no existe guitarra
     * @throws IllegalArgumentException si el código no corresponde a una guitarra
     */
    @Override
    public void agregarFundas(String codigoGuitarra, List<Funda> fundas)
    {
        Optional<Instrumento> instrumento = buscarInstrumento(codigoGuitarra);
        if(instrumento.isPresent())
        {
            if (instrumento.get() instanceof Guitarra guitarra) {
                guitarra.agregarFundas(fundas);
            }else
            {
                throw new IllegalArgumentException("El código debe ser de una guitarra");
            }
        }else {
            throw new NoSuchElementException("No se encontró una guitarra con el código: " + codigoGuitarra);
        }
    }

    /**
     * Edita los datos de una funda asociada a una guitarra específica.
     *
     * @param codigoGuitarra código de la guitarra
     * @param codigoFunda código de la funda a editar
     * @param funda nueva funda con datos actualizados
     * @throws NoSuchElementException si no existe guitarra o funda
     * @throws IllegalArgumentException si el código no corresponde a una guitarra
     */
    @Override
    public void editarFunda(String codigoGuitarra, String codigoFunda, Funda funda) {
        Optional<Instrumento> instrumento = buscarInstrumento(codigoGuitarra);
        if(instrumento.isPresent())
        {
            if (instrumento.get() instanceof Guitarra guitarra) {
                guitarra.editarFunda(codigoFunda, funda);
            }else
            {
                throw new IllegalArgumentException("El código debe ser de una guitarra");
            }
        }else {
            throw new NoSuchElementException("No se encontró una guitarra con el código: " + codigoGuitarra);
        }
    }

    /**
     * Elimina una funda de una guitarra específica.
     *
     * @param codigoGuitarra código de la guitarra
     * @param codigoFunda código de la funda a eliminar
     * @throws NoSuchElementException si no existe guitarra o funda
     * @throws IllegalArgumentException si el código no corresponde a una guitarra
     */
    @Override
    public void eliminarFunda(String codigoGuitarra, String codigoFunda) {
        Optional<Instrumento> instrumento = buscarInstrumento(codigoGuitarra);
        if(instrumento.isPresent())
        {
            if (instrumento.get() instanceof Guitarra guitarra) {
                guitarra.eliminarFunda(codigoFunda);
            }else
            {
                throw new IllegalArgumentException("El código debe ser de una guitarra");
            }
        }else {
            throw new NoSuchElementException("No se encontró una guitarra con el código: " + codigoGuitarra);
        }
    }

    /**
     * Filtra la lista de instrumentos según los criterios proporcionados en un DTO.
     * Cada filtro es opcional y el método compone dinámicamente los predicados.
     *
     * @param filtro objeto DTO con los filtros (nombre, marca, precio, stock, tipo, sensibilidad, etc.)
     * @return lista de instrumentos que cumplen los criterios.
     */
    @Override
    public List<Instrumento> filtrarInstrumentos(FiltroInstrumentoDTO filtro) {
        Predicate<Instrumento> predicado = i -> true;

        if (filtro.getNombre() != null) {
            predicado = predicado.and(i -> i.getNombre().toLowerCase().contains(filtro.getNombre().toLowerCase()));
        }
        if (filtro.getMarca() != null) {
            predicado = predicado.and(i -> i.getMarca().equalsIgnoreCase(filtro.getMarca()));
        }
        if (filtro.getPrecioMin() != null) {
            predicado = predicado.and(i -> i.getPrecioBase() >= filtro.getPrecioMin());
        }
        if (filtro.getPrecioMax() != null) {
            predicado = predicado.and(i -> i.getPrecioBase() <= filtro.getPrecioMax());
        }
        if (filtro.getStockMin() != null) {
            predicado = predicado.and(i -> i.getStock() >= filtro.getStockMin());
        }
        if (filtro.getStockMax() != null) {
            predicado = predicado.and(i -> i.getStock() <= filtro.getStockMax());
        }
        if (filtro.getTipoGuitarra() != null) {
            predicado = predicado.and(i -> (i instanceof Guitarra guitarra) && guitarra.getTipo() == filtro.getTipoGuitarra());
        }
        if (filtro.getSensibilidad() != null) {
            predicado = predicado.and(i -> (i instanceof Teclado teclado) && teclado.getSensibilidad() == filtro.getSensibilidad());
        }

        return instrumentos.stream()
                .filter(predicado)
                .toList();
    }
}
