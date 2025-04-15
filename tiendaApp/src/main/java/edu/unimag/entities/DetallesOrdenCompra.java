package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 
 * 
 */

@Entity
@Table(name = "detalles_orden_compras")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DetallesOrdenCompra", description = "Entidad representante los detalles de las compras realizadas a proveedores")
public class DetallesOrdenCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del detalle", example = "1")
    private Long id;

    @Schema(description = "Orden de compra asociada a este detalle", example = "{\"id\": 1}")
    @ManyToOne
    @JoinColumn(name = "orden_compra_id", nullable = false)
    private OrdenCompra ordenCompra;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @Schema(description = "Producto solicitado en la orden", example = "{\"id\": 3, \"nombre\": \"Leche en polvo\"}")
    private Producto producto;

    @Schema(description = "Cantidad de unidades del producto", example = "10")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @Schema(description = "Precio unitario del producto al momento de la compra", example = "125000")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precioUnitario;

    @Column(name = "subtotal", nullable = false, insertable = false, updatable = false)
    @Schema(description = "Subtotal calculado (cantidad * precio_unitario)", example = "1250000", accessMode = Schema.AccessMode.READ_ONLY)
    private Double subtotal;

}
