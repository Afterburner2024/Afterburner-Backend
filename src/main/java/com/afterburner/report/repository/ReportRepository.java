package com.afterburner.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afterburner.report.model.entity.ReportEntity;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
}
