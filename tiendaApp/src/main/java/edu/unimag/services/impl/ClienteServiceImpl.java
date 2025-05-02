package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import edu.unimag.entities.Cliente;
import edu.unimag.repositories.ClienteRepository;
import edu.unimag.services.ClienteService;

public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
       }

    @Override
    public Cliente create(Cliente newCliente) {
        return clienteRepository.save(newCliente);
       }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
        }

    @Override
    public Cliente update(Long id, Cliente newCliente) {
         Optional<Cliente> existingCliente = clienteRepository.findById(id);
          if (existingCliente.isPresent()) {
              Cliente clienteToUpdate = existingCliente.get();
              clienteToUpdate.setNombre(newCliente.getNombre());
              clienteToUpdate.setEmail(newCliente.getEmail());
              clienteToUpdate.setTelefono(newCliente.getTelefono());
              clienteToUpdate.setDireccion(newCliente.getDireccion());
              
              return clienteRepository.save(clienteToUpdate);
          } else {
              return null;  
          }
      }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
        }
    
}
