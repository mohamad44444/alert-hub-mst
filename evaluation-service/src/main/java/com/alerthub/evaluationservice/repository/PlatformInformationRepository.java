package com.alerthub.evaluationservice.repository;

import com.alerthub.evaluationservice.dto.DeveloperLabelCountDto;
import com.alerthub.evaluationservice.dto.LabelAggregateDto;
import com.alerthub.evaluationservice.entity.PlatformInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PlatformInformationRepository extends JpaRepository<PlatformInformation, Long> {

    @Query("""
            SELECT new com.alerthub.evaluationservice.dto.DeveloperLabelCountDto(
                p.developerId,
                COUNT(p)
            )
            FROM PlatformInformation p
            WHERE p.label = :label
            AND p.timestamp >= :sinceTime
            GROUP BY p.developerId
            ORDER BY COUNT(p) DESC
            """)
    List<DeveloperLabelCountDto> findDeveloperWithMostLabel(String label, LocalDateTime sinceTime);

    @Query("""
            SELECT new com.alerthub.evaluationservice.dto.LabelAggregateDto(
                p.label,
                COUNT(p)
            )
            FROM PlatformInformation p
            WHERE p.developerId = :developerId
            AND p.timestamp >= :sinceTime
            GROUP BY p.label
            """)
    List<LabelAggregateDto> getLabelAggregateByDeveloper(String developerId, LocalDateTime sinceTime);

    @Query("""
            SELECT COUNT(p)
            FROM PlatformInformation p
            WHERE p.developerId = :developerId
            AND p.timestamp >= :sinceTime
            """)
    Long countTasksByDeveloper(String developerId, LocalDateTime sinceTime);
}