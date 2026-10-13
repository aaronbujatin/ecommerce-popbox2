package org.xyz.productsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.xyz.productsvc.entity.Product;
import org.xyz.productsvc.repository.projections.ProductProjection;
import org.xyz.productsvc.repository.dto.ProductCartRespRecord;
import org.xyz.productsvc.repository.projections.ProductCartRespProjection;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

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

    @Query(value =
            """
            SELECT
                    p.id AS id,
                    p.name AS name,
                    p.is_series AS "isSeries",
                    p.description AS description,
                    p.images AS images,
                    p.image_contents AS "imageContents",
                    p.category_id AS "categoryId",
                    (
                        SELECT COALESCE(
                            JSON_AGG(
                                JSON_BUILD_OBJECT(
                                    'id', pu2.id,
                                    'price', pu2.price,
                                    'stockQty', pu2.stock_qty,
                                    'type', pu2.type
                                )
                            ),
                            '[]'::json
                        )
                        FROM product_unit pu2
                        WHERE pu2.product_id = p.id
                    ) AS "productUnits"
            FROM product p
            WHERE (:categoryId IS NULL OR p.category_id = :categoryId) AND
                  (:productId IS NULL OR p.id = :productId)
            GROUP BY p.id
            """,
            nativeQuery = true

    )
    List<ProductProjection> findProductByFilter(
            @Param("categoryId") Long categoryId,
            @Param("productId") Long productId
    );


}
