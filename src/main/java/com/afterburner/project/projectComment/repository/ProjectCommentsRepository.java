package com.afterburner.project.projectComment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afterburner.project.projectComment.model.ProjectComments;

@Repository
public interface ProjectCommentsRepository extends JpaRepository<ProjectComments, Integer> {

}
