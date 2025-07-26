package com.afterburner.studygroup.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import com.afterburner.studygroup.model.entity.StudyRole;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.afterburner.common.enums.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "studygroup")
public class StudyGroupEntity {

	@Id
	@Column(name = "studygroup_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studyGroupId;

	@Column(name = "studygroup_category", nullable = false)
	private List<String> studyGroupCategory;

	@Column(name = "studygroup_title", nullable = false, columnDefinition = "VARCHAR(30)")
	private String studyGroupTitle;

	@Column(name = "studygroup_content", nullable = false, columnDefinition = "VARCHAR(5000)")
	private String studyGroupContent;

	@Column(name = "studygroup_created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime studyGroupCreatedAt;

	@Column(name = "studygroup_updated_at")
	private LocalDateTime studyGroupUpdatedAt;

	@Column(name = "studygroup_deleted_at")
	private LocalDateTime studyGroupDeletedAt;

	@Column(name = "studygroup_status", nullable = false) // 기본값, 삭제상태, 신고된 상태
        private PostStatus studyGroupStatus;

        @Column(name = "studygroup_user_id", nullable = false) // 작성자 ID
        private Integer studyGroupUserId;

        @Column(name = "studygroup_role", nullable = false)
        @Enumerated(EnumType.STRING)
        private StudyRole studyGroupRole;

        @Column(name = "studygroup_members", columnDefinition = "jsonb")
        @Type(JsonBinaryType.class)
        private List<String> studyGroupMembers;


	@PrePersist // 엔티티에 처음 저장될 때에만 동작함
	public void prePersist() {
		this.studyGroupUpdatedAt = null; // 수정칸은 비어있도록
	}

	@PreUpdate // 엔티티가 수정될때 호출됨
	public void preUpdate() {
		this.studyGroupUpdatedAt = LocalDateTime.now(); // 수정될때는 현재 시간으로 호출되게 함
	}

}
