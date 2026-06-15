package com.alerthub.securityservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String role;
}