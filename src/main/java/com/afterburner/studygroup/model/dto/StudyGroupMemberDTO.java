package com.afterburner.studygroup.model.dto;

import java.time.LocalDateTime;

import com.afterburner.common.enums.TeamMemberRole;
import com.afterburner.studygroup.model.StudyMemberStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyGroupMemberDTO {

    private Integer studyMemberId;

    @NotNull(message = "스터디 게시글 id는 필수입니다.")
    private Integer studyGroupId;

    @NotNull(message = "사용자 id는 필수입니다.")
    private Integer studyMemberUserId;

    @NotNull(message = "팀원 역할은 필수입니다.")
    private TeamMemberRole studyMemberRole;

    private StudyMemberStatus studyMemberStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime studyMemberJoinedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime studyMemberDeletedAt;
}
