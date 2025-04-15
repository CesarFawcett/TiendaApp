package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 
 * 
 */

@Entity
@Table(name = "detalles_ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DetallesVentas", description = "Detalle de los productos incluidos en una venta")
public class DetallesVenta{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado", example = "501")
    private Long id;

    @Schema(description = "Cantidad del producto vendido", example = "2")
    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Precio unitario al momento de la venta", example = "149.99")
    private Double precioUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    
    @Column(name = "subtotal", insertable = false, updatable = false)
    @Schema(description = "Subtotal calculado (cantidad * precio_unitario)", example = "1250000", accessMode = Schema.AccessMode.READ_ONLY)
    private Double subtotal;
}
