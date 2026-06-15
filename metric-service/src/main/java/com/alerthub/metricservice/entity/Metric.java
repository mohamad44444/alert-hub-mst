package com.alerthub.metricservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User id is required")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Label is required")
    @Column(nullable = false)
    private String label;

    @NotNull(message = "Threshold is required")
    @Column(nullable = false)
    private Double threshold;

    @NotNull(message = "Time frame hours is required")
    @Min(value = 1, message = "Time frame must be at least 1 hour")
    @Column(name = "time_frame_hours", nullable = false)
    private Integer timeFrameHours;
}