package com.afterburner.studygroup.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afterburner.common.codes.ErrorCode;
import com.afterburner.global.exception.TeamMemberNotFoundException;
import com.afterburner.studygroup.model.StudyMemberStatus;
import com.afterburner.studygroup.model.dto.StudyGroupMemberDTO;
import com.afterburner.studygroup.model.entity.StudyGroupMemberEntity;
import com.afterburner.studygroup.repository.StudyGroupMemberRepository;

@Service
public class StudyGroupMemberService {

    private final StudyGroupMemberRepository studyGroupMemberRepository;

    @Autowired
    public StudyGroupMemberService(StudyGroupMemberRepository studyGroupMemberRepository) {
        this.studyGroupMemberRepository = studyGroupMemberRepository;
    }

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

    @Transactional
    public StudyGroupMemberDTO joinStudyGroup(Integer groupId, StudyGroupMemberDTO dto) {
        StudyGroupMemberEntity entity = StudyGroupMemberEntity.builder()
                .studyGroupId(groupId)
                .studyMemberUserId(dto.getStudyMemberUserId())
                .studyMemberRole(dto.getStudyMemberRole())
                .studyMemberStatus(StudyMemberStatus.PENDING)
                .build();
        StudyGroupMemberEntity saved = studyGroupMemberRepository.save(entity);
        return toDto(saved);
    }

    @Transactional
    public StudyGroupMemberDTO approveMember(Integer groupId, Integer memberId) {
        StudyGroupMemberEntity entity = studyGroupMemberRepository
                .findByStudyMemberIdAndStudyGroupId(memberId, groupId)
                .orElseThrow(() -> new TeamMemberNotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        entity.setStudyMemberStatus(StudyMemberStatus.APPROVED);
        studyGroupMemberRepository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public StudyGroupMemberDTO rejectMember(Integer groupId, Integer memberId) {
        StudyGroupMemberEntity entity = studyGroupMemberRepository
                .findByStudyMemberIdAndStudyGroupId(memberId, groupId)
                .orElseThrow(() -> new TeamMemberNotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        entity.setStudyMemberStatus(StudyMemberStatus.REJECTED);
        studyGroupMemberRepository.save(entity);
        return toDto(entity);
    }

    public List<StudyGroupMemberDTO> getMembers(Integer groupId) {
        List<StudyMemberStatus> statuses = Arrays.asList(StudyMemberStatus.APPROVED, StudyMemberStatus.PENDING);
        return studyGroupMemberRepository
                .findByStudyGroupIdAndStudyMemberStatusIn(groupId, statuses)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteMember(Integer groupId, Integer memberId) {
        StudyGroupMemberEntity entity = studyGroupMemberRepository
                .findByStudyMemberIdAndStudyGroupId(memberId, groupId)
                .orElseThrow(() -> new TeamMemberNotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        entity.setStudyMemberStatus(StudyMemberStatus.DELETED);
        entity.setStudyMemberDeletedAt(LocalDateTime.now());
        studyGroupMemberRepository.save(entity);
        return true;
    }
}
