package edu.unimag.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que registra cada cambio de estado en una orden de compra.
 * Permite mantener un historial completo de transiciones entre estados,
 * incluyendo cuándo ocurrió el cambio, quién lo realizó y observaciones
 * adicionales sobre la razón del cambio.
 * NOTA:
 * Esta entidad es esencial para la trazabilidad del ciclo de vida de las
 * órdenes de compra y para auditorías del proceso de compras.
 */

@Entity
@Table(name = "historial_estados_orden")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "HistorialEstadoOrden", description = "Registro histórico de cambios de estado en órdenes de compra")
public class HistorialEstadoOrden {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del registro histórico", example = "1")
    private Long id;
    
    @Schema(description = "Orden de compra a la que pertenece este registro histórico")
    @ManyToOne
    @JoinColumn(name = "orden_compra_id", nullable = false)
    private OrdenCompra ordenCompra;
    
    @Schema(description = "Estado anterior de la orden antes del cambio", example = "PENDIENTE")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoOrden estadoAnterior;
    
    @Schema(description = "Nuevo estado al que cambió la orden", example = "ENVIADA")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoOrden estadoNuevo;
    
    @Schema(description = "Fecha y hora exacta del cambio de estado", example = "2025-04-16T09:30:00")
    @Column(nullable = false)
    private LocalDateTime fechaCambio;
    
    @Schema(description = "Usuario que realizó el cambio de estado")
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @Schema(description = "Motivo o comentario explicativo sobre el cambio de estado", example = "Orden enviada al proveedor vía correo electrónico")
    @Column(length = 255)
    private String observacion;

    public Long getOrdenCompraId() {
        return this.getOrdenCompraId();  
        
    }
}
