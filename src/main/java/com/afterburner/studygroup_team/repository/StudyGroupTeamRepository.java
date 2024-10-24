package com.afterburner.studygroup_team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afterburner.studygroup_team.model.entity.StudyGroupTeamEntity;

@Repository
public interface StudyGroupTeamRepository extends JpaRepository<StudyGroupTeamEntity, Integer> {
}
