package edu.unimag.dto;

import edu.unimag.entities.EstadoOrden;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrdenCompraDto {
    private Long id;
    private LocalDate fecha;
    private EstadoOrden estado;
    private Long proveedorId;
    private String proveedorNombre;
    private List<DetallesOrdenCompraDto> detalles;
    private Double total;
}