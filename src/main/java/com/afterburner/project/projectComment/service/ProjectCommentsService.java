package com.afterburner.project.projectComment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afterburner.project.projectComment.repository.ProjectCommentsRepository;

@Service
public class ProjectCommentsService {

	private final ProjectCommentsRepository communityRepository;

	@Autowired
	public ProjectCommentsService(ProjectCommentsRepository communityRepository) {
		this.communityRepository = communityRepository;
	}


}

