package com.alerthub.evaluationservice.entity;

import jakarta.persistence.*;
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
    private Long id;

    private LocalDateTime timestamp;

    @Column(name = "owner_id")
    private String ownerId;

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

    private String provider;

    @Column(name = "source_file_name")
    private String sourceFileName;
}