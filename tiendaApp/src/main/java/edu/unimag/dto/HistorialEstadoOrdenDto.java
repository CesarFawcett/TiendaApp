package edu.unimag.dto;

import edu.unimag.entities.EstadoOrden;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistorialEstadoOrdenDto {
    private Long id;
    private Long ordenCompraId;
    private EstadoOrden estadoAnterior;
    private EstadoOrden estadoNuevo;
    private LocalDateTime fechaCambio;
    private String observacion;
    private Long usuarioId;
    private String usuarioNombre;
}