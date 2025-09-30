package com.unibague.poctiendainstrumentos.dto;

import com.unibague.poctiendainstrumentos.model.enums.TipoGuitarra;
import com.unibague.poctiendainstrumentos.model.enums.SensibilidadTeclado;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para encapsular los filtros usados al consultar instrumentos musicales.
 *
 * <p>Incluye múltiples campos opcionales permitiendo filtrar por nombre, marca, rango de precio,
 * rango de stock, tipo de guitarra y sensibilidad de teclado.</p>
 *
 * <p>Se utiliza habitualmente en peticiones REST para búsquedas avanzadas.</p>
 *
 * <p>Usa la anotación {@link Builder} para facilitar su construcción flexible con patrones de diseño.</p>
 *
 * @author Jorge
 */
@Data
@Builder
public class FiltroInstrumentoDTO
{
    /**
     * Nombre o modelo parcial a filtrar.
     */
    private String nombre;

    /**
     * Marca del instrumento a filtrar.
     */
    private String marca;

    /**
     * Precio mínimo permitido.
     */
    private Double precioMin;

    /**
     * Precio máximo permitido.
     */
    private Double precioMax;

    /**
     * Stock mínimo permitido.
     */
    private Integer stockMin;

    /**
     * Stock máximo permitido.
     */
    private Integer stockMax;

    /**
     * Tipo de guitarra a filtrar (si aplica).
     */
    private TipoGuitarra tipoGuitarra;

    /**
     * Sensibilidad del teclado a filtrar (si aplica).
     */
    private SensibilidadTeclado sensibilidad;
}
