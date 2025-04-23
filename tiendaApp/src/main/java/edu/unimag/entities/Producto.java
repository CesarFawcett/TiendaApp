package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa los productos disponibles para compra y venta en el sistema.
 * Contiene información detallada del producto, incluyendo su precio, existencias en
 * inventario, fecha de vencimiento y la categoría a la que pertenece.
 * NOTA:
 * Esta entidad es fundamental para el módulo de inventario y para las transacciones
 * de compra a proveedores y venta a clientes.
 */

@Entity
@Table(name = "productos", indexes = {
    @Index(name = "idx_producto_nombre", columnList = "nombre"),
    @Index(name = "idx_producto_categoria", columnList = "categoria_id")
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Producto", description = "Artículo disponible para compra o venta en el sistema")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autogenerado del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre comercial completo del producto", example = "Azúcar Refinada Marca Dulce")
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Schema(description = "Descripción detallada del producto y sus características", example = "Azúcar refinada en paquete de 1kg, granulado fino ideal para repostería")
    @Column(length = 200)
    private String descripcion;

    @Schema(description = "Precio unitario de venta al público", example = "5000")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;

    @Schema(description = "Cantidad de unidades disponibles en inventario", example = "30")
    @Column(nullable = false)
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(description = "Fecha de vencimiento o caducidad del producto", example = "2025-12-15")
    @Column(nullable = false)
    @FutureOrPresent(message = "La fecha no puede ser anterior a hoy")
    private LocalDate fecha;

    @Schema(description = "Categoría a la que pertenece el producto para su clasificación", example = "Granos")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
