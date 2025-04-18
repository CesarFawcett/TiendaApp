package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa una transacción de venta completa realizada a un cliente.
 * Registra la fecha y hora de la transacción, el cliente que realizó la compra,
 * el usuario que registró la venta, y todos los productos vendidos con sus
 * cantidades y precios.
 * NOTA:
 * Esta entidad es fundamental para el módulo de ventas, facturación y análisis
 * de rendimiento comercial del negocio.
 */

@Entity
@Table(name = "ventas", indexes = {
    @Index(name = "idx_venta_fecha", columnList = "fecha"),
    @Index(name = "idx_venta_usuario", columnList = "usuario_id"),
    @Index(name = "idx_venta_cliente", columnList = "cliente_id")
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Venta", description = "Transacción de venta de productos a un cliente")
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único autogenerado de la venta", example = "1")
    private Long id;

    @Schema(description = "Fecha y hora exacta de la transacción de venta", example = "2025-04-15T14:30:00")
    @Column(nullable = false)
    private LocalDateTime fecha;

    // Relación con Usuario
    @Schema(description = "Usuario que registró la venta en el sistema")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación con DetalleVenta 
    @Schema(description = "Lista de productos vendidos con sus cantidades y precios")
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallesVenta> detalles = new ArrayList<>();
    
    // Relacion con Cliente
    @Schema(description = "Cliente que realizó la compra y a quien se factura")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * Añade un nuevo detalle a la venta y establece la relación bidireccional
     * NOTA:
     * @param detalle Detalle de producto a añadir a la venta
     */
    public void addDetalle(DetallesVenta detalle) {
        detalles.add(detalle);
        detalle.setVenta(this);
    }
    
    /**
     * Elimina un detalle de la venta y rompe la relación bidireccional
     * NOTA:
     * @param detalle Detalle de producto a eliminar de la venta
     */
    public void removeDetalle(DetallesVenta detalle) {
        detalles.remove(detalle);
        detalle.setVenta(null);
    }
    
    /**
     * Calcula el total de la venta sumando todos los subtotales de los detalles
     * NOTA:
     * @return Monto total de la venta
     */
    @Transient
    @Schema(description = "Monto total calculado de la venta (suma de todos los subtotales)", example = "345000")
    public Double getTotal() {
        if (detalles == null || detalles.isEmpty()) {
            return 0.0;
        }
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getSubtotal())
                .sum();
    }
    
}
