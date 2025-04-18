package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que registra todas las operaciones realizadas en el sistema para fines de auditoría.
 * Almacena información sobre qué acción se realizó, qué entidad fue afectada, quién la realizó,
 * cuándo se realizó y detalles adicionales sobre la operación.
 * Nota:
 * Esta entidad es esencial para el cumplimiento de requisitos de seguridad y trazabilidad.
 */

@Entity
@Table(name = "auditoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Auditoria", description = "Registro de acciones realizadas en el sistema para fines de trazabilidad y seguridad")
public class Auditoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID autogenerado de registro de auditoría", example = "1001")
    private Long id;

    @Schema(description = "Tipo de acción realizada (CREATE, UPDATE, DELETE, READ)", example = "CREATE")
    @Column(nullable = false, length = 50)
    private String accion;

    @Schema(description = "Nombre de la entidad afectada por la acción", example = "Producto")
    @Column(name = "entidad_afectada", nullable = false, length = 50)
    private String entidadAfectada;

    @Schema(description = "ID de la entidad que fue afectada por la acción", example = "5")
    @Column(name = "id_entidad", nullable = false)
    private Long idEntidad;

    @Schema(description = "Fecha y hora exacta cuando se realizó la acción", example = "2025-04-15T14:30:00")
    @Column(nullable = false)
    private LocalDateTime fecha;

    @Schema(description = "Detalles adicionales de la operación en formato JSON, incluye valores anteriores y nuevos", example = "{\"campoAnterior\": \"valorAnterior\", \"campoNuevo\": \"valorNuevo\"}")
    @Column(columnDefinition = "TEXT")
    private String detalle;

    @Schema(description = "Usuario que realizó la acción registrada")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
