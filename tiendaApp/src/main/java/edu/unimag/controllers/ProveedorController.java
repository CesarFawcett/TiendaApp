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
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final ProveedorMapper proveedorMapper;

    public ProveedorController(ProveedorService proveedorService, ProveedorMapper proveedorMapper) {
        this.proveedorService = proveedorService;
        this.proveedorMapper = proveedorMapper;
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
