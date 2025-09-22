package eci.edu.Taller5.Property;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    private final PropertyService svc;
    public PropertyController(PropertyService svc){ this.svc = svc; }

    @PostMapping
    public ResponseEntity<Property> create(@RequestBody @Validated Property prop){
        Property saved = svc.createProperty(prop);                // ✅
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Property> all(){ return svc.getAllProperties(); } // ✅

    @GetMapping("/{id}")
    public ResponseEntity<Property> one(@PathVariable Long id){
        return svc.getPropertyById(id)                            // ✅
                  .map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> update(@PathVariable Long id,
                                           @RequestBody @Validated Property prop){
        try {
            return ResponseEntity.ok(svc.updateProperty(id, prop)); // ✅
        } catch (RuntimeException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        svc.deleteProperty(id);                                     // ✅
        return ResponseEntity.noContent().build();
    }
}
