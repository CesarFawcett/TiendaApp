package edu.unimag.dto;

import edu.unimag.entities.Cliente;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteMapper {

    public ClienteDto toClienteDto(Cliente cliente) {
        return new ClienteDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getDireccion()
        );
    }

    public Cliente toCliente(ClienteCreateDto clienteCreateDto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteCreateDto.getNombre());
        cliente.setEmail(clienteCreateDto.getEmail());
        cliente.setTelefono(clienteCreateDto.getTelefono());
        cliente.setDireccion(clienteCreateDto.getDireccion());
        return cliente;
    }
     public List<ClienteDto> toDtoList(List<Cliente> clientess) {
        List<ClienteDto> clienteDtos = new ArrayList<>();
        for (Cliente clientes : clientess) {
            clienteDtos.add(toClienteDto(clientes));
        }
        return clienteDtos;
    }
}
