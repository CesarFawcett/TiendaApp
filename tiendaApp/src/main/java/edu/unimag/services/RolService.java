package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.Rol;

public interface RolService {
    List<Rol> findAll();
    Optional<Rol> findByName(String name); 
}
