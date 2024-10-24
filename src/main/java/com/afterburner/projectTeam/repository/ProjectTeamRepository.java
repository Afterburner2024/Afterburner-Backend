package com.afterburner.projectTeam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterburner.projectTeam.model.ProjectTeam;
import com.afterburner.projectTeam.model.ProjectTeamMember;

public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Integer> {

	// projectTeamPostId로 팀원 목록 조회
	List<ProjectTeam> findByProjectTeamPostId(Integer projectTeamPostId);

	// projectTeamPostId와 projectTeamUserId로 팀원 조회
	Optional<ProjectTeam> findByProjectTeamPostIdAndProjectTeamUserId(Integer projectTeamPostId, Integer projectTeamUserId);

	// 팀원 신청 상태(신청중, 합격, 불합격)에 따라 팀원 조회
	List<ProjectTeam> findByProjectTeamPostIdAndProjectTeamMemberIn(Integer projectTeamPostId, List<ProjectTeamMember> statuses);
}
