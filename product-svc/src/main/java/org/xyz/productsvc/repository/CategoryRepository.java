package org.xyz.productsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xyz.productsvc.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
