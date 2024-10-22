package com.afterburner.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findAllByProjectStatusNot(PostStatus postStatus);

	Optional<Project> findByProjectIdAndProjectStatusNot(Integer projectId, PostStatus projectStatus);

}
