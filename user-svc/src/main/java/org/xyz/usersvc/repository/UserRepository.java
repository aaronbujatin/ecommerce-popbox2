package org.xyz.usersvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xyz.usersvc.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


//    @Query(value =
//            """
//            SELECT
//                 c.email,
//                 c.password,
//                 string_agg(r.name, ', ') AS roles
//            FROM public.customer c
//            JOIN public.customer_role c_role ON c.id = c_role.customer_id
//            JOIN public.role r ON c_role.role_id = r.id
//            WHERE c.email = :email
//            GROUP BY c.password, c.email
//            """,
//            nativeQuery = true
//    )
//    Optional<AuthCustomerProjection> findAuthCustomerInfoByEmail(@Param("email") String email);

}
