package edu.unimag.controllers.testIntegration;

import edu.unimag.dto.CategoriaCreateDto;
import edu.unimag.entities.Categoria;
import edu.unimag.repositories.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper; 

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoriaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Categoria categoria1;
    private Categoria categoria2;

    @BeforeEach
    void setUp() {
        categoriaRepository.deleteAll(); 

        categoria1 = new Categoria(null, "Frutas", "Frutas frescas");
        categoria2 = new Categoria(null, "Verduras", "Verduras de hoja verde");

        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);
    }

    @Test
    void getAllCategorias_ReturnsListOfCategoriaDtos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre", is("Frutas")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre", is("Verduras")));
    }

    @Test
    void getCategoriaById_ReturnsCategoriaDto_WhenCategoriaExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/{id}", categoria1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Frutas")));
    }

    @Test
    void getCategoriaById_Returns404NotFound_WhenCategoriaDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/{id}", 999L) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createCategoria_Returns201Created_WhenCategoriaIsCreated() throws Exception {
        CategoriaCreateDto newCategoriaDto = new CategoriaCreateDto("Lácteos", "Productos lácteos");

        mockMvc.perform(MockMvcRequestBuilders.post("/categoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategoriaDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Lácteos")));
    }

    @Test
    void updateCategoria_Returns200OK_WhenCategoriaIsUpdated() throws Exception {
        CategoriaCreateDto updateCategoriaDto = new CategoriaCreateDto("Frutas Orgánicas", "Frutas orgánicas certificadas");

        mockMvc.perform(MockMvcRequestBuilders.put("/categoria/{id}", categoria1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCategoriaDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Frutas Orgánicas")));
    }

    @Test
    void updateCategoria_Returns404NotFound_WhenCategoriaDoesNotExist() throws Exception {
        CategoriaCreateDto updateCategoriaDto = new CategoriaCreateDto("Bebidas", "Bebidas refrescantes");

        mockMvc.perform(MockMvcRequestBuilders.put("/categoria/{id}", 999L) 
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCategoriaDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteCategoria_Returns204NoContent_WhenCategoriaIsDeleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/categoria/{id}", categoria1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/{id}", categoria1.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
