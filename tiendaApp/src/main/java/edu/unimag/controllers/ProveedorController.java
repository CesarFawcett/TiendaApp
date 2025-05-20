package edu.unimag.controllers;

import edu.unimag.dto.ProveedorDto;
import edu.unimag.dto.ProveedorCreateDto;
import edu.unimag.dto.ProveedorMapper;
import edu.unimag.entities.Proveedor;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.services.ProveedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final ProveedorMapper proveedorMapper;

    public ProveedorController(ProveedorService proveedorService, ProveedorMapper proveedorMapper) {
        this.proveedorService = proveedorService;
        this.proveedorMapper = proveedorMapper;
    }

    @PostConstruct
    public void initSampleProveedores() {
    // Datos de proveedores de ejemplo
    String[][] proveedoresData = {
        {"Distribuidora Nacional S.A.", "Carlos Andrade", "+57 6012345678", "ventas@distrinacional.com", "Calle 100 #11-22, Bogotá"},
        {"Alimentos Frescos Ltda.", "María Fernanda Ruiz", "+57 6023456789", "contacto@alimentosfrescos.com", "Carrera 45 #67-89, Medellín"},
        {"Graneros del Valle", "Jorge Mendoza", "+57 6034567890", "pedidos@granerosdelvalle.com", "Avenida 6N #23-45, Cali"},
        {"Lácteos La Pradera", "Ana Belén Castro", "+57 6045678901", "info@lacteoslapradera.com", "Diagonal 25 #40-50, Bogotá"},
        {"Carnes Premium", "Roberto Salazar", "+57 6056789012", "ventas@carnespremium.com", "Transversal 34 #56-78, Barranquilla"},
        {"Frutas Tropicales S.A.S.", "Luisa Fernanda Gómez", "+57 6067890123", "administracion@frutastropicales.com", "Calle 80 #90-12, Cartagena"}
    };

    for (String[] data : proveedoresData) {
        try {
            ProveedorCreateDto proveedorCreateDto = new ProveedorCreateDto();
            proveedorCreateDto.setNombre(data[0]);
            proveedorCreateDto.setContacto(data[1]);
            proveedorCreateDto.setTelefono(data[2]);
            proveedorCreateDto.setEmail(data[3]);
            proveedorCreateDto.setDireccion(data[4]);
            
            Proveedor proveedor = proveedorMapper.toProveedor(proveedorCreateDto);
            proveedorService.create(proveedor);
        } catch (Exception e) {
            System.err.println("Error creando proveedor: " + data[0] + " - " + e.getMessage());
        }
    }
    }

    //Crear un nuevo proveedor
    @PostMapping
    public ResponseEntity<ProveedorDto> createProveedor(@Valid @RequestBody ProveedorCreateDto proveedorCreateDto) {
        Proveedor proveedor = proveedorMapper.toProveedor(proveedorCreateDto);
        Proveedor createdProveedor = proveedorService.create(proveedor);
        ProveedorDto proveedorDto = proveedorMapper.toProveedorDto(createdProveedor);
        return new ResponseEntity<>(proveedorDto, HttpStatus.CREATED);
    }

    //Obtener todos los proveedores
    @GetMapping
    public ResponseEntity<List<ProveedorDto>> getAllProveedores() {
        List<Proveedor> proveedores = proveedorService.findAll();
        List<ProveedorDto> proveedorDtos = proveedorMapper.toDtoList(proveedores);
        return ResponseEntity.ok(proveedorDtos);
    }

    //Obtener un proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDto> getProveedorById(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.findById(id);
        if (proveedor.isPresent()) {
            ProveedorDto proveedorDto = proveedorMapper.toProveedorDto(proveedor.get());
            return ResponseEntity.ok(proveedorDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Actualizar un proveedor
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDto> updateProveedor(@PathVariable Long id, @Valid @RequestBody ProveedorCreateDto proveedorCreateDto) {
        Proveedor proveedor = proveedorMapper.toProveedor(proveedorCreateDto);
        try {
            Proveedor updatedProveedor = proveedorService.update(id, proveedor);
            ProveedorDto proveedorDto = proveedorMapper.toProveedorDto(updatedProveedor);
            return ResponseEntity.ok(proveedorDto);
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Eliminar un proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
