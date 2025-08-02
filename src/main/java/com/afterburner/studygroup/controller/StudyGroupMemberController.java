package com.afterburner.studygroup.controller;

import com.afterburner.common.codes.SuccessCode;
import com.afterburner.common.response.ApiResponse;
import com.afterburner.studygroup.model.StudyMemberStatus;
import com.afterburner.studygroup.model.dto.StudyGroupMemberDTO;
import com.afterburner.studygroup.service.StudyGroupMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/study-group/{studyGroupId}/member")
@RequiredArgsConstructor
public class StudyGroupMemberController {

    private final StudyGroupMemberService studyGroupMemberService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<StudyGroupMemberDTO>> joinStudyGroup(
            @PathVariable("studyGroupId") Integer studyGroupId,
            @AuthenticationPrincipal Integer userId, // 현재 로그인한 사용자 객체를 컨트롤러 메서드의 파라미터로 자동 주입해주는 어노테이션
            @Valid @RequestBody StudyGroupMemberDTO dto) {
        dto.setStudyGroupId(studyGroupId);
        dto.setStudyMemberStatus(StudyMemberStatus.PENDING);
        StudyGroupMemberDTO joined = studyGroupMemberService.joinStudyGroup(studyGroupId, userId, dto);
        return ResponseEntity.status(SuccessCode.INSERT.getStatus())
                .body(ApiResponse.<StudyGroupMemberDTO>builder()
                        .statusCode(SuccessCode.INSERT.getStatus())
                        .message(SuccessCode.INSERT.getMessage())
                        .result(joined)
                        .build());
    }

    @PutMapping("/{memberId}/approve")
    public ResponseEntity<ApiResponse<StudyGroupMemberDTO>> approveMember(
            @PathVariable("studyGroupId") Integer studyGroupId,
            @PathVariable("memberId") Integer memberId,
            @AuthenticationPrincipal Integer userId) {
        StudyGroupMemberDTO updated = studyGroupMemberService.approveMember(studyGroupId, memberId, userId);
        return ResponseEntity.ok(ApiResponse.<StudyGroupMemberDTO>builder()
                .statusCode(SuccessCode.UPDATE.getStatus())
                .message("참가 신청이 승인되었습니다.")
                .result(updated)
                .build());
    }

    @PutMapping("/{memberId}/reject")
    public ResponseEntity<ApiResponse<StudyGroupMemberDTO>> rejectMember(
            @PathVariable("studyGroupId") Integer studyGroupId,
            @PathVariable("memberId") Integer memberId,
            @AuthenticationPrincipal Integer userId) {
        StudyGroupMemberDTO updated = studyGroupMemberService.rejectMember(studyGroupId, memberId, userId);
        return ResponseEntity.ok(ApiResponse.<StudyGroupMemberDTO>builder()
                .statusCode(SuccessCode.UPDATE.getStatus())
                .message("참가 신청이 거부되었습니다.")
                .result(updated)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudyGroupMemberDTO>>> getMembers(
            @PathVariable("studyGroupId") Integer studyGroupId) {
        List<StudyGroupMemberDTO> members = studyGroupMemberService.getMembers(studyGroupId);
        return ResponseEntity.ok(ApiResponse.<List<StudyGroupMemberDTO>>builder()
                .statusCode(SuccessCode.SELECT.getStatus())
                .message(SuccessCode.SELECT.getMessage())
                .result(members)
                .build());
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable("studyGroupId") Integer studyGroupId,
            @PathVariable("memberId") Integer memberId,
            @AuthenticationPrincipal Integer userId) {
        studyGroupMemberService.deleteMember(studyGroupId, memberId, userId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .statusCode(SuccessCode.DELETE.getStatus())
                .message(SuccessCode.DELETE.getMessage())
                .build());
    }
}
