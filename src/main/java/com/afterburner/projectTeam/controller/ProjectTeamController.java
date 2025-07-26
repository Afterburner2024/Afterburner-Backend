package com.afterburner.projectTeam.controller;

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
import com.afterburner.project.controller.ProjectController;
import com.afterburner.project.service.ProjectService;
import com.afterburner.projectTeam.model.ProjectTeamDTO;
import com.afterburner.projectTeam.model.ProjectTeamMember;
import com.afterburner.projectTeam.service.ProjectTeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/project/{projectId}/member")
public class ProjectTeamController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectTeamController.class);

	private final ProjectTeamService projectTeamService;
	private final ProjectService projectService;

	@Autowired
	public ProjectTeamController(ProjectTeamService projectTeamService, ProjectService projectService) {
		this.projectTeamService = projectTeamService;
		this.projectService = projectService;
	}

	// 참가 신청 메서드
	@PostMapping("/join")
	public ResponseEntity<ApiResponse<ProjectTeamDTO>> joinProject(
		@PathVariable("projectId") Integer projectId,
		@Valid @RequestBody ProjectTeamDTO projectTeamDTO) throws Exception {

		logger.info("ID: {}의 프로젝트에 참가 신청 중입니다.", projectId);

		// 프로젝트 존재 여부 확인
		if (projectService.getProjectById(projectId) == null) {
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(ApiResponse.<ProjectTeamDTO>builder()
					.statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
					.message(ErrorCode.NOT_FOUND_ERROR.getMessage())
					.build());
		}

		// 신청 상태를 PENDING(신청 대기 중)으로 설정
		projectTeamDTO.setProjectTeamMember(ProjectTeamMember.PENDING);

		ProjectTeamDTO joinedTeam = projectTeamService.joinProject(projectId, projectTeamDTO);

		if (joinedTeam != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.<ProjectTeamDTO>builder()
					.statusCode(SuccessCode.INSERT.getStatus())
					.message(SuccessCode.INSERT.getMessage())
					.result(joinedTeam)
					.build());
		} else {
			return ResponseEntity.status(ErrorCode.INSERT_ERROR.getStatus())
				.body(ApiResponse.<ProjectTeamDTO>builder()
					.statusCode(ErrorCode.INSERT_ERROR.getStatus())
					.message(ErrorCode.INSERT_ERROR.getMessage())
					.build());
		}
	}

	// 팀장이 허락했을 때 좋군..
	@PutMapping("/{projectTeamId}/approve")
	public ResponseEntity<ApiResponse<ProjectTeamDTO>> approveTeamMember(
		@PathVariable("projectId") Integer projectId,
		@PathVariable("projectTeamId") Integer projectTeamId) throws Exception {

		logger.info("ID: {}의 프로젝트에서 팀원 ID: {}의 참가 신청을 허락 중입니다.", projectId, projectTeamId);

		// 프로젝트 존재 여부 확인
		if (projectService.getProjectById(projectId) == null) {
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(ApiResponse.<ProjectTeamDTO>builder()
					.statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
					.message(ErrorCode.NOT_FOUND_ERROR.getMessage())
					.build());
		}

		// 신청 상태를 APPROVED로 변경
		ProjectTeamDTO updatedMember = projectTeamService.approveTeamMember(projectId, projectTeamId);

		if (updatedMember != null) {
			return ResponseEntity.ok(ApiResponse.<ProjectTeamDTO>builder()
				.statusCode(SuccessCode.UPDATE.getStatus())
				.message("참가 신청이 승인되었습니다.")
				.result(updatedMember)
				.build());
		} else {
			return ResponseEntity.status(ErrorCode.UPDATE_ERROR.getStatus())
				.body(ApiResponse.<ProjectTeamDTO>builder()
					.statusCode(ErrorCode.UPDATE_ERROR.getStatus())
					.message("참가 신청 승인에 실패했습니다.")
					.build());
		}
	}

	// 팀장이 거절함...ㅠ
	@PutMapping("/{projectTeamId}/reject")
	public ResponseEntity<ApiResponse<ProjectTeamDTO>> rejectTeamMember(
		@PathVariable("projectId") Integer projectId,
		@PathVariable("projectTeamId") Integer projectTeamId) throws Exception {

		logger.info("ID: {}의 프로젝트에서 팀원 ID: {}의 참가 신청을 거부 중입니다.", projectId, projectTeamId);

		// 프로젝트 존재 여부 확인
		if (projectService.getProjectById(projectId) == null) {
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(ApiResponse.<ProjectTeamDTO>builder()
					.statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
					.message(ErrorCode.NOT_FOUND_ERROR.getMessage())
					.build());
		}

		// 신청 상태를 REJECTED로 변경
		ProjectTeamDTO updatedMember = projectTeamService.rejectTeamMember(projectId, projectTeamId);

		if (updatedMember != null) {
			return ResponseEntity.ok(ApiResponse.<ProjectTeamDTO>builder()
				.statusCode(SuccessCode.UPDATE.getStatus())
				.message("참가 신청이 거부되었습니다.")
				.result(updatedMember)
				.build());
		} else {
			return ResponseEntity.status(ErrorCode.UPDATE_ERROR.getStatus())
				.body(ApiResponse.<ProjectTeamDTO>builder()
					.statusCode(ErrorCode.UPDATE_ERROR.getStatus())
					.message("참가 신청 거부에 실패했습니다.")
					.build());
		}
	}

	// 팀원 전체 조회
	@GetMapping
	public ResponseEntity<ApiResponse<List<ProjectTeamDTO>>> getAllTeamMembers(
		@PathVariable("projectId") Integer projectId) throws Exception {
		logger.info("ID: {}의 프로젝트 팀원을 조회 중입니다.", projectId);

		// 프로젝트가 존재하는지 확인
		if (projectService.getProjectById(projectId) == null) {
			logger.warn("ID: {}의 프로젝트를 찾을 수 없어 팀원을 조회할 수 없습니다.", projectId);
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
                                .body(ApiResponse.<List<ProjectTeamDTO>>builder()
                                        .statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
					.message(ErrorCode.NOT_FOUND_ERROR.getMessage())
					.build());
		}

		// 서비스에서 승인된 팀원만 가져오기
                List<ProjectTeamDTO> teamMembers = projectTeamService.getAllTeamMembers(projectId);
                ApiResponse<List<ProjectTeamDTO>> response = ApiResponse.<List<ProjectTeamDTO>>builder()
                        .statusCode(SuccessCode.SELECT.getStatus())
                        .message(SuccessCode.SELECT.getMessage())
                        .result(teamMembers)
                        .build();
                return ResponseEntity.ok(response);
        }

	// 팀원 역할 변경 및 파트 수정 (메서드 추가 필요)
	@PutMapping("/{projectTeamId}")
	public ResponseEntity<ApiResponse<ProjectTeamDTO>> updateTeamMember(
		@PathVariable("projectId") Integer projectId,
		@PathVariable("projectTeamId") Integer projectTeamId,
		@Valid @RequestBody ProjectTeamDTO projectTeamDTO) throws Exception {

		logger.info("ID: {}의 프로젝트에서 팀원 ID: {}의 정보를 수정 중입니다.", projectId, projectTeamId);

		// 프로젝트가 존재하는지 확인
		if (projectService.getProjectById(projectId) == null) {
			logger.warn("ID: {}의 프로젝트를 찾을 수 없어 팀원 정보를 수정할 수 없습니다.", projectId);
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(ApiResponse.<ProjectTeamDTO>builder()
					.statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
					.message(ErrorCode.NOT_FOUND_ERROR.getMessage())
					.build());
		}

		// 팀원 수정 처리
		ProjectTeamDTO updatedMember = projectTeamService.updateTeamMember(projectId, projectTeamId, projectTeamDTO);
		if (updatedMember == null) {
			logger.error("팀원 정보 수정에 실패했습니다.");
			return ResponseEntity.status(ErrorCode.UPDATE_ERROR.getStatus())
				.body(ApiResponse.<ProjectTeamDTO>builder()
					.statusCode(ErrorCode.UPDATE_ERROR.getStatus())
					.message(ErrorCode.UPDATE_ERROR.getMessage())
					.build());
		}

		ApiResponse<ProjectTeamDTO> response = ApiResponse.<ProjectTeamDTO>builder()
			.statusCode(SuccessCode.UPDATE.getStatus())
			.message(SuccessCode.UPDATE.getMessage())
			.result(updatedMember)
			.build();
		return ResponseEntity.ok(response);
	}

	// 팀원 탈퇴
	@DeleteMapping("/{projectTeamId}")
	public ResponseEntity<ApiResponse<Void>> removeTeamMember(
		@PathVariable("projectId") Integer projectId,
		@PathVariable("projectTeamId") Integer projectTeamId) throws Exception {

		logger.info("ID: {}의 프로젝트에서 팀원 ID: {}를 삭제 중입니다.", projectId, projectTeamId);

		// 프로젝트가 존재하는지 확인
		if (projectService.getProjectById(projectId) == null) {
			logger.warn("ID: {}의 프로젝트를 찾을 수 없어 팀원을 삭제할 수 없습니다.", projectId);
			return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus())
				.body(ApiResponse.<Void>builder()
					.statusCode(ErrorCode.NOT_FOUND_ERROR.getStatus())
					.message(ErrorCode.NOT_FOUND_ERROR.getMessage())
					.build());
		}

		// 팀원 삭제 처리
		boolean isRemoved = projectTeamService.removeTeamMember(projectId, projectTeamId);
		if (!isRemoved) {
			logger.error("팀원 삭제에 실패했습니다.");
			return ResponseEntity.status(ErrorCode.DELETE_ERROR.getStatus())
				.body(ApiResponse.<Void>builder()
					.statusCode(ErrorCode.DELETE_ERROR.getStatus())
					.message(ErrorCode.DELETE_ERROR.getMessage())
					.build());
		}

		logger.info("ID: {}의 프로젝트에서 팀원 ID: {}가 성공적으로 삭제되었습니다.", projectId, projectTeamId);
		ApiResponse<Void> response = ApiResponse.<Void>builder()
			.statusCode(SuccessCode.DELETE.getStatus())
			.message(SuccessCode.DELETE.getMessage())
			.build();
		return ResponseEntity.ok(response);
	}
}

