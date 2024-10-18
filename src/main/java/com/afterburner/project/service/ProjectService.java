package com.afterburner.project.service;

import java.time.LocalDateTime;
import java.util.List;
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
			.projectStatus(PostStatus.DEFAULT)
			.projectUserId(projectDTO.getProjectUserId())
			.build();

		// 기술 스택 설정
		project.setProjectTechStack(projectDTO.getProjectTechStack());

		projectRepository.save(project);
		logger.info("프로젝트가 성공적으로 등록되었습니다. ID: {}", project.getProjectId());

		// DTO 생성 및 반환
		return convertToDTO(project);
	}

	private ProjectDTO convertToDTO(Project project) {
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
			project.getProjectUserId()
		);
	}

	// 전체 조회
	public List<ProjectDTO> getAllProjects() {
		logger.info("모든 프로젝트를 조회 중입니다.");
		return projectRepository.findAll().stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	// ID 기준 상세 조회
	public ProjectDTO getProjectById(Integer projectId) throws Exception {
		logger.info("ID: {}의 프로젝트를 조회 중입니다.", projectId);
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNotFoundException("사이드 프로젝트 게시글을 찾을 수 없습니다. ID: " + projectId));

		logger.info("ID: {}의 프로젝트를 성공적으로 조회하였습니다.", projectId);
		return convertToDTO(project);
	}

	// 게시글 수정
	@Transactional
	public ProjectDTO updateProject(Integer projectId, ProjectDTO projectDTO) throws Exception {
		logger.info("ID: {}의 프로젝트를 수정 중입니다.", projectId);
		validateProjectDTO(projectDTO);

		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNotFoundException("사이드 프로젝트 게시글을 찾을 수 없습니다. ID: " + projectId));

		project.setProjectTitle(projectDTO.getProjectTitle());
		project.setProjectContent(projectDTO.getProjectContent());
		project.setProjectLink(projectDTO.getProjectLink());
		project.setProjectTechStack(projectDTO.getProjectTechStack());
		project.setProjectStatus(projectDTO.getProjectStatus());
		project.setProjectUserId(projectDTO.getProjectUserId());

		projectRepository.save(project);
		logger.info("ID: {}의 프로젝트가 성공적으로 수정되었습니다.", projectId);

		return convertToDTO(project);
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
