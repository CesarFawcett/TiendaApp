package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 
 * 
 */

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Usuario", description = "Entidad que representa a un usuario del sistema")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado", example = "1")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "Correo electrónico único", example = "juan@example.com")
    private String correo;

    @Column(nullable = false, length = 200)
    @Schema(description = "Contraseña encriptada", example = "$2a$10$xyz...")
    private String contraseña;

    @Column(length = 20)
    @Schema(description = "Rol del usuario", example = "ADMIN")
    private String rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Venta> ventas = new ArrayList<>();
    
}
