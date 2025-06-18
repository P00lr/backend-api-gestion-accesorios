package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryAssignmentCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryWithCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.AccessoryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccessoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessoryService accessoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = { "VER_ACCESORIO" })
    public void testGetAccessoryById_found() throws Exception {
        Long id = 1L;
        AccessoryWithCategoryDto dto = new AccessoryWithCategoryDto();
        dto.setId(id);

        Mockito.when(accessoryService.findById(id)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/accessories/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @WithMockUser(authorities = { "VER_ACCESORIO" })
    public void testGetAccessoryById_notFound() throws Exception {
        Long id = 99L;
        Mockito.when(accessoryService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/accessories/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = { "CREAR_ACCESORIO" })
    public void testCreateAccessory() throws Exception {
        AccessoryAssignmentCategoryDto dto = new AccessoryAssignmentCategoryDto();
        dto.setId(1L);
        dto.setName("Accesorio prueba");
        dto.setPrice(99.99);
        dto.setDescription("Descripción del accesorio");
        dto.setBrand("MarcaX");
        dto.setModel("ModeloY");
        dto.setCategoryId(1L);

        Accessory accessory = new Accessory();
        accessory.setId(1L);

        Mockito.when(accessoryService.saveDto(Mockito.any(AccessoryAssignmentCategoryDto.class)))
                .thenReturn(accessory);

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/accessories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(authorities = { "EDITAR_ACCESORIO" })
    public void testUpdateAccessory_found() throws Exception {
        Long id = 1L;

        AccessoryAssignmentCategoryDto dto = new AccessoryAssignmentCategoryDto();
        dto.setId(id);
        dto.setName("Actualizado");
        dto.setPrice(150.00);
        dto.setDescription("Descripción actualizada");
        dto.setBrand("MarcaZ");
        dto.setModel("ModeloZ");
        dto.setCategoryId(2L);

        AccessoryWithCategoryDto updatedDto = new AccessoryWithCategoryDto();
        updatedDto.setId(id); // el resultado que se espera

        Mockito.when(accessoryService.update(Mockito.eq(id), Mockito.any(AccessoryAssignmentCategoryDto.class)))
                .thenReturn(Optional.of(updatedDto));

        mockMvc.perform(put("/api/accessories/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))) // JSON válido del DTO
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @WithMockUser(authorities = { "EDITAR_ACCESORIO" })
    public void testUpdateAccessory_notFound() throws Exception {
        Long id = 99L;

        AccessoryAssignmentCategoryDto dto = new AccessoryAssignmentCategoryDto();
        dto.setId(id);
        dto.setName("Accesorio inexistente");
        dto.setPrice(100.0);
        dto.setDescription("No debería existir");
        dto.setBrand("MarcaX");
        dto.setModel("ModeloX");
        dto.setCategoryId(1L);

        Mockito.when(accessoryService.update(Mockito.eq(id), Mockito.any(AccessoryAssignmentCategoryDto.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/accessories/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = { "ELIMINAR_ACCESORIO" })
    public void testDeleteAccessory_found() throws Exception {
        Long id = 1L;
        Mockito.when(accessoryService.deleteById(id)).thenReturn(true);

        mockMvc.perform(delete("/api/accessories/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = { "ELIMINAR_ACCESORIO" })
    public void testDeleteAccessory_notFound() throws Exception {
        Long id = 99L;
        Mockito.when(accessoryService.deleteById(id)).thenReturn(false);

        mockMvc.perform(delete("/api/accessories/{id}", id))
                .andExpect(status().isNotFound());
    }

}
