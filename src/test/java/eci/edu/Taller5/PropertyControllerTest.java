package eci.edu.Taller5;


import com.fasterxml.jackson.databind.ObjectMapper;

import eci.edu.Taller5.Property.Property;
import eci.edu.Taller5.Property.PropertyController;
import eci.edu.Taller5.Property.PropertyService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PropertyService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void create_returns201() throws Exception {
        Property p = sample();
        p.setId(1L);
        when(service.createProperty(any())).thenReturn(p);

        mvc.perform(post("/api/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAll_returnsList() throws Exception {
        when(service.getAllProperties()).thenReturn(List.of(sample()));
        mvc.perform(get("/api/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("Addr"));
    }

    @Test
    void getOne_found() throws Exception {
        Property p = sample();
        p.setId(1L);
        when(service.getPropertyById(1L)).thenReturn(Optional.of(p));
        mvc.perform(get("/api/properties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("Addr"));
    }

    @Test
    void update_success() throws Exception {
        Property p = sample();
        p.setId(1L);
        when(service.updateProperty(eq(1L), any())).thenReturn(p);

        mvc.perform(put("/api/properties/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("Addr"));
    }

    @Test
    void delete_noContent() throws Exception {
        mvc.perform(delete("/api/properties/1"))
                .andExpect(status().isNoContent());
    }

    private Property sample() {
        Property p = new Property();
        p.setAddress("Addr");
        p.setPrice(100.0);
        p.setSize(40.0);
        p.setDescription("desc");
        return p;
    }
}

