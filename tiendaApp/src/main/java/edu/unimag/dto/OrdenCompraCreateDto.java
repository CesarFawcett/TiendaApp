package edu.unimag.dto;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import edu.unimag.entities.EstadoOrden;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
@Schema(description = "DTO para la creación de una nueva orden de compra")
public class OrdenCompraCreateDto {

    @Schema(description = "Fecha de emisión de la orden", example = "2024-03-15")
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @Schema(description = "Estado inicial de la orden", example = "PENDIENTE")
    @NotNull(message = "El estado es obligatorio")
    private EstadoOrden estado;

    @Schema(description = "ID del proveedor asociado a la orden", example = "101")
    @NotNull(message = "El ID del proveedor es obligatorio")
    private Long proveedorId;

    //  No incluimos detalles aquí, se manejan aparte
}
