package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.unimag.dto.ProductoMasVendidoDto;
import edu.unimag.entities.Producto;
import edu.unimag.repositories.ProductoRepository;
import edu.unimag.services.ProductoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @PersistenceContext 
    private EntityManager entityManager;
    
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

    @Override
     public List<ProductoMasVendidoDto> findTopSellingProducts(int limit) {
        // Esta es una consulta JPQL de ejemplo. Puede que necesites ajustarla
        // dependiendo de cómo estén modeladas tus relaciones de Ventas y DetallesVenta.
        // Asume que tienes una relación Venta -> DetallesVenta -> Producto y que DetallesVenta
        // tiene una 'cantidad'.

        String jpql = "SELECT NEW edu.unimag.dto.ProductoMasVendidoDto(dp.producto.id, dp.producto.nombre, SUM(dp.cantidad)) " +
              "FROM DetallesVenta dp " +
              "GROUP BY dp.producto.id, dp.producto.nombre " +
              "ORDER BY SUM(dp.cantidad) DESC";

        return entityManager.createQuery(jpql, ProductoMasVendidoDto.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Producto> findByStockLessThan(int stock) {
      return productoRepository.findByStockLessThan(stock);
    }
    
    
}
