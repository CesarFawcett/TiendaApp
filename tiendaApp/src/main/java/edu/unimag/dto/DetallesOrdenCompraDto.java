package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la transferencia de datos de DetallesOrdenCompra")
public class DetallesOrdenCompraDto {
    @Schema(description = "ID único del detalle de orden", example = "1")
    private Long id;

    @Schema(description = "ID del producto en el detalle", example = "3")
    private Long productoId;

    @Schema(description = "Cantidad del producto en el detalle", example = "10")
    private Integer cantidad;

    @Schema(description = "Precio unitario del producto en el detalle", example = "125000")
    private Double precioUnitario;

    @Schema(description = "Valor subtotal calculado (cantidad × precioUnitario) para esta línea", example = "1250000")
    private Double subtotal;
}
