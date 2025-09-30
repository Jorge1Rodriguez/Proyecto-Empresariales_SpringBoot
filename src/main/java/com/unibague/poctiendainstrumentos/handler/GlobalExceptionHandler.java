package com.unibague.poctiendainstrumentos.handler;

import com.unibague.poctiendainstrumentos.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Controlador global para manejo centralizado de excepciones en la aplicación.
 *
 * <p>Utiliza la anotación {@link ControllerAdvice} para interceptar excepciones
 * arrojadas en cualquier controlador y devolver respuestas HTTP adecuadas
 * con mensajes informativos y estados HTTP.</p>
 *
 * <p>Los tipos de excepciones manejados incluyen:</p>
 * <ul>
 *     <li>{@link HttpMessageNotReadableException}: para peticiones con cuerpo inválido o ausente.</li>
 *     <li>{@link NoSuchElementException}: para recursos no encontrados.</li>
 *     <li>{@link IllegalArgumentException}: para solicitudes inválidas o argumentos incorrectos.</li>
 *     <li>{@link IllegalStateException}: para conflictos de estado, como duplicados.</li>
 * </ul>
 *
 * <p>Cada excepción retorna un objeto {@link ApiResponse} con un mensaje y un indicador de error,
 * enviando el código HTTP correspondiente.</p>
 *
 * <p>Permite mantener respuestas consistentes de error y mejorar la experiencia del consumidor
 * de la API REST.</p>
 *
 * @author Jorge
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
    /**
     * Maneja excepciones de cuerpo mal formateado o ausente en una petición HTTP.
     *
     * @param ex excepción lanzada por Spring al leer cuerpo JSON inválido.
     * @return respuesta con estado {@link HttpStatus#BAD_REQUEST} y mensaje descriptivo.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> HttpMessageNotReadableException(HttpMessageNotReadableException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(true, "El cuerpo de la petición es requerido y debe estar en formato JSON válido."));
    }

    /**
     * Maneja excepciones cuando no se encuentra un recurso esperado.
     *
     * @param e excepción que contiene el mensaje de recurso no encontrado.
     * @return respuesta con estado {@link HttpStatus#NOT_FOUND} y mensaje del error.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse> handleNotFound(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(true, e.getMessage()));
    }

    /**
     * Maneja excepciones de argumentos inválidos en la solicitud.
     *
     * @param e excepción que describe el problema de argumento.
     * @return respuesta con estado {@link HttpStatus#BAD_REQUEST} y mensaje de error.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(true, e.getMessage()));
    }

    /**
     * Maneja excepciones de estado ilegal o conflictos en la solicitud.
     *
     * @param e excepción del tipo estado ilegal.
     * @return respuesta con estado {@link HttpStatus#CONFLICT} y mensaje de error.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse> handleBadRequest(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse(true, e.getMessage()));
    }
}
