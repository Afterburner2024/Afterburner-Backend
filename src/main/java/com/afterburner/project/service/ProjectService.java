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
import com.afterburner.user.model.User;
import com.afterburner.user.repository.UserRepository;

@Service
public class ProjectService {

	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
        private final ProjectRepository projectRepository;
        private final UserRepository userRepository;

	@Autowired
        public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
                this.projectRepository = projectRepository;
                this.userRepository = userRepository;
        }

	// 필수 값 검증해서 로그에 남길려고 만든거임... 필요 없어지거나 리소스 많이 먹으면 삭제하던지 해야 할 듯?
        private void validateProjectDTO(ProjectDTO projectDTO) {
                if (projectDTO.getProjectTitle() == null || projectDTO.getProjectContent() == null) {
                        logger.warn("제목 또는 내용이 없습니다. 제목: {}, 내용: {}", projectDTO.getProjectTitle(), projectDTO.getProjectContent());
                        throw new IllegalArgumentException("제목과 내용은 필수입니다.");
                }
        }

        private ProjectDTO toDTO(Project project) {
                String userName = userRepository.findById(project.getProjectUserId())
                                .map(User::getUserName)
                                .orElse(null);
                return ProjectDTO.builder()
                                .projectId(project.getProjectId())
                                .projectTitle(project.getProjectTitle())
                                .projectSummary(project.getProjectSummary())
                                .projectContent(project.getProjectContent())
                                .projectLink(project.getProjectLink())
                                .projectCreatedAt(project.getProjectCreatedAt())
                                .projectUpdatedAt(project.getProjectUpdatedAt())
                                .projectDeletedAt(project.getProjectDeletedAt())
                                .projectFinishedAt(project.getProjectFinishedAt())
                                .projectStatus(project.getProjectStatus())
                                .projectTechStack(project.getProjectTechStack())
                                .projectRecruitmentRoles(project.getProjectRecruitmentRoles())
                                .projectUserId(project.getProjectUserId())
                                .projectUserName(userName)
                                .projectRegion(project.getProjectRegion())
                                .build();
        }

	// 등록
	@Transactional
	public ProjectDTO createProject(ProjectDTO projectDTO) throws Exception {
		validateProjectDTO(projectDTO);
		logger.info("프로젝트 등록 중: 제목: {}, 내용: {}", projectDTO.getProjectTitle(), projectDTO.getProjectContent());

		Project project = Project.builder()
				.projectTitle(projectDTO.getProjectTitle())
				.projectContent(projectDTO.getProjectContent())
				.projectLink(projectDTO.getProjectLink())
				.projectStatus(PostStatus.DEFAULT)
				.projectUserId(projectDTO.getProjectUserId())
				.projectTechStack(projectDTO.getProjectTechStack())
				.projectRecruitmentRoles(projectDTO.getProjectRecruitmentRoles())
				.projectRegion(projectDTO.getProjectRegion())
				.build();
                projectRepository.save(project);
                logger.info("프로젝트가 성공적으로 등록되었습니다. ID: {}", project.getProjectId());

                return toDTO(project);
        }

	public List<ProjectDTO> getAllProjects() {
		logger.info("모든 프로젝트를 조회 중입니다.");
                return projectRepository.findAll().stream()
                                .map(this::toDTO)
                                .collect(Collectors.toList());
        }

	// ID 기준 상세 조회
	public ProjectDTO getProjectById(Integer projectId) throws Exception {
		logger.info("ID: {}의 프로젝트를 조회 중입니다.", projectId);
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ProjectNotFoundException("사이드 프로젝트 게시글을 찾을 수 없습니다. ID: " + projectId));

                logger.info("ID: {}의 프로젝트를 성공적으로 조회하였습니다.", projectId);
                return toDTO(project);
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
		project.setProjectRecruitmentRoles(projectDTO.getProjectRecruitmentRoles());
		project.setProjectRegion(projectDTO.getProjectRegion());
		project.setProjectStatus(PostStatus.DEFAULT);
		project.setProjectUserId(projectDTO.getProjectUserId());

                projectRepository.save(project);
                logger.info("ID: {}의 프로젝트가 성공적으로 수정되었습니다.", projectId);

                return toDTO(project);
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

