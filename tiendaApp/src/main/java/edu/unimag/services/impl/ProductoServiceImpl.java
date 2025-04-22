package edu.unimag.services.impl;

import java.util.List;

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
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto create(Producto newProducto) {
      return productoRepository.save(newProducto);
    }
    
}
