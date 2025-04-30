package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.Rol;

public interface RolService {
    List<Rol> findAll();
    Rol create(Rol newRol);
    Optional<Rol> finById(Long id);
    Rol update(Long id, Rol newRol);
    void delete(long id);
}
