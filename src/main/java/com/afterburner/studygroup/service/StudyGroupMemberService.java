package com.afterburner.studygroup.service;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.global.exception.StudyGroupApplicationException;
import com.afterburner.global.exception.TeamMemberNotFoundException;
import com.afterburner.studygroup.model.StudyMemberStatus;
import com.afterburner.studygroup.model.dto.StudyGroupMemberDTO;
import com.afterburner.studygroup.model.entity.StudyGroupEntity;
import com.afterburner.studygroup.model.entity.StudyGroupMemberEntity;
import com.afterburner.studygroup.repository.StudyGroupMemberRepository;
import com.afterburner.studygroup.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyGroupMemberService {

    private final StudyGroupMemberRepository studyGroupMemberRepository;
    private final StudyGroupRepository studyGroupRepository;

    private StudyGroupMemberDTO toDto(StudyGroupMemberEntity entity) {
        return StudyGroupMemberDTO.builder()
                .studyMemberId(entity.getStudyMemberId())
                .studyGroupId(entity.getStudyGroupId())
                .studyMemberUserId(entity.getStudyMemberUserId())
                .studyMemberRole(entity.getStudyMemberRole())
                .studyMemberStatus(entity.getStudyMemberStatus())
                .studyMemberJoinedAt(entity.getStudyMemberJoinedAt())
                .studyMemberDeletedAt(entity.getStudyMemberDeletedAt())
                .build();
    }

    // 멤버 참가
    // 한번 거절당했던 유저는 신청 금지
    @Transactional
    public StudyGroupMemberDTO joinStudyGroup(Integer groupId, Integer userId, StudyGroupMemberDTO dto) {
        studyGroupMemberRepository.findByStudyGroupIdAndStudyMemberUserId(groupId, userId)
                .ifPresent(member -> {
                    if (member.getStudyMemberStatus() == StudyMemberStatus.REJECTED) {
                        throw new StudyGroupApplicationException(ErrorCode.REJECTED_APPLICATION);
                    }
                    if (member.getStudyMemberStatus() == StudyMemberStatus.PENDING ||
                            member.getStudyMemberStatus() == StudyMemberStatus.APPROVED) {
                        throw new StudyGroupApplicationException(ErrorCode.ALREADY_APPLIED);
                    }
                });

        StudyGroupMemberEntity entity = StudyGroupMemberEntity.builder()
                .studyGroupId(groupId)
                .studyMemberUserId(userId)
                .studyMemberRole(dto.getStudyMemberRole())
                .studyMemberStatus(StudyMemberStatus.PENDING)
                .build();
        StudyGroupMemberEntity saved = studyGroupMemberRepository.save(entity);
        return toDto(saved);
    }

    // 멤버 승인 (리더만 가능)
    @Transactional
    public StudyGroupMemberDTO approveMember(Integer groupId, Integer memberId, Integer currentUserId) {
        StudyGroupEntity studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new TeamMemberNotFoundException(ErrorCode.NOT_FOUND));

        if (!Objects.equals(studyGroup.getStudyGroupUserId(), currentUserId)) {
            throw new StudyGroupApplicationException(ErrorCode.NOT_STUDY_GROUP_LEADER);
        }

        StudyGroupMemberEntity entity = studyGroupMemberRepository
                .findByStudyMemberIdAndStudyGroupId(memberId, groupId)
                .orElseThrow(() -> new TeamMemberNotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        entity.setStudyMemberStatus(StudyMemberStatus.APPROVED);
        studyGroupMemberRepository.save(entity);
        return toDto(entity);
    }

    // 멤버 거절(리더)
    @Transactional
    public StudyGroupMemberDTO rejectMember(Integer groupId, Integer memberId, Integer currentUserId) {
        StudyGroupEntity studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new TeamMemberNotFoundException(ErrorCode.NOT_FOUND));

        if (!Objects.equals(studyGroup.getStudyGroupUserId(), currentUserId)) {
            throw new StudyGroupApplicationException(ErrorCode.NOT_STUDY_GROUP_LEADER);
        }

        StudyGroupMemberEntity entity = studyGroupMemberRepository
                .findByStudyMemberIdAndStudyGroupId(memberId, groupId)
                .orElseThrow(() -> new TeamMemberNotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        entity.setStudyMemberStatus(StudyMemberStatus.REJECTED);
        studyGroupMemberRepository.save(entity);
        return toDto(entity);
    }

    // 멤버 조회
    public List<StudyGroupMemberDTO> getMembers(Integer groupId) {
        List<StudyMemberStatus> statuses = Arrays.asList(StudyMemberStatus.APPROVED, StudyMemberStatus.PENDING);
        return studyGroupMemberRepository
                .findByStudyGroupIdAndStudyMemberStatusIn(groupId, statuses)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 멤버 등록 취소 및 삭제
    @Transactional
    public boolean deleteMember(Integer groupId, Integer memberId, Integer currentUserId) {
        StudyGroupEntity studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new TeamMemberNotFoundException(ErrorCode.NOT_FOUND));

        StudyGroupMemberEntity member = studyGroupMemberRepository.findById(memberId)
                .orElseThrow(() -> new TeamMemberNotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        boolean isLeader = Objects.equals(studyGroup.getStudyGroupUserId(), currentUserId);
        boolean isSelf = Objects.equals(member.getStudyMemberUserId(), currentUserId);

        if (!isLeader && !isSelf) {
            throw new StudyGroupApplicationException(ErrorCode.FORBIDDEN);
        }

        member.setStudyMemberStatus(StudyMemberStatus.DELETED);
        member.setStudyMemberDeletedAt(LocalDateTime.now());
        studyGroupMemberRepository.save(member);
        return true;
    }
}
