package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa a los usuarios que tienen acceso al sistema.
 * Contiene información personal del usuario, credenciales de acceso,
 * nivel de autorización (rol) y el historial de ventas que ha registrado.
 * NOTA:
 * Esta entidad es fundamental para el sistema de autenticación, autorización
 * y para la auditoría de operaciones realizadas en el sistema.
 */

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Usuario", description = "Persona con credenciales para acceder y operar el sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autogenerado del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario para identificación", example = "Juan Pérez Rodríguez")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Schema(description = "Correo electrónico que sirve como identificador único de acceso", example = "usuario@empresa.com")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Debe ser un email válido")
    @Column(nullable = false)
    private String email;

    @Schema(description = "Contraseña encriptada para autenticación segura", example = "$2a$10$xyz...")
    @Column(nullable = false, length = 60)  
    private String contraseña;

    @Schema(description = "Rol que determina los permisos del usuario.)", example = "ADMINISTRADOR")
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Schema(description = "Historial de ventas registradas por este usuario")
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Venta> ventas = new ArrayList<>();
    
    //para security
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
}

}
