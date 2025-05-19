package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la transferencia de datos de Venta")
public class VentaDto {
    @Schema(description = "ID único de la venta", example = "1")
    private Long id;

    @Schema(description = "Fecha y hora de la venta", example = "2024-03-15T10:00:00")
    private LocalDate fecha;

    @Schema(description = "ID del cliente de la venta", example = "101")
    private Long clienteId;

    @Schema(description = "ID del usuario que realizó la venta", example = "201")
    private Long usuarioId;

    @Schema(description = "Total de la venta", example = "250000")
    private Double total;
}
