package com.unibague.poctiendainstrumentos.dto;

import com.unibague.poctiendainstrumentos.model.enums.TipoGuitarra;
import com.unibague.poctiendainstrumentos.model.enums.SensibilidadTeclado;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroInstrumentoDTO
{
    private String nombre;
    private String marca;
    private Double precioMin;
    private Double precioMax;
    private Integer stockMin;
    private Integer stockMax;
    private TipoGuitarra tipoGuitarra;
    private SensibilidadTeclado sensibilidad;
}
