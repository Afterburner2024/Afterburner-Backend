package com.afterburner.user.model;

import com.afterburner.common.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@BatchSize(size = 100)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String userName;

    private String userPhoneNumber;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registeredAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt; // 논리적 삭제를 위한 필드

    private String note;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT") // JSON 문자열로 저장될 수 있도록 TEXT 타입으로 설정
    private List<String> userTechStacks;

    private String userImage; // 사용자 프로필 이미지 URL 또는 경로

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGrade userGrade; // 사용자 등급

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
