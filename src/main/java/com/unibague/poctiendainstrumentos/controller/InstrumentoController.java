package com.unibague.poctiendainstrumentos.controller;

import com.unibague.poctiendainstrumentos.dto.ApiResponse;
import com.unibague.poctiendainstrumentos.model.Funda;
import com.unibague.poctiendainstrumentos.model.Guitarra;
import com.unibague.poctiendainstrumentos.model.Instrumento;
import com.unibague.poctiendainstrumentos.model.Teclado;
import com.unibague.poctiendainstrumentos.service.IServicioInstrumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instrumentos")
public class InstrumentoController
{

    @Autowired
    private IServicioInstrumento servicioInstrumento;



    private void validarCodigo(String codigo, String campo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El " + campo + " debe existir y no estar vacío");
        }
    }

    @GetMapping(value = "/healthCheck")
    public String healthCheck()
    {
        return "Status Ok!";
    }

    @PostMapping
    public ResponseEntity<ApiResponse> agregarInstrumento(@RequestBody Instrumento instrumento)
    {
            servicioInstrumento.agregarInstrumento(instrumento);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(false, "Instrumento agregado correctamente"));
    }

    @GetMapping
    public ResponseEntity<List<Instrumento>> listarInstrumentos()
    {
        List<Instrumento> listaInstrumentos= servicioInstrumento.listarInstrumentos();
        return ResponseEntity.ok(listaInstrumentos);
    }

    @GetMapping(value = "/guitarras")
    public ResponseEntity<List<Guitarra>> listarGuitarras()
    {
        List<Guitarra> listaGuitarras = servicioInstrumento.listarGuitarras();
        return ResponseEntity.ok(listaGuitarras);

    }

    @GetMapping(value = "/teclados")
    public ResponseEntity<List<Teclado>> listarTeclados()
    {
        List<Teclado> listaTeclados = servicioInstrumento.listarTeclados();
        return ResponseEntity.ok(listaTeclados);

    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity<?> buscarInstrumento(@PathVariable("codigo") String codigo)
    {
        validarCodigo(codigo, "código del instrumento");
        Optional<Instrumento> instrumento = servicioInstrumento.buscarInstrumento(codigo);
        if (instrumento.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(true, "El instrumento no existe"));
        }
        return ResponseEntity.ok(instrumento.get());
    }

    @PutMapping(value = "/{codigo}")
    public ResponseEntity<ApiResponse> editarInstrumento(@PathVariable("codigo") String codigo,
                                                         @RequestBody Instrumento instrumentoModificado)
    {
        validarCodigo(codigo, "código del instrumento");
        servicioInstrumento.editarInstrumento(codigo, instrumentoModificado);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(false, "Instrumento editado correctamente"));
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<ApiResponse> eliminarInstrumento(@PathVariable("codigo") String codigo)
    {
        validarCodigo(codigo, "código del instrumento");
        servicioInstrumento.eliminarInstrumento(codigo);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(false, "Instrumento eliminado correctamente"));
    }

    @PostMapping(value = "/guitarras/{codigo}/fundas")
    public ResponseEntity<ApiResponse> agregarFundas(@PathVariable("codigo") String codigo,
                                                     @RequestBody List<Funda> fundas)
    {
        validarCodigo(codigo, "código de la guitarra");
        servicioInstrumento.agregarFundas(codigo, fundas);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(false, "Fundas agregadas correctamente"));
    }

    @PutMapping(value = "/guitarras/{codigo}/fundas/{codigoFunda}")
    public ResponseEntity<ApiResponse> editarFunda(@PathVariable("codigo") String codigo,
                                                    @PathVariable("codigoFunda") String codigoFunda,
                                                    @RequestBody Funda fundaModificada) {
        validarCodigo(codigo, "código de la guitarra");
        validarCodigo(codigoFunda, "código de la funda");
        servicioInstrumento.editarFunda(codigo, codigoFunda, fundaModificada);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(false, "Funda modificada correctamente"));
    }

    @DeleteMapping(value = "/guitarras/{codigo}/fundas/{codigoFunda}")
    public ResponseEntity<ApiResponse> eliminarFunda(@PathVariable("codigo") String codigo,
                                                     @PathVariable("codigoFunda") String codigoFunda)
    {
        validarCodigo(codigo, "código de la guitarra");
        validarCodigo(codigoFunda, "código de la funda");
        servicioInstrumento.eliminarFunda(codigo, codigoFunda);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(false, "Funda eliminada correctamente"));

    }
}
