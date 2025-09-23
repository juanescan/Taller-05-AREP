package eci.edu.Taller5;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import eci.edu.Taller5.Property.Property;
import eci.edu.Taller5.Property.PropertyRepository;
import eci.edu.Taller5.Property.PropertyService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PropertyServiceTest {

    @Mock
    private PropertyRepository repo;

    @InjectMocks
    private PropertyService service;

    PropertyServiceTest() { MockitoAnnotations.openMocks(this); }

    @Test
    void createProperty_saves() {
        Property p = sample();
        when(repo.save(p)).thenReturn(p);
        assertThat(service.createProperty(p)).isEqualTo(p);
    }

    @Test
    void getPropertyById_found() {
        Property p = sample();
        when(repo.findById(1L)).thenReturn(Optional.of(p));
        assertThat(service.getPropertyById(1L)).contains(p);
    }

    @Test
    void updateProperty_updatesFields() {
        Property existing = sample();
        existing.setId(1L);
        Property changes = sample();
        changes.setAddress("New");
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Property.class))).thenAnswer(a -> a.getArgument(0));

        Property updated = service.updateProperty(1L, changes);
        assertThat(updated.getAddress()).isEqualTo("New");
    }

    @Test
    void updateProperty_notFoundThrows() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> service.updateProperty(99L, sample()));
    }

    @Test
    void getAllPropertiesPaged_returnsPage() {
        when(repo.findAll(PageRequest.of(0,5)))
                .thenReturn(new PageImpl<>(List.of(sample())));
        Page<Property> page = service.getAllPropertiesPaged(0,5);
        assertThat(page.getContent()).hasSize(1);
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


