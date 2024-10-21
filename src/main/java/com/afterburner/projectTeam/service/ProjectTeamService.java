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
import com.afterburner.projectTeam.model.ProjectTeamMember;
import com.afterburner.projectTeam.repository.ProjectTeamRepository;

import jakarta.persistence.EntityNotFoundException;

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

	// 팀원 신청
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
		projectTeam.setProjectTeamMember(projectTeamDTO.getProjectTeamMember());
		projectTeam.setProjectTeamJoinedAt(LocalDateTime.now());

		projectTeamRepository.save(projectTeam);

		logger.info("팀원 등록: {}", projectTeamDTO.getProjectTeamUserId());

		return projectTeamDTO;
	}

	// 팀장이 승인
	@Transactional
	public ProjectTeamDTO approveTeamMember(Integer projectId, Integer projectTeamId) {
		ProjectTeam teamMember = projectTeamRepository.findByProjectIdAndMemberId(projectId, projectTeamId)
			.orElseThrow(() -> new EntityNotFoundException("팀원을 찾을 수 없습니다."));

		// 신청 상태를 APPROVED로 변경
		teamMember.setProjectTeamMember(ProjectTeamMember.APPROVED);
		projectTeamRepository.save(teamMember);

		return new ProjectTeamDTO(teamMember);
	}

	// 팀원 거부  잔인하다
	@Transactional
	public ProjectTeamDTO rejectTeamMember(Integer projectId, Integer projectTeamId) {
		ProjectTeam teamMember = projectTeamRepository.findByProjectIdAndMemberId(projectId, projectTeamId)
			.orElseThrow(() -> new EntityNotFoundException("팀원을 찾을 수 없습니다."));

		// 신청 상태를 REJECTED로 변경
		teamMember.setProjectTeamMember(ProjectTeamMember.REJECTED);
		projectTeamRepository.save(teamMember);

		return new ProjectTeamDTO(teamMember);
	}

	// 팀원 전체 조회 (상태 필터링 추가, 거부된 애들은 안보임)
	public List<ProjectTeamDTO> getAllTeamMembers(Integer projectId) {
		// 프로젝트가 존재하는지 확인
		projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNotFoundException("ID: " + projectId + "의 프로젝트를 찾을 수 없습니다."));

		// 상태가 PENDING 또는 APPROVED인 팀원만 조회
		List<ProjectTeam> teamMembers = projectTeamRepository.findByProjectTeamPostIdAndProjectTeamStatus(projectId, "APPROVED");
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

	// 팀원 정보 수정
	@Transactional
	public ProjectTeamDTO updateTeamMember(Integer projectId, Integer projectTeamId, ProjectTeamDTO projectTeamDTO) {
		// 팀원 수정할 프로젝트가 존재하는지 확인
		projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNotFoundException("ID: " + projectId + "의 프로젝트를 찾을 수 없습니다."));

		// 팀원이 존재하는지 확인
		ProjectTeam projectTeam = projectTeamRepository.findById(projectTeamId)
			.orElseThrow(() -> new TeamMemberNotFoundException("ID: " + projectTeamId + "의 팀원을 찾을 수 없습니다."));

		projectTeam.setProjectTeamRole(projectTeamDTO.getProjectTeamRole()); // 팀장, 부팀장, 팀원
		projectTeam.setProjectTeamPart(projectTeamDTO.getProjectTeamPart()); // 백엔드, 프론트, 등등
		projectTeam.setProjectTeamMember(projectTeamDTO.getProjectTeamMember()); // 신청 중, 허락, 거부

		projectTeamRepository.save(projectTeam);

		logger.info("팀원 정보 수정: {}", projectTeamId);

		ProjectTeamDTO updatedDTO = new ProjectTeamDTO();
		updatedDTO.setProjectTeamUserId(projectTeam.getProjectTeamUserId());
		updatedDTO.setProjectTeamRole(projectTeam.getProjectTeamRole());
		updatedDTO.setProjectTeamPart(projectTeam.getProjectTeamPart());
		updatedDTO.setProjectTeamMember(projectTeam.getProjectTeamMember());
		updatedDTO.setProjectTeamJoinedAt(projectTeam.getProjectTeamJoinedAt());

		return updatedDTO;
	}

	// 팀원 삭제
	@Transactional
	public boolean removeTeamMember(Integer projectId, Integer projectTeamId) {
		// 팀원이 존재하는지 확인
		ProjectTeam projectTeam = projectTeamRepository.findById(projectTeamId)
			.orElseThrow(() -> new TeamMemberNotFoundException("ID: " + projectTeamId + "의 팀원을 찾을 수 없습니다."));

		projectTeamRepository.delete(projectTeam);
		logger.info("팀원 삭제: {}", projectTeamId);

		return true; // 삭제 성공
	}
}
