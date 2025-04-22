package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa las categorías de clasificación para los productos.
 * Cada producto debe estar asociado a una categoría para facilitar su organización,
 * búsqueda y presentación en el sistema.
 * NOTA:
 * Las categorías permiten agrupar productos similares bajo una estructura jerárquica.
 */

@Entity
@Table(name = "categorias")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Categoria", description = "Clasificación jerárquica para agrupar productos similares")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autogenerado de la categoría", example = "1")
    private Long id;

    @Schema(description = "Nombre único de la categoría", example = "Granos")
    @Column(unique = true, nullable = false, length = 50)
    private String nombre;

    @Schema(description = "Descripción detallada de la categoría y tipo de productos que incluye", example = "Productos derivados de cereales como arroz, frijol, lentejas y similares")
    private String descripcion;

}