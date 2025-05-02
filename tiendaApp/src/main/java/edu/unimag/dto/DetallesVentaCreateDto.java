package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Schema(description = "DTO para la creación de un nuevo Detalle de Venta")
public class DetallesVentaCreateDto {

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    @Schema(description = "Cantidad de unidades vendidas del producto", example = "2")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio unitario aplicado al momento de la venta", example = "149000")
    private Double precioUnitario;

    @NotNull(message = "El ID del producto es obligatorio")
    @Schema(description = "ID del producto vendido", example = "101")
    private Long productoId;
}
