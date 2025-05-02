package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Schema(description = "DTO para la creación de un nuevo Detalle de Orden de Compra")
public class DetallesOrdenCompraCreateDto {

    @NotNull(message = "El ID del producto es obligatorio")
    @Schema(description = "ID del producto en el detalle", example = "3")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    @Schema(description = "Cantidad del producto en el detalle", example = "10")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio unitario del producto en el detalle", example = "125000")
    private Double precioUnitario;
}
