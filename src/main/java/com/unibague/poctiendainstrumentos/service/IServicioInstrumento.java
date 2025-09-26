/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unibague.poctiendainstrumentos.service;

import com.unibague.poctiendainstrumentos.model.Guitarra;
import com.unibague.poctiendainstrumentos.model.Instrumento;
import com.unibague.poctiendainstrumentos.model.Teclado;
import java.util.List;

/**
 *
 * @author jorge
 */
public interface IServicioInstrumento {

    public void agregarInstrumento(Instrumento instrumento);

    public List<Instrumento> listarInstrumentos();

    public List<Guitarra> listarGuitarras();

    public List<Teclado> listarTeclados();

    public Instrumento buscarInstrumento(String codigo);

    public void editarInstrumento(String codigo, Instrumento instrumento);

    public void eliminarInstrumento(String codigo);

}
