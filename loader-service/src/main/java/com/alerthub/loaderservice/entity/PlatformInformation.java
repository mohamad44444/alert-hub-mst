package com.alerthub.loaderservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "platform_information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatformInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @NotBlank(message = "Owner id is required")
    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @NotBlank(message = "Project is required")
    @Column(nullable = false)
    private String project;

    private String tag;

    private String label;

    @Column(name = "developer_id")
    private String developerId;

    @Column(name = "task_number")
    private String taskNumber;

    private String environment;

    @Column(name = "user_story")
    private String userStory;

    @Column(name = "task_point")
    private Integer taskPoint;

    private String sprint;

    @NotBlank(message = "Provider is required")
    @Column(nullable = false)
    private String provider;

    @Column(name = "source_file_name")
    private String sourceFileName;

    @PrePersist
    public void prePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }

        if (taskPoint == null) {
            taskPoint = 0;
        }
    }
}