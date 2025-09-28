package com.unibague.poctiendainstrumentos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse
{
    private boolean error;
    private String mensaje;
}
