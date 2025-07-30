package com.afterburner.studygroup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afterburner.common.codes.SuccessCode;
import com.afterburner.common.response.ApiResponse;
import com.afterburner.studygroup.model.dto.StudyGroupMemberDTO;
import com.afterburner.studygroup.model.StudyMemberStatus;
import com.afterburner.studygroup.service.StudyGroupMemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/study-group/{studyGroupId}/member")
public class StudyGroupMemberController {

    private final StudyGroupMemberService studyGroupMemberService;

    @Autowired
    public StudyGroupMemberController(StudyGroupMemberService studyGroupMemberService) {
        this.studyGroupMemberService = studyGroupMemberService;
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<StudyGroupMemberDTO>> joinStudyGroup(
            @PathVariable("studyGroupId") Integer studyGroupId,
            @Valid @RequestBody StudyGroupMemberDTO dto) {
        dto.setStudyGroupId(studyGroupId);
        dto.setStudyMemberStatus(StudyMemberStatus.PENDING);
        StudyGroupMemberDTO joined = studyGroupMemberService.joinStudyGroup(studyGroupId, dto);
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
            @PathVariable("memberId") Integer memberId) {
        StudyGroupMemberDTO updated = studyGroupMemberService.approveMember(studyGroupId, memberId);
        return ResponseEntity.ok(ApiResponse.<StudyGroupMemberDTO>builder()
                .statusCode(SuccessCode.UPDATE.getStatus())
                .message("참가 신청이 승인되었습니다.")
                .result(updated)
                .build());
    }

    @PutMapping("/{memberId}/reject")
    public ResponseEntity<ApiResponse<StudyGroupMemberDTO>> rejectMember(
            @PathVariable("studyGroupId") Integer studyGroupId,
            @PathVariable("memberId") Integer memberId) {
        StudyGroupMemberDTO updated = studyGroupMemberService.rejectMember(studyGroupId, memberId);
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
            @PathVariable("memberId") Integer memberId) {
        studyGroupMemberService.deleteMember(studyGroupId, memberId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .statusCode(SuccessCode.DELETE.getStatus())
                .message(SuccessCode.DELETE.getMessage())
                .build());
    }
}
