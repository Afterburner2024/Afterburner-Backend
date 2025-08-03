package com.afterburner.studygroup.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afterburner.studygroup.model.entity.StudyGroupEntity;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Integer> {
    @EntityGraph(attributePaths = "user")
    java.util.List<StudyGroupEntity> findByStudyGroupUserId(Integer studyGroupUserId);
}
