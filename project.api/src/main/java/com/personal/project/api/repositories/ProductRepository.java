package com.personal.project.api.repositories;

import com.personal.project.api.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "SELECT * FROM sch_application.product WHERE price_in_cents BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Product> findByRangeOfPrices(Integer price1, Integer price2);

    List<Product> findAllByActiveTrue();
}
