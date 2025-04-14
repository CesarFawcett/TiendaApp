package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Representa una categoría de productos en el sistema.
 * <p>
 * Permite organizar los productos.
 */

@Entity
@Table(name = "categorias")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Categoria", description = "Entidad que representa las categorías de productos en la tienda")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la categoría", example = "1")
    private Long id;

    @Schema(description = "Nombre de la categoría", example = "Granos")
    @Column(unique = true, nullable = false, length = 50)
    private String nombre;

    @Schema(description = "Descripción opcional de la categoría", example = "Producto derivado de algo")
    private String descripcion;

}