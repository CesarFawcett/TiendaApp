package edu.unimag.dto;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import edu.unimag.entities.EstadoOrden;
import lombok.Data;

@Data
@Schema(description = "DTO para mostrar los detalles principales de una orden de compra")
public class OrdenCompraDto {

    @Schema(description = "ID único de la orden de compra", example = "1")
    private Long id;

    @Schema(description = "Fecha de emisión de la orden", example = "2024-03-15")
    private LocalDate fecha;

    @Schema(description = "Estado actual de la orden", example = "PENDIENTE")
    private EstadoOrden estado;

    @Schema(description = "ID del proveedor asociado a la orden", example = "101")
    private Long proveedorId;

    @Schema(description = "Total calculado de la orden", example = "1500.50")
    private Double totalCalculated;

    //  No incluimos detalles ni historial de estados en este DTO básico
}
