package edu.unimag.controllers;

import edu.unimag.dto.ProductoCreateDto;
import edu.unimag.dto.ProductoDto;
import edu.unimag.entities.Producto;
import edu.unimag.entities.Categoria;
import edu.unimag.repositories.ProductoRepository;
import edu.unimag.repositories.CategoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Categoria categoria1;
    private Producto producto1;

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll();
        categoriaRepository.deleteAll();

        categoria1 = new Categoria(null, "Test Category", "Test Description");
        categoria1 = categoriaRepository.save(categoria1);

        producto1 = new Producto(null, "Test Product", "Test Description", 10.0, 5, LocalDate.now(), categoria1);
        producto1 = productoRepository.save(producto1);
    }

    @Test
    void getAllProductos_ReturnsListOfProductoDtos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre", is("Test Product")));
    }

    @Test
    void getProductoById_ReturnsProductoDto_WhenProductoExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/productos/{id}", producto1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Test Product")));
    }

    @Test
    void getProductoById_Returns404NotFound_WhenProductoDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/productos/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createProducto_Returns201Created_WhenProductoIsCreated() throws Exception {
        ProductoCreateDto newProductoDto = new ProductoCreateDto("New Product", "New Description", 20.0, 10, LocalDate.now(), categoria1.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductoDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("New Product")));
    }

    @Test
    void updateProducto_Returns200OK_WhenProductoIsUpdated() throws Exception {
         ProductoCreateDto updateProductoDto = new ProductoCreateDto("Updated Product", "Updated Description", 25.0, 15, LocalDate.now(), categoria1.getId());

        mockMvc.perform(MockMvcRequestBuilders.put("/productos/{id}", producto1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductoDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Updated Product")));
    }

    @Test
    void updateProducto_Returns404NotFound_WhenProductoDoesNotExist() throws Exception {
        ProductoCreateDto updateProductoDto = new ProductoCreateDto("Updated Product", "Updated Description", 25.0, 15, LocalDate.now(), categoria1.getId());

        mockMvc.perform(MockMvcRequestBuilders.put("/productos/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductoDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteProducto_Returns204NoContent_WhenProductoIsDeleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/productos/{id}", producto1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/productos/{id}", producto1.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}