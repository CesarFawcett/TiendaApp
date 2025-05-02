package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la transferencia de datos de Auditoria")
public class AuditoriaDto {
    @Schema(description = "ID único del registro de auditoría", example = "1")
    private Long id;

    @Schema(description = "Tipo de acción realizada", example = "CREATE")
    private String accion;

    @Schema(description = "Entidad afectada por la acción", example = "Producto")
    private String entidadAfectada;

    @Schema(description = "ID de la entidad afectada", example = "10")
    private Long idEntidad;

    @Schema(description = "Fecha y hora de la acción", example = "2024-03-15T12:00:00")
    private LocalDateTime fecha;

    @Schema(description = "Detalles de la acción", example = "{\"campo\": \"valor\"}")
    private String detalle;
}

