package edu.unimag.controllers;

import edu.unimag.entities.Cliente;
import edu.unimag.entities.DetallesVenta;
import edu.unimag.entities.Rol;
import edu.unimag.entities.Usuario;
import edu.unimag.entities.Venta;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.repositories.ClienteRepository;
import edu.unimag.repositories.UsuarioRepository;
import edu.unimag.repositories.VentaRepository;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final VentaMapper ventaMapper;
    private final DetallesVentaMapper detallesVentaMapper;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;
    

    public VentaController(VentaService ventaService, ClienteService clienteService, UsuarioService usuarioService, VentaMapper ventaMapper,DetallesVentaMapper detallesVentaMapper,
    ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.ventaService = ventaService;
        this.ventaMapper = ventaMapper;
        this.detallesVentaMapper = detallesVentaMapper;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<VentaDto>> getAllVentas() {
    System.out.println("Entrando a getAllVentas"); // Debug 1
    List<Venta> ventas = ventaService.findAll();
    System.out.println("Ventas encontradas: " + ventas.size()); // Debug 2
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
    // Validar que los IDs de cliente y usuario existen
    if (!clienteRepository.existsById(ventaCreateDto.getClienteId())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente no encontrado");
    }
    if (!usuarioRepository.existsById(ventaCreateDto.getUsuarioId())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado");
    }

    Venta venta = ventaMapper.toVenta(ventaCreateDto);
    Venta createdVenta = ventaService.create(venta);
    return new ResponseEntity<>(ventaMapper.toVentaDto(createdVenta), HttpStatus.CREATED);
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
