package eci.edu.Taller5.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Page<Property> getAllPropertiesPaged(int page, int size) {
        return propertyRepository.findAll(PageRequest.of(page, size));
    }
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property propertyDetails) {
        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (propertyDetails.getAddress() != null) {
            existingProperty.setAddress(propertyDetails.getAddress());
        }
        if (propertyDetails.getPrice() != null) {
            existingProperty.setPrice(propertyDetails.getPrice());
        }
        if (propertyDetails.getSize() != null) {
            existingProperty.setSize(propertyDetails.getSize());
        }
        if (propertyDetails.getDescription() != null) {
            existingProperty.setDescription(propertyDetails.getDescription());
        }

        return propertyRepository.save(existingProperty);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
    public Page<Property> getAllProperties(int page, int size) {
        return propertyRepository.findAll(PageRequest.of(page, size));
    }
    public List<Property> searchProperties(String address, Double minPrice, Double maxPrice, Double minSize, Double maxSize) {
        if (address != null) {
            return propertyRepository.findByAddressContainingIgnoreCase(address);
        } else if (minPrice != null && maxPrice != null) {
            return propertyRepository.findByPriceBetween(minPrice, maxPrice);
        } else if (minSize != null && maxSize != null) {
            return propertyRepository.findBySizeBetween(minSize, maxSize);
        } else {
            return propertyRepository.findAll();
        }
    }
}
