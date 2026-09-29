package org.xyz.productsvc.security;

//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.authority.SimpleGrantedAuthority;
////import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//@Component
//@RequiredArgsConstructor
//public class HeaderAuthFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        var customerEmail = request.getHeader("X-Customer-Email");
//        var customerRoles = request.getHeader("X-Customer-Roles");
//
//        if (customerEmail != null && customerRoles != null) {
//            var authorities = Arrays.stream(customerRoles.split(", "))
//                    .map(String::trim)
//                    .map(SimpleGrantedAuthority::new)
//                    .toList();
//
//            var auth = new UsernamePasswordAuthenticationToken(
//                    customerEmail, null, authorities
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
