package com.afterburner.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afterburner.community.model.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {

}
