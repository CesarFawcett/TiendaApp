package edu.unimag.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrdenCompraCreateDto {
    private Long proveedorId;
    private List<DetallesOrdenCompraCreateDto> detalles;
    //private EstadoOrden estado;
    //private LocalDate fecha;
    // El estado se establecerá como PENDIENTE
    // La fecha se asignará automáticamente
}