package edu.unimag.controllers;

import edu.unimag.dto.OrdenCompraDto;
import edu.unimag.dto.OrdenCompraCreateDto;
import edu.unimag.dto.OrdenCompraMapper;
import edu.unimag.entities.OrdenCompra;
import edu.unimag.entities.Proveedor;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.services.OrdenCompraService;
import edu.unimag.services.ProveedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordenes-compra")
public class OrdenCompraController {

    private final OrdenCompraService ordenCompraService;
    private final ProveedorService proveedorService;
    private final OrdenCompraMapper ordenCompraMapper;

    public OrdenCompraController(OrdenCompraService ordenCompraService, ProveedorService proveedorService, OrdenCompraMapper ordenCompraMapper) {
        this.ordenCompraService = ordenCompraService;
        this.proveedorService = proveedorService;
        this.ordenCompraMapper = ordenCompraMapper;
    }

    //  Crear una nueva orden de compra
    @PostMapping
    public ResponseEntity<OrdenCompraDto> createOrdenCompra(@Valid @RequestBody OrdenCompraCreateDto ordenCompraCreateDto) {
        //  Primero, busca al proveedor para asegurarte de que existe
        Optional<Proveedor> proveedor = proveedorService.findById(ordenCompraCreateDto.getProveedorId());
        if (proveedor.isEmpty()) {
            throw new EntidadNoEncontradaException("Proveedor", ordenCompraCreateDto.getProveedorId());
        }

        OrdenCompra ordenCompra = ordenCompraMapper.toOrdenCompra(ordenCompraCreateDto);
        ordenCompra.setProveedor(proveedor.get());  //  Asocia el proveedor a la orden
        OrdenCompra createdOrdenCompra = ordenCompraService.create(ordenCompra);
        OrdenCompraDto ordenCompraDto = ordenCompraMapper.toOrdenCompraDto(createdOrdenCompra);
        return new ResponseEntity<>(ordenCompraDto, HttpStatus.CREATED);
    }

    //  Obtener todas las órdenes de compra
    @GetMapping
    public ResponseEntity<List<OrdenCompraDto>> getAllOrdenesCompra() {
        List<OrdenCompra> ordenesCompra = ordenCompraService.findAll();
        List<OrdenCompraDto> ordenCompraDtos = ordenCompraMapper.toDtoList(ordenesCompra);
        return ResponseEntity.ok(ordenCompraDtos);
    }

    //  Obtener una orden de compra por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraDto> getOrdenCompraById(@PathVariable Long id) {
        Optional<OrdenCompra> ordenCompra = ordenCompraService.findById(id);
        if (ordenCompra.isPresent()) {
            OrdenCompraDto ordenCompraDto = ordenCompraMapper.toOrdenCompraDto(ordenCompra.get());
            return ResponseEntity.ok(ordenCompraDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //  Actualizar una orden de compra
    @PutMapping("/{id}")
    public ResponseEntity<OrdenCompraDto> updateOrdenCompra(@PathVariable Long id, @Valid @RequestBody OrdenCompraCreateDto ordenCompraCreateDto) {
        //  Primero, busca al proveedor para asegurarte de que existe
        Optional<Proveedor> proveedor = proveedorService.findById(ordenCompraCreateDto.getProveedorId());
        if (proveedor.isEmpty()) {
            throw new EntidadNoEncontradaException("Proveedor", ordenCompraCreateDto.getProveedorId());
        }

        OrdenCompra ordenCompra = ordenCompraMapper.toOrdenCompra(ordenCompraCreateDto);
        ordenCompra.setProveedor(proveedor.get());  //  Asocia el proveedor a la orden
        try {
            OrdenCompra updatedOrdenCompra = ordenCompraService.update(id, ordenCompra);
            OrdenCompraDto ordenCompraDto = ordenCompraMapper.toOrdenCompraDto(updatedOrdenCompra);
            return ResponseEntity.ok(ordenCompraDto);
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //  Eliminar una orden de compra
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdenCompra(@PathVariable Long id) {
        ordenCompraService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //  faltan metodos
    //  TODO:  Agregar detalles a una orden de compra (POST /ordenes-compra/{id}/detalles)
    //  TODO:  Obtener los detalles de una orden de compra (GET /ordenes-compra/{id}/detalles)
    //  TODO:  Cambiar el estado de una orden de compra (PATCH /ordenes-compra/{id}/estado)
    //  ...
}