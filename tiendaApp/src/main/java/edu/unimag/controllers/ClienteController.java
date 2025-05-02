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
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }
    
    //Obtener todas los clientes
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
