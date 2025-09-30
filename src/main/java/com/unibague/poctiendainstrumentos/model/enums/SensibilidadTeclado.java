package com.unibague.poctiendainstrumentos.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeración que representa los niveles de sensibilidad que puede tener un teclado musical.
 *
 * <p>Cada valor es una descripción legible utilizada durante la serialización y deserialización
 * con JSON, permitiendo compatibilidad y claridad en la comunicación entre cliente y servidor.</p>
 *
 * <ul>
 *   <li>NINGUNA: "Ninguna"</li>
 *   <li>BASICA: "Básica"</li>
 *   <li>MEDIA: "Intermedia"</li>
 *   <li>ALTA: "Alta"</li>
 *   <li>PROFESIONAL: "Profesional"</li>
 * </ul>
 *
 * <p>
 * Incluye métodos para obtener la representación legible y para crear un valor del enum a partir
 * de una cadena de texto, ignorando mayúsculas/minúsculas y espacios.
 * Se integra con Jackson para soporte transparente en JSON.
 * </p>
 *
 * @author Jorge
 */
public enum SensibilidadTeclado
{
    /**
     * Sin sensibilidad especial.
     */
    NINGUNA("Ninguna"),

    /**
     * Sensibilidad básica.
     */
    BASICA("Básica"),

    /**
     * Sensibilidad intermedia.
     */
    MEDIA("Intermedia"),

    /**
     * Sensibilidad alta.
     */
    ALTA("Alta"),

    /**
     * Sensibilidad profesional.
     */
    PROFESIONAL("Profesional");

    /**
     * Valor legible y descriptivo de la sensibilidad.
     */
    private final String valor;

    /**
     * Constructor privado para asociar a cada enum su valor legible.
     * @param valor descripción humana del nivel de sensibilidad
     */
    SensibilidadTeclado(String valor) {
        this.valor = valor;
    }

    /**
     * Obtiene el valor textual del enum, usado al serializar a JSON.
     * @return valor legible de la sensibilidad
     */
    @JsonValue
    public String getValor() {
        return valor;
    }

    /**
     * Crea una instancia de SensibilidadTeclado a partir de una cadena descriptiva,
     * ignorando diferencias de mayúsculas/minúsculas y espacios.
     * Lanza excepción si el valor no es válido.
     * Utilizada automáticamente por Jackson en deserialización.
     *
     * @param valor descripción textual
     * @return valor correspondiente del enum
     * @throws IllegalArgumentException si el valor es nulo o no corresponde a ningún nivel válido
     */
    @JsonCreator
    public static SensibilidadTeclado desdeValor(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("La sensibilidad no puede ser nula");
        }
        String normalizado = valor.trim().toLowerCase();

        for (SensibilidadTeclado s : values()) {
            if (s.valor.toLowerCase().equals(normalizado)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Sensibilidad no válida: " + valor);
    }
}
