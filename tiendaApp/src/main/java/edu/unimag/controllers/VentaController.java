package edu.unimag.controllers;

import edu.unimag.entities.DetallesVenta;
import edu.unimag.entities.Venta;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.dto.VentaDto;
import edu.unimag.dto.DetallesVentaCreateDto;
import edu.unimag.dto.DetallesVentaDto;
import edu.unimag.dto.DetallesVentaMapper;
import edu.unimag.dto.VentaCreateDto;
import edu.unimag.dto.VentaMapper;
import edu.unimag.services.VentaService;
import edu.unimag.services.ClienteService;
import edu.unimag.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final VentaMapper ventaMapper;
    private final DetallesVentaMapper detallesVentaMapper;
    

    public VentaController(VentaService ventaService, ClienteService clienteService, UsuarioService usuarioService, VentaMapper ventaMapper,DetallesVentaMapper detallesVentaMapper) {
        this.ventaService = ventaService;
        this.ventaMapper = ventaMapper;
        this.detallesVentaMapper = detallesVentaMapper;
    }

    @GetMapping
    public ResponseEntity<List<VentaDto>> getAllVentas() {
        List<Venta> ventas = ventaService.findAll();
        List<VentaDto> ventaDtos = ventaMapper.toDtoList(ventas);
        return ResponseEntity.ok(ventaDtos);
    }

    //Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaDto> getVentaById(@PathVariable Long id) {
        return ventaService.findById(id)
                .map(ventaMapper::toVentaDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "venta no encontrada"));
    }

    //Crear nueva venta
    @PostMapping
    public ResponseEntity<VentaDto> createVenta(@Valid @RequestBody VentaCreateDto ventaCreateDto) {
        Venta venta = ventaMapper.toVenta(ventaCreateDto);
        Venta createdVenta = ventaService.create(venta);
        VentaDto ventaDto = ventaMapper.toVentaDto(createdVenta);
        return new ResponseEntity<>(ventaDto, HttpStatus.CREATED); 
    }

    //Actualizar venta
    @PutMapping("/{id}")
    public ResponseEntity<VentaDto> updateVenta(@PathVariable Long id, @Valid @RequestBody VentaCreateDto ventaCreateDto) {
         try {
            Venta venta = ventaMapper.toVenta(ventaCreateDto);
            Venta updatedVenta = ventaService.update(id, venta);
            return ResponseEntity.ok(ventaMapper.toVentaDto(updatedVenta));
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); 
        }
    }

    //Eliminar venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        ventaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<DetallesVentaDto> addDetalle(@PathVariable Long id, 
                                                   @Valid @RequestBody DetallesVentaCreateDto dto) {
    Venta venta = ventaService.addDetalle(id, dto);
    DetallesVenta nuevoDetalle = venta.getDetalles()
                                .get(venta.getDetalles().size() - 1);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(detallesVentaMapper.toDetallesVentaDto(nuevoDetalle));
    
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<List<DetallesVentaDto>> getDetalles(@PathVariable Long id) {
        List<DetallesVenta> detalles = ventaService.getDetalles(id);
        return ResponseEntity.ok(detallesVentaMapper.toDtoList(detalles));
    } 
}
