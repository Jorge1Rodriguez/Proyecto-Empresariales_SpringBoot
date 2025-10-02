package com.unibague.poctiendainstrumentos.controller;

import com.unibague.poctiendainstrumentos.dto.ApiResponse;
import com.unibague.poctiendainstrumentos.dto.FiltroInstrumentoDTO;
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

/**
 * Controlador REST para manejar solicitudes relacionadas con instrumentos musicales.
 *
 * <p>Provee endpoints para agregar, listar, buscar, editar y eliminar instrumentos,
 * junto con funcionalidades específicas para guitarras, teclados y gestión de fundas.</p>
 *
 * <p>Incluye validaciones básicas para campos clave como el código de instrumento
 * para evitar errores comunes.</p>
 *
 * <p>Permite filtrar instrumentos usando un objeto DTO con múltiples criterios mediante un endpoint dedicado.</p>
 *
 * <p>Los métodos devuelven {@link ResponseEntity} con el código HTTP adecuado y
 * respuestas unificadas en formato {@link ApiResponse} para operaciones CRUD y mensajes.</p>
 *
 * @author Jorge
 */
@RestController
@RequestMapping("/instrumentos")
public class InstrumentoController
{

    @Autowired
    private IServicioInstrumento servicioInstrumento;

    /**
     * Valida que el código de instrumento o funda no sea nulo ni vacío.
     *
     * @param codigo valor del código a validar
     * @param campo nombre del campo para mensaje de error
     * @throws IllegalArgumentException si el código es nulo o vacío
     */
    private void validarCodigo(String codigo, String campo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El " + campo + " debe existir y no estar vacío");
        }
    }

    /**
     * Endpoint para verificar el estado del servicio.
     *
     * @return cadena simple "Status Ok!" indicando servicio activo.
     */
    @GetMapping(value = "/healthCheck")
    public String healthCheck()
    {
        return "Status Ok!";
    }

    /**
     * Endpoint para agregar un nuevo instrumento.
     *
     * @param instrumento objeto {@link Instrumento} enviado en el cuerpo de la solicitud.
     * @return respuesta con código 201 (CREATED) y mensaje de éxito.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> agregarInstrumento(@RequestBody Instrumento instrumento)
    {
            servicioInstrumento.agregarInstrumento(instrumento);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(false, "Instrumento agregado correctamente"));
    }

    /**
     * Endpoint que devuelve la lista completa de instrumentos registrados.
     *
     * @return lista de instrumentos en respuesta con código 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Instrumento>> listarInstrumentos()
    {
        List<Instrumento> listaInstrumentos= servicioInstrumento.listarInstrumentos();
        return ResponseEntity.ok(listaInstrumentos);
    }

    /**
     * Devuelve la lista solo de guitarras.
     *
     * @return lista de {@link Guitarra}
     */
    @GetMapping(value = "/guitarras")
    public ResponseEntity<List<Guitarra>> listarGuitarras()
    {
        List<Guitarra> listaGuitarras = servicioInstrumento.listarGuitarras();
        return ResponseEntity.ok(listaGuitarras);

    }

    /**
     * Devuelve la lista solo de teclados.
     *
     * @return lista de {@link Teclado}
     */
    @GetMapping(value = "/teclados")
    public ResponseEntity<List<Teclado>> listarTeclados()
    {
        List<Teclado> listaTeclados = servicioInstrumento.listarTeclados();
        return ResponseEntity.ok(listaTeclados);

    }

    /**
     * Busca un instrumento por su código único.
     *
     * @param codigo código del instrumento a buscar.
     * @return instrumento encontrado con código 200 o mensaje error 404 si no existe.
     */
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

    /**
     * Edita un instrumento existente identificado por código.
     *
     * @param codigo código único del instrumento
     * @param instrumentoModificado nuevos datos del instrumento
     * @return respuesta con mensaje de éxito y código 200
     */
    @PutMapping(value = "/{codigo}")
    public ResponseEntity<ApiResponse> editarInstrumento(@PathVariable("codigo") String codigo,
                                                         @RequestBody Instrumento instrumentoModificado)
    {
        validarCodigo(codigo, "código del instrumento");
        servicioInstrumento.editarInstrumento(codigo, instrumentoModificado);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(false, "Instrumento editado correctamente"));
    }

    /**
     * Elimina un instrumento por código.
     *
     * @param codigo código único del instrumento
     * @return respuesta con mensaje de éxito y código 200
     */
    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<ApiResponse> eliminarInstrumento(@PathVariable("codigo") String codigo)
    {
        validarCodigo(codigo, "código del instrumento");
        servicioInstrumento.eliminarInstrumento(codigo);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(false, "Instrumento eliminado correctamente"));
    }

    /**
     * Agrega fundas a una guitarra existente.
     *
     * @param codigo código de la guitarra
     * @param fundas lista de fundas para agregar
     * @return respuesta con mensaje de éxito y código 201
     */
    @PostMapping(value = "/guitarras/{codigo}/fundas")
    public ResponseEntity<ApiResponse> agregarFundas(@PathVariable("codigo") String codigo,
                                                     @RequestBody List<Funda> fundas)
    {
        validarCodigo(codigo, "código de la guitarra");
        servicioInstrumento.agregarFundas(codigo, fundas);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(false, "Fundas agregadas correctamente"));
    }

    /**
     * Edita una funda específica de una guitarra.
     *
     * @param codigo código de la guitarra
     * @param codigoFunda código de la funda a modificar
     * @param fundaModificada funda con datos actualizados
     * @return respuesta con mensaje de éxito y código 200
     */
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

    /**
     * Elimina una funda específica de una guitarra.
     *
     * @param codigo código de la guitarra
     * @param codigoFunda código de la funda a eliminar
     * @return respuesta con mensaje de éxito y código 200
     */
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

    /**
     * Filtra instrumentos según los criterios en un DTO.
     *
     * @param filtro DTO con filtros como nombre, marca, precio, stock, tipo, sensibilidad
     * @return lista de instrumentos que cumplen los criterios
     */
    @PostMapping(value = "/filtrar")
    public ResponseEntity<List<Instrumento>> filtrarInstrumentos(@RequestBody FiltroInstrumentoDTO filtro) {
        List<Instrumento> resultado = servicioInstrumento.filtrarInstrumentos(filtro);
        return ResponseEntity.ok(resultado);
    }
}
