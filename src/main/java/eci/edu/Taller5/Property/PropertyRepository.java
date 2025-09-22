package eci.edu.Taller5.Property;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Property> findBySizeBetween(Double minSize, Double maxSize);
    List<Property> findByAddressContainingIgnoreCase(String address);
	
}
