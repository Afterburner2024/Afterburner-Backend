package com.afterburner.community.communityComment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afterburner.community.communityComment.repository.CommunityCommentsRepository;

@Service
public class CommunityCommentsService {

	private final CommunityCommentsRepository communityRepository;

	@Autowired
	public CommunityCommentsService(CommunityCommentsRepository communityRepository) {
		this.communityRepository = communityRepository;
	}


}

