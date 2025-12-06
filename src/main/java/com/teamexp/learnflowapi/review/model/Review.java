package com.teamexp.learnflowapi.review.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "review", uniqueConstraints = {
    @UniqueConstraint(name = "uk_review_user_lecture",columnNames = {"user_id","lecture_id"})
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "instructor_reply", length = 1000)
    private String instructorReply;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReviewStatus status;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false)
    private Instant updatedAt;

    private Review(String userId, Long lectureId, String content, Integer rating) {
        validateRating(rating);
        this.userId = userId;
        this.lectureId = lectureId;
        this.content = content;
        this.rating = rating;
        this.status = ReviewStatus.POSTED;
    }

    public static Review create(String userId, Long lectureId, String content, Integer rating) {
        return new Review(userId, lectureId, content, rating);
    }

    public void reply(String replyContent) {
        this.instructorReply = replyContent;
    }

    private void validateRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1점에서 5점 사이여야 합니다.");
        }
    }
}
