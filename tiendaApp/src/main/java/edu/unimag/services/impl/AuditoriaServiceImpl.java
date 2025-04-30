package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unimag.entities.Auditoria;
import edu.unimag.repositories.AuditoriaRepository;
import edu.unimag.services.AuditoriaService;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Override
    public List<Auditoria> findAll() {
        return auditoriaRepository.findAll();
       }

    @Override
    public Auditoria create(Auditoria newAuditoria) {
        return auditoriaRepository.save(newAuditoria);
       }

    @Override
    public Optional<Auditoria> findById(Long id) {
        return auditoriaRepository.findById(id);
    }

    @Override
    public Auditoria update(Long id, Auditoria newAuditoria) {
        Optional<Auditoria> existingAuditoria = auditoriaRepository.findById(id);
        if (existingAuditoria.isPresent()){
            Auditoria auditoriaToUpdate = existingAuditoria.get();
            auditoriaToUpdate.setAccion(newAuditoria.getAccion());
            auditoriaToUpdate.setEntidadAfectada(newAuditoria.getEntidadAfectada());
            auditoriaToUpdate.setIdEntidad(newAuditoria.getIdEntidad());
            auditoriaToUpdate.setFecha(newAuditoria.getFecha());
            auditoriaToUpdate.setDetalle(newAuditoria.getDetalle());
            return auditoriaRepository.save(auditoriaToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        auditoriaRepository.deleteById(id);
    }
}
