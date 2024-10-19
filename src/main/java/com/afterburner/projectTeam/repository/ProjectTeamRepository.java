package com.afterburner.projectTeam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterburner.projectTeam.model.ProjectTeam;

public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Integer> {

	// projectTeamPostId로 팀원 목록 조회
	List<ProjectTeam> findByProjectTeamPostId(Integer projectTeamPostId);
}
