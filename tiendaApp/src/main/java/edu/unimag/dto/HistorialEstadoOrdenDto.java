package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import edu.unimag.entities.EstadoOrden;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la transferencia de datos de HistorialEstadoOrden")
public class HistorialEstadoOrdenDto {
    @Schema(description = "ID único del registro histórico", example = "1")
    private Long id;

    @Schema(description = "ID de la orden de compra asociada", example = "101")
    private Long ordenCompraId;

    @Schema(description = "Estado anterior de la orden", example = "PENDIENTE")
    private EstadoOrden estadoAnterior;

    @Schema(description = "Nuevo estado de la orden", example = "ENVIADA")
    private EstadoOrden estadoNuevo;

    @Schema(description = "Fecha y hora del cambio de estado", example = "2024-03-15T12:00:00")
    private LocalDateTime fechaCambio;

    @Schema(description = "ID del usuario que realizó el cambio", example = "201")
    private Long usuarioId;

    @Schema(description = "Observaciones sobre el cambio", example = "Orden enviada al proveedor")
    private String observacion;
}
