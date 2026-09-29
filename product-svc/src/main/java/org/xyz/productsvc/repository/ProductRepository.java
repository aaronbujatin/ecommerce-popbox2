package org.xyz.productsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.xyz.productsvc.entity.Product;
import org.xyz.productsvc.repository.dto.ProductCartRespRecord;
import org.xyz.productsvc.repository.projections.ProductCartRespProjection;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value =
            """
            SELECT
                pu.id,
                pu.product_id,
                p.images,
                p.name,
                pu.price,
                pu.product_unit_type,
                pu.stock,
                p.description
            FROM public.product_unit pu
            JOIN public.product p ON pu.product_id = p.id
            WHERE pu.id IN :productUnitIds
            ORDER BY pu.id ASC
            """,
            nativeQuery = true
    )
    List<ProductCartRespProjection> findAllProductUnitById(@Param("productUnitIds") List<Long> productUnitIds);

    @Query(value =
            """
            SELECT
                pu.id,
                pu.product_id,
                p.images,
                p.name,
                pu.price,
                pu.product_unit_type,
                pu.stock,
                p.description
            FROM public.product_unit pu
            JOIN public.product p ON pu.product_id = p.id
            WHERE pu.id = :productUnitId
            """,
            nativeQuery = true
    )
    Optional<ProductCartRespProjection> findProductUnitById(@Param("productUnitId") Long productUnitId);

    @Query(value =
            """
            SELECT pu.id as id,
                   pu.product_id as productId,
                   p.name as name,
                   pu.price as price,
                   pu.product_unit_type as productUnitType,
                   pu.stock as stock,
                   p.description as description,
                   p.images as images
            FROM public.product_unit pu
            JOIN public.product p ON pu.product_id = p.id
            WHERE pu.id = :productUnitId
            """,
            nativeQuery = true
        )
    Optional<ProductCartRespRecord> findProductUnitByIdRecord(@Param("productUnitId") Long productUnitId);


}
