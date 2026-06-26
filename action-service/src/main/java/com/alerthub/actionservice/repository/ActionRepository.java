package com.alerthub.actionservice.repository;

import com.alerthub.actionservice.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

    List<Action> findByUserId(Long userId);

    List<Action> findByIsDeletedFalse();

    List<Action> findByUserIdAndIsDeletedFalse(Long userId);
}