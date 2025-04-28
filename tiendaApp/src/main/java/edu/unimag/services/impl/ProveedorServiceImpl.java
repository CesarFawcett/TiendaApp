package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.unimag.entities.Proveedor;
import edu.unimag.repositories.ProveedorRepository;
import edu.unimag.services.ProveedorService;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();    
    }

    @Override
    public Proveedor create(Proveedor newProveedor) {
        return proveedorRepository.save(newProveedor);
    }

    @Override
    public Optional<Proveedor> findById(Long id) {
       return proveedorRepository.findById(id);
    }

    @Override
    public Proveedor update(Long id, Proveedor newProveedor) {
       Optional<Proveedor> existingProveedor = proveedorRepository.findById(id);
       if (existingProveedor.isPresent()){
        Proveedor proveedorToUpdate = existingProveedor.get();
        proveedorToUpdate.setNombre(newProveedor.getNombre());
        proveedorToUpdate.setContacto(newProveedor.getContacto());
        proveedorToUpdate.setTelefono(newProveedor.getTelefono());
        proveedorToUpdate.setEmail(newProveedor.getEmail());
        proveedorToUpdate.setDireccion(newProveedor.getDireccion());
        proveedorToUpdate.setOrdenesCompra(newProveedor.getOrdenesCompra());

        return proveedorRepository.save(proveedorToUpdate);
       }else {
        return null;
       }
    }

    @Override
    public void delete(Long id) {
       proveedorRepository.deleteById(id);
    }
}
