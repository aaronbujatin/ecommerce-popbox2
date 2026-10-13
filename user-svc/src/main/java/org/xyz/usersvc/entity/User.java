package org.xyz.usersvc.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Builder
@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
