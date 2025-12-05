package com.teamexp.learnflowapi.review.dto;

import com.teamexp.learnflowapi.review.model.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
    Long reviewId,
    Long lectureId,
    Long userId,
    Integer rating,
    String content,
    LocalDateTime createdAt
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
