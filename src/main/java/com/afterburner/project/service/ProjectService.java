package com.afterburner.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.global.exception.ProjectNotFoundException;
import com.afterburner.global.exception.TechStackConversionException;
import com.afterburner.project.model.Project;
import com.afterburner.project.model.ProjectDTO;
import com.afterburner.project.repository.ProjectRepository;

@Service
public class ProjectService {

	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	// 필수 값 검증
	private void validateProjectDTO(ProjectDTO projectDTO) {
		if (projectDTO.getProjectTitle() == null || projectDTO.getProjectContent() == null) {
			logger.warn("제목 또는 내용이 없습니다. 제목: {}, 내용: {}", projectDTO.getProjectTitle(), projectDTO.getProjectContent());
			throw new IllegalArgumentException("제목과 내용은 필수입니다.");
		}
	}

	// 게시글 등록
	@Transactional
	public ProjectDTO createProject(ProjectDTO projectDTO) throws Exception {
		validateProjectDTO(projectDTO);
		logger.info("프로젝트 등록 중: 제목: {}, 내용: {}", projectDTO.getProjectTitle(), projectDTO.getProjectContent());

		Project project = new Project.Builder()
			.projectTitle(projectDTO.getProjectTitle())
			.projectContent(projectDTO.getProjectContent())
			.projectLink(projectDTO.getProjectLink())
			.projectStatus(PostStatus.DEFAULT)  // 기본값으로 DEFAULT 설정
			.projectUserId(projectDTO.getProjectUserId())
			.build();

		project.setProjectTechStack(projectDTO.getProjectTechStack());
		project.setTeamPartLimits(projectDTO.getTeamPartLimits());
		projectRepository.save(project);
		logger.info("프로젝트가 성공적으로 등록되었습니다. ID: {}", project.getProjectId());

		// DTO 변환
		return new ProjectDTO(
			project.getProjectId(),
			project.getProjectTitle(),
			project.getProjectContent(),
			project.getProjectLink(),
			project.getProjectCreatedAt(),
			project.getProjectUpdatedAt(),
			project.getProjectDeletedAt(),
			project.getProjectFinishedAt(),
			project.getProjectStatus(),
			project.getProjectTechStack(),
			project.getTeamPartLimits(),
			project.getProjectUserId()
		);
	}

	// 전체 게시글 조회
	public List<ProjectDTO> getAllProjects() { // DELETED가 아닌 애들만 조회
		List<Project> projects = projectRepository.findAllByProjectStatusNot(PostStatus.DELETED);
		return projects.stream()
			.map(project -> new ProjectDTO(
				project.getProjectId(),
				project.getProjectTitle(),
				project.getProjectContent(),
				project.getProjectLink(),
				project.getProjectCreatedAt(),
				project.getProjectUpdatedAt(),
				project.getProjectDeletedAt(),
				project.getProjectFinishedAt(),
				project.getProjectStatus(),
				project.getProjectTechStack(),
				project.getTeamPartLimits(),
				project.getProjectUserId()
			))
			.collect(Collectors.toList());
	}

	// ID 기준 게시글 조회
	public ProjectDTO getProjectById(Integer projectId) { // DELETED가 아닌 애들만 조회
		Project project = projectRepository.findByIdAndProjectStatusNot(projectId, PostStatus.DELETED)
			.orElseThrow(() -> new NoSuchElementException("해당 ID의 프로젝트가 존재하지 않습니다."));

		// DTO 변환
		return new ProjectDTO(
			project.getProjectId(),
			project.getProjectTitle(),
			project.getProjectContent(),
			project.getProjectLink(),
			project.getProjectCreatedAt(),
			project.getProjectUpdatedAt(),
			project.getProjectDeletedAt(),
			project.getProjectFinishedAt(),
			project.getProjectStatus(),
			project.getProjectTechStack(),
			project.getTeamPartLimits(),
			project.getProjectUserId()
		);
	}

	// 게시글 수정
	@Transactional
	public ProjectDTO updateProject(Integer projectId, ProjectDTO projectDTO) { // DELETED가 아닌 애들만 수정 가능
		validateProjectDTO(projectDTO);
		Project project = projectRepository.findByIdAndProjectStatusNot(projectId, PostStatus.DELETED)
			.orElseThrow(() -> new NoSuchElementException("해당 ID의 프로젝트가 존재하지 않습니다."));

		project.setProjectTitle(projectDTO.getProjectTitle());
		project.setProjectContent(projectDTO.getProjectContent());
		project.setProjectLink(projectDTO.getProjectLink());
		project.setProjectTechStack(projectDTO.getProjectTechStack());
		project.setTeamPartLimits(projectDTO.getTeamPartLimits());

		logger.info("프로젝트가 수정되었습니다. ID: {}", projectId);

		// DTO 변환
		return new ProjectDTO(
			project.getProjectId(),
			project.getProjectTitle(),
			project.getProjectContent(),
			project.getProjectLink(),
			project.getProjectCreatedAt(),
			project.getProjectUpdatedAt(),
			project.getProjectDeletedAt(),
			project.getProjectFinishedAt(),
			project.getProjectStatus(),
			project.getProjectTechStack(),
			project.getTeamPartLimits(),
			project.getProjectUserId()
		);
	}

	// 논리적 삭제
	@Transactional
	public void deleteProject(Integer projectId) {
		logger.info("ID: {}의 프로젝트를 삭제 중입니다.", projectId);
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNotFoundException("프로젝트 게시글을 찾을 수 없습니다. ID: " + projectId));

		project.setProjectDeletedAt(LocalDateTime.now());
		project.setProjectStatus(PostStatus.DELETED);
		projectRepository.save(project);
		logger.info("ID: {}의 프로젝트가 성공적으로 삭제되었습니다.", projectId);
	}
}
