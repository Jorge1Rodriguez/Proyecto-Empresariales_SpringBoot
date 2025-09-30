/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unibague.poctiendainstrumentos.model;

import com.unibague.poctiendainstrumentos.model.enums.TipoGuitarra;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

/**
 * Representa una guitarra específica, que es un tipo de instrumento musical.
 *
 * <p>Extiende la clase abstracta {@link Instrumento} para incluir detalles
 * particulares de guitarras, como el tipo (eléctrica, acústica, etc.),
 * el material del cuerpo y una lista de fundas asociadas.</p>
 *
 * <p>Incluye métodos para calcular el valor total de la guitarra sumando
 * el precio básico y el precio de las fundas asociadas, así como para gestionar
 * la lista de fundas ofreciendo operaciones para agregar, buscar, editar y eliminar fundas.</p>
 *
 * <p>La lista de fundas se expone de forma inmutable para evitar modificaciones
 * externas accidentales.</p>
 *
 * <p>Los métodos para editar y eliminar fundas lanzan excepción {@link NoSuchElementException}
 * si no se encuentra la funda con el código especificado.</p>
 *
 * @author Jorge
 */
@Data
@NoArgsConstructor
public class Guitarra extends Instrumento
{
    /**
     * Tipo de guitarra (eléctrica, acústica, clásica, etc.).
     */
    private TipoGuitarra tipo;

    /**
     * Material con el que está fabricado el cuerpo de la guitarra.
     */
    private String materialCuerpo;

    /**
     * Lista de fundas asociadas a la guitarra.
     */
    private List<Funda> fundas = new ArrayList<>();

    /**
     * Constructor con parámetros para crear una guitarra completa.
     *
     * @param codigo código único
     * @param nombre nombre o modelo
     * @param marca marca fabricante
     * @param precioBase precio base inicial
     * @param stock cantidad disponible
     * @param fechaIngreso fecha de ingreso al inventario
     * @param tipo tipo de guitarra
     * @param materialCuerpo material del cuerpo
     */
    public Guitarra(String codigo, String nombre, String marca, double precioBase, int stock, LocalDate fechaIngreso,
            TipoGuitarra tipo, String materialCuerpo) {
        super(codigo, nombre, marca, precioBase, stock, fechaIngreso);
        this.tipo = tipo;
        this.materialCuerpo = materialCuerpo;
    }

    /**
     * Calcula el valor total de la guitarra sumando el precio base
     * y el precio de todas las fundas asociadas.
     *
     * @param precioBase precio base del instrumento
     * @return precio total calculado
     */
    @Override
    public double calcularValor(double precioBase) {
        for (Funda funda : fundas) {
            precioBase += funda.getPrecio();
        }
        return precioBase;
    }

    /**
     * Agrega una lista de fundas nuevas a la guitarra,
     * asegurando que no haya duplicados.
     *
     * @param fundas lista de fundas a agregar
     */
    public void agregarFundas(List<Funda> fundas) {
        if (fundas == null) return;
        else {
            this.fundas = new ArrayList<>(
                    Stream.concat(this.fundas.stream(), fundas.stream())
                            .distinct()
                            .toList()
            );
        }
    }

    /**
     * Busca una funda en la lista según su código.
     *
     * @param codigo código de la funda a buscar
     * @return Optional con la funda si se encuentra, o vacío si no
     */
    public Optional<Funda> buscarFunda(String codigo) {
        return fundas.stream()
                .filter(i -> codigo.equalsIgnoreCase(i.getCodigo()))
                .findFirst();
    }

    /**
     * Elimina una funda de la lista buscando por código.
     * Lanza excepción si la funda no existe.
     *
     * @param codigo código de la funda a eliminar
     * @throws NoSuchElementException si no se encuentra la funda
     */
    public void eliminarFunda(String codigo) {
         Optional<Funda> funda = buscarFunda(codigo);
        if (funda.isPresent())
        {
            fundas.remove(funda.get());
        }
        else
        {
            throw new NoSuchElementException("No se encontró una funda con el código: " + codigo);
        }
    }

    /**
     * Edita (reemplaza) una funda existente buscando por código.
     * Lanza excepción si la funda no existe.
     *
     * @param codigo código de la funda a editar
     * @param funda nuevos datos de la funda
     * @throws NoSuchElementException si no se encuentra la funda
     */
    public void editarFunda(String codigo, Funda funda)
    {
        Optional<Funda> fundaAEditar = buscarFunda(codigo);
        if (fundaAEditar.isPresent())
        {
            fundas.set(fundas.indexOf(fundaAEditar.get()), funda);
        }
        else
        {
            throw new NoSuchElementException("No se encontró una funda con el código: " + codigo);
        }
    }

    /**
     * Obtiene una lista inmutable de las fundas asociadas.
     *
     * @return lista inmutable de fundas, vacía si no hay fundas
     */
    public List<Funda> getFundas() {
        return fundas == null ? Collections.emptyList() : Collections.unmodifiableList(fundas);
    }

    /**
     * Representación en cadena de la guitarra con los atributos heredados
     * y los propios adicionales.
     *
     * @return cadena que representa la guitarra
     */
    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", tipo=" + tipo +
                ", materialCuerpo=" + materialCuerpo +
                ", fundas=" + fundas +
                '}';
    }

}
