package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 
 * 
 */

@Entity
@Table(name = "clientes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Cliente", description = "Entidad que registra a los clientes")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado", example = "1")
    private Long id;

    @Schema(description = "Nombre del cliente", example = "Andres Leon.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Schema(description = "nombre de correo", example = "AndresLeon@gmail.com")
    @Column(nullable = false)
    private String correo;

    @Schema(description = "Teléfono de contacto", example = "+57 3101234567")
    @Pattern(regexp = "^\\+?[0-9\\s-]{10,}$", message = "Teléfono inválido")
    @Column(nullable = false)
    private String telefono;

    @Schema(description = "Dirección física ", example = "Calle 123 #45-67, Bogotá")
    @Column(nullable = false)
    private String direccion;

}
