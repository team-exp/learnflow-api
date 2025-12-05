package com.teamexp.learnflowapi.enrollment.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "completed_lesson", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"enrollment_id", "lesson_id"})
})
public class CompletedLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completed_lesson_id")
    private Long id;

    @Column(name = "enrollment_id", nullable = false)
    private Long enrollmentId;

    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

//  TODO 협의 이후 수정 예정
    @Column(name = "completed_at", columnDefinition = "DATETIME(0)", nullable = false)
    private LocalDateTime completedAt;

    protected CompletedLesson() {}

    private CompletedLesson(Long enrollmentId, Long lessonId, LocalDateTime completedAt) {
//      TODO 확인 필요
        if (enrollmentId == null || enrollmentId <= 0) throw new IllegalArgumentException("enrollmentId cannot be null or zero or negative");
        if (lessonId == null || lessonId <= 0) throw new IllegalArgumentException("lessonId cannot be null or zero or negative");
        if (completedAt == null) throw new IllegalArgumentException("completedAt cannot be null");

        this.enrollmentId = enrollmentId;
        this.lessonId = lessonId;
        this.completedAt = completedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
