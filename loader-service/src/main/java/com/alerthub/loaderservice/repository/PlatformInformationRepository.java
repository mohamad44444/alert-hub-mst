package com.alerthub.loaderservice.repository;

import com.alerthub.loaderservice.entity.PlatformInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformInformationRepository extends JpaRepository<PlatformInformation, Long> {

    List<PlatformInformation> findByProvider(String provider);

    List<PlatformInformation> findByDeveloperId(String developerId);

    List<PlatformInformation> findByLabel(String label);
}