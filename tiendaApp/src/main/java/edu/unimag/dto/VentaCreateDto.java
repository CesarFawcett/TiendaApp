package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Schema(description = "DTO para la creación de una nueva Venta")
public class VentaCreateDto {
    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "Fecha y hora de la venta", example = "2024-03-15T10:00:00")
    private LocalDate fecha;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente de la venta", example = "101")
    private Long clienteId;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Schema(description = "ID del usuario que realizó la venta", example = "201")
    private Long usuarioId;
}
