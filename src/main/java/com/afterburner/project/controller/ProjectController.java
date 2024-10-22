package com.afterburner.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.codes.SuccessCode;
import com.afterburner.common.response.ApiResponse;
import com.afterburner.project.model.ProjectDTO;
import com.afterburner.project.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	private final ProjectService projectService;

	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	// 게시글 등록
	@PostMapping
	public ResponseEntity<ApiResponse<ProjectDTO>> createProject(@Valid @RequestBody ProjectDTO projectDTO) throws
		Exception {
		if (projectDTO == null) {
			logger.warn("요청 본문이 없습니다.");
			return ResponseEntity.status(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
				.body(new ApiResponse.Builder<ProjectDTO>()
					.statusCode(ErrorCode.REQUEST_BODY_MISSING_ERROR.getStatus())
					.message(ErrorCode.REQUEST_BODY_MISSING_ERROR.getMessage())
					.build());
		}

		logger.info("프로젝트 생성 중, ID: {}", projectDTO.getProjectId());
		ProjectDTO createdProject = projectService.createProject(projectDTO);

		if (createdProject != null) {
			logger.info("프로젝트가 성공적으로 생성되었습니다. ID: {}", createdProject.getProjectId());
			ApiResponse<ProjectDTO> response = new ApiResponse.Builder<ProjectDTO>()
				.statusCode(SuccessCode.INSERT.getStatus())
				.message(SuccessCode.INSERT.getMessage())
				.result(createdProject)
				.build();
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} else {
			logger.error("프로젝트 생성에 실패했습니다.");
			return ResponseEntity.status(ErrorCode.INSERT_ERROR.getStatus())
				.body(new ApiResponse.Builder<ProjectDTO>()
					.statusCode(ErrorCode.INSERT_ERROR.getStatus())
					.message(ErrorCode.INSERT_ERROR.getMessage())
					.build());
		}
	}

	// 전체 조회
	@GetMapping
	public ResponseEntity<ApiResponse<List<ProjectDTO>>> getAllProjects() {
		logger.info("모든 프로젝트를 조회 중입니다.");
		List<ProjectDTO> projects = projectService.getAllProjects();
		ApiResponse<List<ProjectDTO>> response = new ApiResponse.Builder<List<ProjectDTO>>()
			.statusCode(SuccessCode.SELECT.getStatus())
			.message(SuccessCode.SELECT.getMessage())
			.result(projects)
			.build();
		return ResponseEntity.ok(response);
	}

	// ID 기준 상세 조회
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProjectDTO>> getProjectById(@PathVariable("id") Integer projectId) throws
		Exception {
		logger.info("ID: {}의 프로젝트를 조회 중입니다.", projectId);
		ProjectDTO project = projectService.getProjectById(projectId);
		if (project == null) {
			logger.warn("ID: {}의 프로젝트를 찾을 수 없습니다.", projectId);
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(new ApiResponse.Builder<ProjectDTO>()
					.statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
					.message(ErrorCode.NOT_FOUND_ERROR.getMessage())
					.build());
		}
		ApiResponse<ProjectDTO> response = new ApiResponse.Builder<ProjectDTO>()
			.statusCode(SuccessCode.SELECT.getStatus())
			.message(SuccessCode.SELECT.getMessage())
			.result(project)
			.build();
		return ResponseEntity.ok(response);
	}

	// 게시글 수정
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProjectDTO>> updateProject(
		@PathVariable("id") Integer projectId,
		@Valid @RequestBody ProjectDTO projectDTO) throws Exception {
		logger.info("ID: {}의 프로젝트를 수정 중입니다.", projectId);
		if (projectService.getProjectById(projectId) == null) {
			logger.warn("ID: {}의 프로젝트를 찾을 수 없어 수정할 수 없습니다.", projectId);
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(new ApiResponse.Builder<ProjectDTO>()
					.statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
					.message(ErrorCode.NOT_FOUND_ERROR.getMessage())
					.build());
		}
		ProjectDTO updatedProject = projectService.updateProject(projectId, projectDTO);
		logger.info("프로젝트가 성공적으로 수정되었습니다. ID: {}", updatedProject.getProjectId());
		ApiResponse<ProjectDTO> response = new ApiResponse.Builder<ProjectDTO>()
			.statusCode(SuccessCode.UPDATE.getStatus())
			.message(SuccessCode.UPDATE.getMessage())
			.result(updatedProject)
			.build();
		return ResponseEntity.ok(response);
	}

	// 논리적 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable("id") Integer projectId) throws Exception {
		logger.info("ID: {}의 프로젝트를 삭제 중입니다.", projectId);
		if (projectService.getProjectById(projectId) == null) {
			logger.warn("ID: {}의 프로젝트를 찾을 수 없어 삭제할 수 없습니다.", projectId);
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(new ApiResponse.Builder<Void>()
					.statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
					.message(ErrorCode.NOT_FOUND_ERROR.getMessage())
					.build());
		}
		projectService.deleteProject(projectId);
		logger.info("ID: {}의 프로젝트가 성공적으로 삭제되었습니다.", projectId);
		return ResponseEntity.noContent().build();
	}
}