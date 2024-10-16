package com.afterburner.studygroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afterburner.studygroup.model.entity.StudyGroupEntity;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Integer> {
}
