package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 
 * 
 */

@Entity
@Table(name = "auditoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Auditoria", description = "Registro de acciones realizadas en el sistema")
public class Auditoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado", example = "1001")
    private Long id;

    @Column(nullable = false, length = 50)
    @Schema(description = "Tipo de acción realizada", example = "CREATE")
    private String accion;

    @Column(name = "entidad_afectada", nullable = false, length = 50)
    @Schema(description = "Entidad afectada por la acción", example = "Producto")
    private String entidadAfectada;

    @Column(name = "id_entidad", nullable = false)
    @Schema(description = "ID de la entidad afectada", example = "5")
    private Long idEntidad;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora de la acción", example = "2025-04-15T14:30:00")
    private LocalDateTime fecha;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detalles adicionales en formato JSON", example = "{\"campo\": \"valor\"}")
    private String detalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
