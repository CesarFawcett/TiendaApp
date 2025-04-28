package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.Proveedor;

public interface ProveedorService {
    
    List<Proveedor> findAll();
    Proveedor create (Proveedor newProveedor);
    Optional<Proveedor> findById(Long id);
    Proveedor update(Long id,Proveedor newProveedor);
    void delete(Long id);
}
