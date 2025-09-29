package com.unibague.poctiendainstrumentos.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoGuitarra {
    ELECTRICA("Eléctrica"),
    ACUSTICA("Acústica"),
    ELECTROACUSTICA("Electroacústica"),
    CLASICA("Clásica");

    private final String valor;

    TipoGuitarra(String valor) {
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }

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

