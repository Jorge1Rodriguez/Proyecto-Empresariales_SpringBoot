package com.unibague.poctiendainstrumentos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la estructura estándar de respuesta para la API REST,
 * indicando si ocurrió algún error y un mensaje descriptivo.
 *
 * <p>Esta clase es utilizada para unificar el formato de respuesta de errores
 * y mensajes informativos en los endpoints del servidor.</p>
 *
 * <p>Incluye un campo booleano para indicar error,
 * y un campo de texto para el mensaje correspondiente.</p>
 *
 * <p>Usa anotaciones Lombok para generar constructores, getters, setters,
 * equals, hashCode y toString automáticamente.</p>
 *
 * @author Jorge
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse
{
    /**
     * Indica si ocurrió un error (true) o si la respuesta fue exitosa (false).
     */
    private boolean error;

    /**
     * Mensaje descriptivo asociado a la respuesta o error.
     */
    private String mensaje;
}
