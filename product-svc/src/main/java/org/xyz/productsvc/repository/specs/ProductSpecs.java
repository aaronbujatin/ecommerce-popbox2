package org.xyz.productsvc.repository.specs;

import org.springframework.data.jpa.domain.Specification;
import org.xyz.productsvc.dto.ProductFilterParam;
import org.xyz.productsvc.entity.Product;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecs {

    public static Specification<Product> withFilter(ProductFilterParam f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (f.categoryId() != null)
                predicates.add(cb.equal(root.get("category").get("id"), f.categoryId()));
            if (f.productId() != null)
                predicates.add(cb.equal(root.get("category").get("id"), f.productId()));
//            if (f.minPrice() != null)
//                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), f.minPrice()));
//            if (f.maxPrice() != null)
//                predicates.add(cb.lessThanOrEqualTo(root.get("price"), f.maxPrice()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
