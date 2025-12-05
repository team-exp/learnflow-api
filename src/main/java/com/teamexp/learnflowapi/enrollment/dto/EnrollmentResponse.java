package com.teamexp.learnflowapi.enrollment.dto;

import com.teamexp.learnflowapi.enrollment.model.Enrollment;
import com.teamexp.learnflowapi.enrollment.model.EnrollmentStatus;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public record EnrollmentResponse(
        Long userId,
        Long lectureId,
        EnrollmentStatus enrollmentStatus,
        Integer progress,
        OffsetDateTime enrolledAt,
        OffsetDateTime updatedAt
) {
    public static EnrollmentResponse from(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getUserId(),
                enrollment.getLectureId(),
                enrollment.getStatus(),
                enrollment.getProgress(),
                enrollment.getEnrolledAt().atOffset(ZoneOffset.ofHours(9)).truncatedTo(ChronoUnit.SECONDS),
                enrollment.getUpdatedAt().atOffset(ZoneOffset.ofHours(9)).truncatedTo(ChronoUnit.SECONDS));
    }
}
