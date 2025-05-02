package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la creación de un nuevo registro de Auditoria")
public class AuditoriaCreateDto {
    @NotBlank(message = "La acción es obligatoria")
    @Schema(description = "Tipo de acción realizada", example = "CREATE")
    private String accion;

    @NotBlank(message = "La entidad afectada es obligatoria")
    @Schema(description = "Entidad afectada por la acción", example = "Producto")
    private String entidadAfectada;

    @NotNull(message = "El ID de la entidad afectada es obligatorio")
    @Schema(description = "ID de la entidad afectada", example = "10")
    private Long idEntidad;

    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "Fecha y hora de la acción", example = "2024-03-15T12:00:00")
    private LocalDateTime fecha;

    @Schema(description = "Detalles de la acción", example = "{\"campo\": \"valor\"}")
    private String detalle;
}
