
package com.taxfiling.user.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String pan;
    @Column(unique = true)
    private String aadhaar;
    @Column(unique = true)
    private String email;
    private String phone;
    private String passwordHash;
    private LocalDateTime createdAt;
@Enumerated(EnumType.STRING)
private UserRole role = UserRole.USER;

public enum UserRole {
    USER, ADMIN, TAX_PROFESSIONAL
}
}
