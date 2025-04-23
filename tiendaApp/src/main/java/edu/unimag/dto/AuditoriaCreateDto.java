package edu.unimag.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditoriaCreateDto {
    private String accion;
    private String entidadAfectada;
    private Long idEntidad;
    private LocalDateTime fecha;
    private String detalle;
    private Long usuarioId;
}
