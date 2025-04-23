package edu.unimag.dto;

import edu.unimag.entities.EstadoOrden;
import lombok.Data;

@Data
public class HistorialEstadoOrdenCreateDto {
    private EstadoOrden estadoNuevo;
    private String observacion;
    //private EstadoOrden estadoAnterior;
    //private LocalDateTime fechaCambio;
    //private Long usuarioId;

    // El estado anterior se obtiene de la orden actual
    // La fecha se asigna automáticamente
    // El usuario se toma del contexto de seguridad
}
