/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unibague.poctiendainstrumentos.service;

import com.unibague.poctiendainstrumentos.model.Funda;
import com.unibague.poctiendainstrumentos.model.Guitarra;
import com.unibague.poctiendainstrumentos.model.Instrumento;
import com.unibague.poctiendainstrumentos.model.Teclado;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jorge
 */
public interface IServicioInstrumento {

    void agregarInstrumento(Instrumento instrumento);

    List<Instrumento> listarInstrumentos();

    List<Guitarra> listarGuitarras();

    List<Teclado> listarTeclados();

    Optional<Instrumento> buscarInstrumento(String codigo);

    void editarInstrumento(String codigo, Instrumento instrumento);

    void eliminarInstrumento(String codigo);

    void agregarFundas(String codigoGuitarra, List<Funda> fundas);

    void editarFunda(String codigoGuitarra, String codigoFunda, Funda funda);

    void eliminarFunda(String codigoGuitarra, String CodigoFunda);

}
