package com.alerthub.actionservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User id is required")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    /*
     * Store condition as JSON text.
     * Example:
     * [[1,2],[3]]
     */
    @NotBlank(message = "Condition is required")
    @Column(name = "condition_json", nullable = false, columnDefinition = "TEXT")
    private String conditionJson;

    @NotBlank(message = "Recipient is required")
    @Column(name = "recipient", nullable = false)
    private String to;

    @NotNull(message = "Action type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    @NotNull(message = "Run on time is required")
    @Column(name = "run_on_time", nullable = false)
    private LocalTime runOnTime;

    @NotBlank(message = "Run on day is required")
    @Column(name = "run_on_day", nullable = false)
    private String runOnDay;

    @NotBlank(message = "Message is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "last_run")
    private LocalDateTime lastRun;

    @PrePersist
    public void prePersist() {
        if (createDate == null) {
            createDate = LocalDate.now();
        }

        if (isEnabled == null) {
            isEnabled = true;
        }

        if (isDeleted == null) {
            isDeleted = false;
        }

        lastUpdate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}