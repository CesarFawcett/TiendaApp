package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la transferencia de datos de DetallesVenta")
public class DetallesVentaDto {
    @Schema(description = "ID único del detalle de venta", example = "501")
    private Long id;

    @Schema(description = "Cantidad de unidades vendidas del producto", example = "2")
    private Integer cantidad;

    @Schema(description = "Precio unitario aplicado al momento de la venta", example = "149000")
    private Double precioUnitario;

    @Schema(description = "ID del producto vendido", example = "101")
    private Long productoId;

    @Schema(description = "Valor subtotal calculado (cantidad × precioUnitario) para esta línea", example = "298000")
    private Double subtotal;
}
