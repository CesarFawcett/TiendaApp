package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa cada línea o ítem individual dentro de una orden de compra a proveedores.
 * Contiene la información del producto solicitado, cantidad, precio unitario y permite
 * calcular el subtotal de cada línea de la orden.
 * NOTA:
 * Esta entidad establece la relación muchos a uno con OrdenCompra y Producto, representando
 * la composición detallada de cada orden de compra realizada a proveedores.
 */

@Entity
@Table(name = "detalles_orden_compras")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DetallesOrdenCompra", description = "Línea individual de producto dentro de una orden de compra a proveedor")
public class DetallesOrdenCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único del detalle de orden", example = "1")
    private Long id;

    @Schema(description = "Orden de compra a la que pertenece este detalle", example = "{\"id\": 1}")
    @ManyToOne
    @JoinColumn(name = "orden_compra_id", nullable = false)
    private OrdenCompra ordenCompra;

    @Schema(description = "Producto específico solicitado en esta línea de la orden", example = "{\"id\": 3, \"nombre\": \"Leche en polvo\"}")
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Schema(description = "Cantidad de unidades solicitadas del producto", example = "10")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @Schema(description = "Precio unitario negociado con el proveedor para este producto", example = "125000")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precioUnitario;

    @Transient
    @Schema(description = "Valor subtotal calculado (cantidad × precioUnitario) para esta línea", example = "1250000")
    public Double getSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            return cantidad * precioUnitario;
        }
        return 0.0;
    }

}
