package com.afterburner.projectTeam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterburner.projectTeam.model.ProjectTeam;

public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Integer> {

	// projectTeamPostId로 팀원 목록 조회
	List<ProjectTeam> findByProjectTeamPostId(Integer projectTeamPostId);

	// projectId와 memberId로 팀원 조회
	Optional<ProjectTeam> findByProjectTeamIdAndProjectTeamUserId(Integer projectId, Integer userId);

	// 팀원 신청 상태(신청중, 합격, 불합격)에 따라 팀원 조회
	List<ProjectTeam> findByProjectTeamPostIdAndProjectTeamMemberIn(Integer projectId, List<String> statuses);
}
