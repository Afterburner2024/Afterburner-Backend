package com.afterburner.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afterburner.project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    java.util.List<Project> findByProjectUserId(Integer projectUserId);
}
