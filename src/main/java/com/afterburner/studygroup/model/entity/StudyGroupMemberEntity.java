package com.afterburner.studygroup.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.afterburner.common.enums.TeamMemberRole;
import com.afterburner.studygroup.model.StudyMemberStatus;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "study_group_member")
public class StudyGroupMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_member_id")
    private Integer studyMemberId;

    @Column(name = "study_group_id", nullable = false)
    private Integer studyGroupId;

    @Column(name = "study_member_user_id", nullable = false)
    private Integer studyMemberUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "study_member_role", nullable = false)
    private TeamMemberRole studyMemberRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "study_member_status", nullable = false)
    private StudyMemberStatus studyMemberStatus;

    @CreationTimestamp
    @Column(name = "study_member_joined_at", nullable = false)
    private LocalDateTime studyMemberJoinedAt;

    @Column(name = "study_member_deleted_at")
    private LocalDateTime studyMemberDeletedAt;
}
