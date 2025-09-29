package com.unibague.poctiendainstrumentos.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SensibilidadTeclado {
    NINGUNA("Ninguna"),
    BASICA("Básica"),
    MEDIA("Intermedia"),
    ALTA("Alta"),
    PROFESIONAL("Profesional");

    private final String valor;

    SensibilidadTeclado(String valor) {
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }

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
