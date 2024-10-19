package com.afterburner.projectTeam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afterburner.global.exception.ProjectNotFoundException;
import com.afterburner.global.exception.TeamMemberNotFoundException;
import com.afterburner.project.repository.ProjectRepository;
import com.afterburner.projectTeam.model.ProjectTeam;
import com.afterburner.projectTeam.model.ProjectTeamDTO;
import com.afterburner.projectTeam.repository.ProjectTeamRepository;

@Service
public class ProjectTeamService {

	private static final Logger logger = LoggerFactory.getLogger(ProjectTeamService.class);

	private final ProjectTeamRepository projectTeamRepository;
	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectTeamService(ProjectTeamRepository projectTeamRepository, ProjectRepository projectRepository) {
		this.projectTeamRepository = projectTeamRepository;
		this.projectRepository = projectRepository;
	}

	// 팀원 등록
	@Transactional
	public ProjectTeamDTO joinProject(Integer projectId, ProjectTeamDTO projectTeamDTO) {
		// 프로젝트가 존재하는지 확인
		projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNotFoundException("ID: " + projectId + "의 프로젝트를 찾을 수 없습니다."));

		ProjectTeam projectTeam = new ProjectTeam();
		projectTeam.setProjectTeamPostId(projectId);
		projectTeam.setProjectTeamUserId(projectTeamDTO.getProjectTeamUserId());
		projectTeam.setProjectTeamRole(projectTeamDTO.getProjectTeamRole());
		projectTeam.setProjectTeamPart(projectTeamDTO.getProjectTeamPart());
		projectTeam.setProjectTeamJoinedAt(LocalDateTime.now());

		projectTeamRepository.save(projectTeam);

		logger.info("팀원 등록: {}", projectTeamDTO.getProjectTeamUserId());

		return projectTeamDTO;
	}

	// 팀원 전체 조회
	public List<ProjectTeamDTO> getAllTeamMembers(Integer projectId) {
		// 프로젝트가 존재하는지 확인
		projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNotFoundException("ID: " + projectId + "의 프로젝트를 찾을 수 없습니다."));

		List<ProjectTeam> teamMembers = projectTeamRepository.findByProjectTeamPostId(projectId);
		List<ProjectTeamDTO> teamMemberDTOs = teamMembers.stream()
			.map(teamMember -> {
				ProjectTeamDTO dto = new ProjectTeamDTO();
				dto.setProjectTeamUserId(teamMember.getProjectTeamUserId());
				dto.setProjectTeamRole(teamMember.getProjectTeamRole());
				dto.setProjectTeamPart(teamMember.getProjectTeamPart());
				dto.setProjectTeamJoinedAt(teamMember.getProjectTeamJoinedAt());
				return dto;
			})
			.collect(Collectors.toList());

		logger.info("팀원 전체 조회: {}명", teamMemberDTOs.size());

		return teamMemberDTOs;
	}

	// 팀원 상세 조회
	public ProjectTeamDTO getTeamMemberById(Integer projectId, Integer memberId) {
		// 팀원이 존재하는지 확인
		ProjectTeam projectTeam = projectTeamRepository.findById(memberId)
			.orElseThrow(() -> new TeamMemberNotFoundException("ID: " + memberId + "의 팀원을 찾을 수 없습니다."));

		ProjectTeamDTO dto = new ProjectTeamDTO();
		dto.setProjectTeamUserId(projectTeam.getProjectTeamUserId());
		dto.setProjectTeamRole(projectTeam.getProjectTeamRole());
		dto.setProjectTeamPart(projectTeam.getProjectTeamPart());
		dto.setProjectTeamJoinedAt(projectTeam.getProjectTeamJoinedAt());

		logger.info("팀원 상세 조회: {}", memberId);

		return dto;
	}

	// 팀원 정보 수정
	@Transactional
	public ProjectTeamDTO updateTeamMember(Integer projectId, Integer memberId, ProjectTeamDTO projectTeamDTO) {
		// 팀원 수정할 프로젝트가 존재하는지 확인
		projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNotFoundException("ID: " + projectId + "의 프로젝트를 찾을 수 없습니다."));

		// 팀원이 존재하는지 확인
		ProjectTeam projectTeam = projectTeamRepository.findById(memberId)
			.orElseThrow(() -> new TeamMemberNotFoundException("ID: " + memberId + "의 팀원을 찾을 수 없습니다."));

		projectTeam.setProjectTeamRole(projectTeamDTO.getProjectTeamRole());
		projectTeam.setProjectTeamPart(projectTeamDTO.getProjectTeamPart());

		projectTeamRepository.save(projectTeam);

		logger.info("팀원 정보 수정: {}", memberId);

		ProjectTeamDTO updatedDTO = new ProjectTeamDTO();
		updatedDTO.setProjectTeamUserId(projectTeam.getProjectTeamUserId());
		updatedDTO.setProjectTeamRole(projectTeam.getProjectTeamRole());
		updatedDTO.setProjectTeamPart(projectTeam.getProjectTeamPart());
		updatedDTO.setProjectTeamJoinedAt(projectTeam.getProjectTeamJoinedAt());

		return updatedDTO;
	}

	// 팀원 삭제
	@Transactional
	public boolean removeTeamMember(Integer projectId, Integer memberId) {
		// 팀원이 존재하는지 확인
		ProjectTeam projectTeam = projectTeamRepository.findById(memberId)
			.orElseThrow(() -> new TeamMemberNotFoundException("ID: " + memberId + "의 팀원을 찾을 수 없습니다."));

		projectTeamRepository.delete(projectTeam);
		logger.info("팀원 삭제: {}", memberId);

		return true; // 삭제 성공
	}
}
