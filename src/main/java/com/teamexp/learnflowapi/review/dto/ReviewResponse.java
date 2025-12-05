package com.teamexp.learnflowapi.review.dto;

import com.teamexp.learnflowapi.review.model.Review;

import java.time.Instant;

public record ReviewResponse(
    Long reviewId,
    Long lectureId,
    String userId,
    Integer rating,
    String content,
    Instant createdAt
) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
            review.getId(),
            review.getLectureId(),
            review.getUserId(),
            review.getRating(),
            review.getContent(),
            review.getCreatedAt()
        );
    }
}
