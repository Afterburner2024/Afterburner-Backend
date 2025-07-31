package com.afterburner.studygroup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterburner.studygroup.model.StudyMemberStatus;
import com.afterburner.studygroup.model.entity.StudyGroupMemberEntity;

public interface StudyGroupMemberRepository extends JpaRepository<StudyGroupMemberEntity, Integer> {

    List<StudyGroupMemberEntity> findByStudyGroupIdAndStudyMemberStatusIn(Integer studyGroupId, List<StudyMemberStatus> statuses);

    Optional<StudyGroupMemberEntity> findByStudyMemberIdAndStudyGroupId(Integer studyMemberId, Integer studyGroupId);

    Optional<StudyGroupMemberEntity> findByStudyGroupIdAndStudyMemberUserId(Integer studyGroupId, Integer studyMemberUserId);

    List<StudyGroupMemberEntity> findByStudyMemberUserId(Integer studyMemberUserId);
}
