package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import edu.unimag.entities.EstadoOrden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la creación de un nuevo registro de HistorialEstadoOrden")
public class HistorialEstadoOrdenCreateDto {
    @NotNull(message = "El ID de la orden de compra es obligatorio")
    @Schema(description = "ID de la orden de compra asociada", example = "101")
    private Long ordenCompraId;

    @NotNull(message = "El estado anterior es obligatorio")
    @Schema(description = "Estado anterior de la orden", example = "PENDIENTE")
    private EstadoOrden estadoAnterior;

    @NotNull(message = "El nuevo estado es obligatorio")
    @Schema(description = "Nuevo estado de la orden", example = "ENVIADA")
    private EstadoOrden estadoNuevo;

    @NotNull(message = "La fecha y hora del cambio son obligatorias")
    @Schema(description = "Fecha y hora del cambio de estado", example = "2024-03-15T12:00:00")
    private LocalDateTime fechaCambio;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Schema(description = "ID del usuario que realizó el cambio", example = "201")
    private Long usuarioId;

    @Schema(description = "Observaciones sobre el cambio", example = "Orden enviada al proveedor")
    private String observacion;
}
