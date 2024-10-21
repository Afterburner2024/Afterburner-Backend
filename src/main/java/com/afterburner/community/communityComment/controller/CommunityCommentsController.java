package com.afterburner.community.communityComment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.afterburner.community.communityComment.service.CommunityCommentsService;

@RestController
@RequestMapping("/api/v1/community/{communityId}/comment")
public class CommunityCommentsController {

	private final CommunityCommentsService communityService;

	@Autowired
	public CommunityCommentsController(CommunityCommentsService communityService) {
		this.communityService = communityService;
	}

}
