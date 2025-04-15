package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 
 * 
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

    @Schema(description = "Fecha de vencimiento ", example = "2025-04-15")
    @Column(nullable = false)
    private LocalDate fecha;

    @Schema(description = "Categoría a la que pertenece el producto", example = "Granos")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

}
