package com.universidad.tecno.api_gestion_accesorios.controllers;

import com.universidad.tecno.api_gestion_accesorios.auth.SpringSecurityConfig;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryAssignmentCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryWithCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.entities.Category;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.AccessoryService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(AccessoryController.class)
@Import(SpringSecurityConfig.class)
public class AccessoryValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessoryService accessoryService;

    @Test
    @WithMockUser(authorities = { "CREAR_ACCESORIO" })
    void testCreateAccessory_withInvalidData_returnsBadRequestAndErrors() throws Exception {
        String invalidJson = """
                {
                    "name": "",
                    "price": -10,
                    "description": "",
                    "brand": "",
                    "model": "",
                    "categoryId": null
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/accessories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andReturn(); // aqui capturo la respuesta

        String responseBody = result.getResponse().getContentAsString();
        System.out.println(">>> RESPONSE BODY:\n" + responseBody);
    }

    @Test
    @WithMockUser(authorities = { "CREAR_ACCESORIO" })
    void testCreateAccessory_withValidData_returnsCreated() throws Exception {
        String validJson = """
                {
                    "name": "accesorio validacion4",
                    "price": 10.0,
                    "description": "Soporte ajustable validacion4",
                    "brand": "Anker validacion4",
                    "model": "Phone Stand Pro validacion4",
                    "categoryId": 4
                }
                """;

        Accessory mockAccessory = new Accessory();
        mockAccessory.setId(73L);
        mockAccessory.setName("accesorio validacion4");
        mockAccessory.setPrice(10.0);
        mockAccessory.setDescription("Soporte ajustable validacion4");
        mockAccessory.setBrand("Anker validacion4");
        mockAccessory.setModel("Phone Stand Pro validacion4");
        Category category = new Category();
        category.setId(4L);
        category.setName("Soportes y Bases");
        mockAccessory.setCategory(category);

        when(accessoryService.saveDto(any(AccessoryAssignmentCategoryDto.class))).thenReturn(mockAccessory);

        mockMvc.perform(post("/api/accessories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(validJson)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("accesorio validacion4"));
    }

    @Test
    @WithMockUser(authorities = { "EDITAR_ACCESORIO" })
    void testUpdateAccessory_whenExists_returnsUpdatedAccessory() throws Exception {
        Long accessoryId = 73L;

        String updateJson = """
                {
                    "name": "accesorio actualizado",
                    "price": 15.0,
                    "description": "Soporte ajustable actualizado",
                    "brand": "Anker actualizado",
                    "model": "Phone Stand Pro actualizado",
                    "categoryId": 4
                }
                """;

        AccessoryWithCategoryDto updatedAccessory = new AccessoryWithCategoryDto();
        updatedAccessory.setId(accessoryId);
        updatedAccessory.setName("accesorio actualizado");
        updatedAccessory.setPrice(15.0);
        updatedAccessory.setDescription("Soporte ajustable actualizado");
        updatedAccessory.setBrand("Anker actualizado");
        updatedAccessory.setModel("Phone Stand Pro actualizado");
        updatedAccessory.setCategoryName("Soportes y Bases");

        when(accessoryService.update(eq(accessoryId), any(AccessoryAssignmentCategoryDto.class)))
                .thenReturn(Optional.of(updatedAccessory));

        mockMvc.perform(put("/api/accessories/{id}", accessoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateJson)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(accessoryId))
                .andExpect(jsonPath("$.name").value("accesorio actualizado"))
                .andExpect(jsonPath("$.price").value(15.0))
                .andExpect(jsonPath("$.categoryName").value("Soportes y Bases"));
    }

}
