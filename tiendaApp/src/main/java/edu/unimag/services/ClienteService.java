package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.Cliente;

public interface ClienteService {
    List<Cliente> findAll();
    Cliente create(Cliente newCliente);
    Optional<Cliente> findById(Long id);
    Cliente update(Long id, Cliente newCliente);
    void delete(Long id);
}
