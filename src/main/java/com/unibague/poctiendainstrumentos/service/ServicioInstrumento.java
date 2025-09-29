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
 * La clase {@code ServicioInstrumento} administra un conjunto de instrumentos
 * musicales, permitiendo operaciones de agregar, listar, buscar, modificar y
 * eliminar.
 *
 * <p>
 * Esta clase implementa el patrón <b>Singleton</b>, garantizando que en el
 * sistema exista una única instancia de servicio que centraliza la gestión de
 * los instrumentos.</p>
 *
 * <p>
 * Las operaciones disponibles incluyen:
 * <ul>
 * <li>Agregar un instrumento si no existe uno con el mismo código.</li>
 * <li>Listar todos los instrumentos (solo lectura).</li>
 * <li>Listar solo guitarras o teclados usando filtrado por tipo.</li>
 * <li>Buscar un instrumento por su código único.</li>
 * <li>Editar un instrumento existente.</li>
 * <li>Eliminar un instrumento por código.</li>
 * </ul>
 *
 * <p>
 *
 *
 * @author Jorge
 * @version 1.0
 * @since 2025
 */

@Service
public class ServicioInstrumento implements IServicioInstrumento {

    /**
     * Colección de instrumentos administrados por el servicio.
     */
    private List<Instrumento> instrumentos;

    private static ServicioInstrumento instancia;

    /**
     * Constructor privado para restringir la creación de instancias
     * (Singleton). Inicializa la lista vacía de instrumentos.
     */
    private ServicioInstrumento() {
        this.instrumentos = new ArrayList<>();
    }

    public static synchronized ServicioInstrumento getInstance() {
        if (instancia == null) {
            instancia = new ServicioInstrumento();
        }
        return instancia;
    }

    /**
     * Agrega un nuevo instrumento al servicio.
     *
     * @param instrumento Instrumento a agregar.
     * @throws IllegalArgumentException Si el instrumento es {@code null} o si
     * ya existe otro con el mismo código.
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
     * Devuelve una lista inmutable con todos los instrumentos disponibles.
     *
     * @return Lista de instrumentos (solo lectura).
     */
    @Override
    public List<Instrumento> listarInstrumentos() {
        return Collections.unmodifiableList(instrumentos);
    }

    /**
     * Devuelve una lista de guitarras filtradas de la colección de
     * instrumentos.
     *
     * @return Lista de guitarras.
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
     * Devuelve una lista de teclados filtrados de la colección de instrumentos.
     *
     * @return Lista de teclados.
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
     * Busca un instrumento en la colección, identificado por su código.
     *
     * @param codigo Código único del instrumento.
     * @return El instrumento encontrado.
     */
    @Override
    public Optional<Instrumento> buscarInstrumento(String codigo) {
        return instrumentos.stream()
                .filter(i -> codigo.equalsIgnoreCase(i.getCodigo()))
                .findFirst();
    }

    /**
     * Edita (reemplaza) un instrumento existente en la colección.
     *
     * @param codigo Código del instrumento a editar.
     * @param instrumento Nuevo objeto con los datos actualizados.
     * @throws NoSuchElementException Si no existe ningún instrumento con el
     * código proporcionado.
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
     * Elimina un instrumento de la colección según su código.
     *
     * @param codigo Código del instrumento a eliminar.
     * @throws NoSuchElementException Si no existe ningún instrumento con ese
     * código.
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
