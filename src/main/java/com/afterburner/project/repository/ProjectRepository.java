package com.afterburner.project.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.afterburner.project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @EntityGraph(attributePaths = "user")
    java.util.List<Project> findByProjectUserId(Integer projectUserId);
}
