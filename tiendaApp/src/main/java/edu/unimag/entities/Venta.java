package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 
 * 
 */

@Entity
@Table(name = "ventas")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Venta", description = "Entidad que registra una transacción de venta")
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado", example = "1")
    private Long id;

    @Schema(description = "Fecha de la venta", example = "2025-04-15")
    @Column(nullable = false)
    private LocalDate fecha;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación con DetalleVenta 
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallesVenta> detalles = new ArrayList<>();
    
}
