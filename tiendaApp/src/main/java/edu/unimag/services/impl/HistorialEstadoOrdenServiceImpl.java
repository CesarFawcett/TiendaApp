package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.unimag.entities.HistorialEstadoOrden;
import edu.unimag.repositories.HistorialEstadoOrdenRepository;
import edu.unimag.services.HistorialEstadoOrdenService;

@Service
public class HistorialEstadoOrdenServiceImpl implements HistorialEstadoOrdenService{

    @Autowired
    private HistorialEstadoOrdenRepository historialEstadoOrdenControllerRepository;
    
    @Override
    public List<HistorialEstadoOrden> findAll() {
        return historialEstadoOrdenControllerRepository.findAll();
    }

    @Override
    public HistorialEstadoOrden create(HistorialEstadoOrden newHistorialEstadoOrden) {
       return historialEstadoOrdenControllerRepository.save(newHistorialEstadoOrden);
    }

    @Override
    public Optional<HistorialEstadoOrden> findById(Long id) {
       return historialEstadoOrdenControllerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        historialEstadoOrdenControllerRepository.deleteById(id);
    }
    
}
