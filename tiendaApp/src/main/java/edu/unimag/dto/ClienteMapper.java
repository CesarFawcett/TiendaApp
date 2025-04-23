package edu.unimag.dto;

import org.springframework.stereotype.Component;
import edu.unimag.entities.Cliente;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteMapper {
    
    public ClienteDto toClienteDto(Cliente cliente) {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(cliente.getId());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setEmail(cliente.getEmail());
        clienteDto.setTelefono(cliente.getTelefono());
        clienteDto.setDireccion(cliente.getDireccion());
        return clienteDto;
    }
    
    public Cliente toCliente(ClienteCreateDto clienteCreateDto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteCreateDto.getNombre());
        cliente.setEmail(clienteCreateDto.getEmail());
        cliente.setTelefono(clienteCreateDto.getTelefono());
        cliente.setDireccion(clienteCreateDto.getDireccion());
        return cliente;
    }
    
    public List<ClienteDto> toDtoList(List<Cliente> clientes) {
        List<ClienteDto> clienteDtos = new ArrayList<>();
        for (Cliente cliente : clientes) {
            clienteDtos.add(toClienteDto(cliente));
        }
        return clienteDtos;
    }
}