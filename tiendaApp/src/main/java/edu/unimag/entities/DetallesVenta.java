package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa cada línea o ítem individual dentro de una venta realizada.
 * Contiene información sobre el producto vendido, cantidad, precio unitario aplicado
 * y el cálculo del subtotal para cada producto en la venta.
 * NOTA:
 * Esta entidad establece la relación muchos a uno con Venta y Producto, registrando
 * la composición detallada de cada transacción de venta en el sistema.
 */

@Entity
@Table(name = "detalles_ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DetallesVentas", description = "Línea individual de producto dentro de una transacción de venta")
public class DetallesVenta{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único autogenerado del detalle de venta", example = "501")
    private Long id;

    @Schema(description = "Cantidad de unidades vendidas del producto", example = "2")
    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    @Schema(description = "Precio unitario aplicado al momento de la venta (puede diferir del precio actual)", example = "149000")
    private Double precioUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @Schema(description = "Venta a la que pertenece este detalle")
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @Schema(description = "Producto específico vendido en esta línea")
    private Producto producto;
    
    @Transient
    @Schema(description = "Valor subtotal calculado (cantidad × precioUnitario) para esta línea", example = "298000")
    public Double getSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            return cantidad * precioUnitario;
        }
        return 0.0;
    }
    
}
