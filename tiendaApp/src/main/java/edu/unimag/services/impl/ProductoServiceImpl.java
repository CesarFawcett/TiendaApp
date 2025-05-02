package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.unimag.entities.Producto;
import edu.unimag.repositories.ProductoRepository;
import edu.unimag.services.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
       return productoRepository.findAll();
    }

    @Override
    public Producto create(Producto newProducto) {
      return productoRepository.save(newProducto);
    }

    @Override
    public Optional<Producto> findById(Long id) {
      return productoRepository.findById(id);
    }
    
    @Override
    public Producto update(Long id, Producto newProducto) { 
    Optional<Producto> existingProducto = productoRepository.findById(id);
    if (existingProducto.isPresent()) {
        Producto productoToUpdate = existingProducto.get();
        productoToUpdate.setNombre(newProducto.getNombre()); 
        productoToUpdate.setDescripcion(newProducto.getDescripcion());
        productoToUpdate.setPrecio(newProducto.getPrecio());
        productoToUpdate.setStock(newProducto.getStock());
        productoToUpdate.setFecha(newProducto.getFecha());
        productoToUpdate.setCategoria(newProducto.getCategoria());
        return productoRepository.save(productoToUpdate);
    } else {
        return null;
    }
}

    @Override
    public void delete(Long id) {
      productoRepository.deleteById(id);
    }

    
}
