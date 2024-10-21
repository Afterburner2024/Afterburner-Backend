package com.afterburner.project.projectComment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afterburner.project.projectComment.service.ProjectCommentsService;

@RestController
@RequestMapping("/api/v1/project/{communityId}/comment")
public class ProjectCommentsController {

	private final ProjectCommentsService communityService;

	@Autowired
	public ProjectCommentsController(ProjectCommentsService communityService) {
		this.communityService = communityService;
	}

}
