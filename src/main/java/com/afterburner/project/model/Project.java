package com.afterburner.project.model;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import com.afterburner.common.enums.PostStatus;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.afterburner.user.model.User;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {

	@Id
	@Column(name = "project_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectId;

	@Column(name = "project_title", nullable = false, columnDefinition = "VARCHAR(30)")
	private String projectTitle;

	@Column(name = "project_summary", nullable = false, columnDefinition = "VARCHAR(20)")
	private String projectSummary;

	@Column(name = "project_content", nullable = false, columnDefinition = "VARCHAR(5000)")
	private String projectContent;

	@Column(name = "project_link", columnDefinition = "VARCHAR(255)")
	private String projectLink;

	@Column(name = "project_created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime projectCreatedAt;

	@Column(name = "project_updated_at")
	private LocalDateTime projectUpdatedAt;

	@Column(name = "project_deleted_at")
	private LocalDateTime projectDeletedAt;

	@Column(name = "project_finished_at")
	private LocalDateTime projectFinishedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "project_status", nullable = false)
	private PostStatus projectStatus;

	@Column(name = "project_tech_stack", columnDefinition = "jsonb")
	@Type(JsonBinaryType.class)
	private List<String> projectTechStack;

        @Column(name = "project_user_id", nullable = false)
        private Integer projectUserId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "project_user_id", insertable = false, updatable = false)
        private User user;

	@Column(name = "project_recruitment_roles", columnDefinition = "jsonb")
	@Type(JsonBinaryType.class)
	private List<String> projectRecruitmentRoles;

	@Column(name = "project_region", columnDefinition = "VARCHAR(255)")
	private String projectRegion;

	@Column(name = "project_team_id")
	private Integer projectTeamId;

	@PrePersist // 처음 저장될 때 호출됨
	public void prePersist() {
		this.projectUpdatedAt = null;
	}

	@PreUpdate // 수정될 때 호출됨
	public void preUpdate() {
		this.projectUpdatedAt = LocalDateTime.now();
	}

}