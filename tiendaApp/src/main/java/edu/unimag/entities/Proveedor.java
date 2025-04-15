package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 
 * 
 */

@Entity
@Table(name = "proveedores")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Proveedor", description = "Entidad que representa los proveedores con los que se cuentan")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del proveedor", example = "1")
    private Long id;

    @Schema(description = "Nombre del proveedor", example = "Distribuidora S.A.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Schema(description = "Persona de contacto", example = "Juan Pérez")
    @Column(nullable = false)
    private String contacto;

    @Schema(description = "Teléfono de contacto", example = "+57 3101234567")
    @Pattern(regexp = "^\\+?[0-9\\s-]{10,}$", message = "Teléfono inválido")
    @Column(nullable = false)
    private String telefono;

    @Schema(description = "Correo electrónico Gmail", example = "usuario@gmail.com")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Debe ser un correo electrónico válido de Gmail")
    @Column(nullable = false)
    private String email;

    @Schema(description = "Dirección física del proveedor", example = "Calle 123 #45-67, Bogotá")
    @Column(nullable = false)
    private String direccion;

    // Relación con órdenes de compra
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdenCompra> ordenesCompra;

}
