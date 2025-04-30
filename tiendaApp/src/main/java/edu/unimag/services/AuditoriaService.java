package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.Auditoria;

public interface AuditoriaService {
    List<Auditoria> findAll();
    Auditoria create(Auditoria newAuditoria);
    Optional<Auditoria> findById(Long id);
    Auditoria update(Long id, Auditoria newAuditoria);
    void delete(Long id);
} 
