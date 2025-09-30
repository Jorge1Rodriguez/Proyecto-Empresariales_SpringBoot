package com.unibague.poctiendainstrumentos.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeración que representa los tipos válidos de guitarras disponibles en la aplicación.
 *
 * <p>Cada tipo de guitarra tiene un valor descriptivo legible.
 * Este enum se utiliza para clasificar guitarras en el sistema y facilitar la serialización/deserialización JSON.</p>
 *
 * <p>Incluye integración con Jackson para manejar la conversión entre el valor legible y el valor enum,
 * permitiendo que el valor JSON sea una cadena descriptiva y que el backend acepte valores flexibles en solicitudes.</p>
 *
 * <ul>
 *   <li>ELECTRICA: "Eléctrica"</li>
 *   <li>ACUSTICA: "Acústica"</li>
 *   <li>ELECTROACUSTICA: "Electroacústica"</li>
 *   <li>CLASICA: "Clásica"</li>
 * </ul>
 *
 * @author Jorge
 */
public enum TipoGuitarra
{
    /**
     * Guitarra eléctrica.
     */
    ELECTRICA("Eléctrica"),

    /**
     * Guitarra acústica.
     */
    ACUSTICA("Acústica"),

    /**
     * Guitarra electroacústica.
     */
    ELECTROACUSTICA("Electroacústica"),

    /**
     * Guitarra clásica.
     */
    CLASICA("Clásica");

    /**
     * Valor descriptivo legible del tipo de guitarra.
     */
    private final String valor;

    /**
     * Constructor privado del enum, asigna el valor legible.
     * @param valor valor descriptivo
     */
    TipoGuitarra(String valor) {
        this.valor = valor;
    }

    /**
     * Devuelve el valor legible del tipo de guitarra, usado al serializar a JSON.
     *
     * @return valor legible
     */
    @JsonValue
    public String getValor() {
        return valor;
    }

    /**
     * Crea una instancia de TipoGuitarra a partir de una cadena de texto, ignorando mayúsculas/minúsculas y espacios.
     * Lanza una excepción si el valor no es válido.
     * Usado por Jackson al deserializar requests JSON.
     *
     * @param valor cadena descriptiva del tipo
     * @return instancia de TipoGuitarra correspondiente
     * @throws IllegalArgumentException si el valor es nulo o no corresponde a ningún tipo válido
     */
    @JsonCreator
    public static TipoGuitarra desdeValor(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El tipo de guitarra no puede ser nulo");
        }
        String normalizado = valor.trim().toLowerCase();

        for (TipoGuitarra tipo : values()) {
            if (tipo.valor.toLowerCase().equals(normalizado)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de guitarra no válido: " + valor);
    }
}

