package edu.unimag.controllers;

import edu.unimag.entities.Cliente;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.dto.ClienteDto;
import edu.unimag.dto.ClienteCreateDto;
import edu.unimag.dto.ClienteMapper;
import edu.unimag.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }
    
    @PostConstruct
    public void initSampleClientes() { 
    // Datos de clientes de ejemplo
    String[][] clientesData = {
        {"Juan Pérez", "juan.perez@email.com", "+57 3101234567", "Calle 123 #45-67, Bogotá"},
        {"María Rodríguez", "maria.rod@email.com", "+57 3202345678", "Carrera 56 #12-34, Medellín"},
        {"Carlos Gómez", "c.gomez@email.com", "+57 3153456789", "Avenida 7 #23-45, Cali"},
        {"Ana López", "a.lopez@email.com", "+57 3004567890", "Diagonal 34 #56-78, Barranquilla"},
        {"Pedro Martínez", "p.martinez@email.com", "+57 3185678901", "Transversal 12 #34-56, Cartagena"},
        {"Laura García", "l.garcia@email.com", "+57 3136789012", "Calle 78 #90-12, Bucaramanga"},
        {"Jorge Ramírez", "j.ramirez@email.com", "+57 3177890123", "Carrera 45 #67-89, Pereira"},
        {"Sofía Herrera", "s.herrera@email.com", "+57 3148901234", "Avenida 30 #40-50, Manizales"},
        {"Diego Castro", "d.castro@email.com", "+57 3129012345", "Calle 100 #11-22, Cúcuta"}
    };

    for (String[] data : clientesData) {
        try {
            ClienteCreateDto clienteCreateDto = new ClienteCreateDto();
            clienteCreateDto.setNombre(data[0]);
            clienteCreateDto.setEmail(data[1]);
            clienteCreateDto.setTelefono(data[2]);
            clienteCreateDto.setDireccion(data[3]);
            
            Cliente cliente = clienteMapper.toCliente(clienteCreateDto);
            clienteService.create(cliente);
        } catch (Exception e) {
            System.err.println("Error creando cliente: " + data[0] + " - " + e.getMessage());
        }
    }
    }


    //Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<ClienteDto>> getAllClientes() {
        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDto> clienteDto = clienteMapper.toDtoList(clientes);
        return ResponseEntity.ok(clienteDto);
    }

    //Obtener un Cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable Long id) {
        return clienteService.findById(id)
            .map(clienteMapper::toClienteDto)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    //Crear nuevo Cliente
    @PostMapping
    public ResponseEntity<ClienteDto> createCliente(@Valid @RequestBody ClienteCreateDto clienteCreateDto) {
        Cliente cliente = clienteMapper.toCliente(clienteCreateDto);
        Cliente createCliente = clienteService.create(cliente);
        ClienteDto clienteDto = clienteMapper.toClienteDto(createCliente);
        return new ResponseEntity<>(clienteDto, HttpStatus.CREATED); 
    }

    //Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteCreateDto clienteCreateDto) {
        try {
            Cliente cliente = clienteMapper.toCliente(clienteCreateDto);
            Cliente updatedCliente = clienteService.update(id, cliente);
            return ResponseEntity.ok(clienteMapper.toClienteDto(updatedCliente));
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); 
        }
    }

    //Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
