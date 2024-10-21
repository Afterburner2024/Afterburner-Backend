package com.afterburner.community.communityComment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afterburner.community.communityComment.model.CommunityComments;

@Repository
public interface CommunityCommentsRepository extends JpaRepository<CommunityComments, Integer> {

}
