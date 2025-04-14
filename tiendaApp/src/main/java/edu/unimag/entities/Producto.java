package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Representa un producto .
 * <p>
 * Contiene información de los productos.
 */

@Entity
@Table(name = "productos")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Producto", description = "Entidad representante los productos de la tienda")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "nombre completo", example = "azucar refinada")
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Schema(description = "Descripción del producto", example = "Azúcar refinada en paquete de 1kg")
    @Column(length = 200)
    private String descripcion;

    @Schema(description = "Precio unitario del producto", example = "5000")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;

    @Schema(description = "Cantidad disponible en inventario", example = "30")
    @Positive(message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    private Integer stock;

    
    @Schema(description = "Categoría a la que pertenece el producto", example = "Granos")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Long categoria;

    //Método para actualizar stock
    public void reducirStock(Integer cantidad) {
        if (cantidad > this.stock) {
            throw new IllegalArgumentException("No hay suficiente stock disponible");
        }
        this.stock -= cantidad;
    }

}
