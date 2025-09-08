package com.gym.member_microservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "members")
@Schema(description = "Entity that represents a gym member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the member", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Full name of the member", example = "Anna Lopez", required = true)
    private String name;

    @Column(nullable = false, unique = true)
    @Schema(description = "Member email", example = "anna.lopez@email.com", required = true)
    private String email;

    @Column(name = "registration_date")
    @Schema(description = "Member registration date", example = "2024-01-15")
    private LocalDate registrationDate;
}
