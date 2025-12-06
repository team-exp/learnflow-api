package com.teamexp.learnflowapi.review.dto;

import com.teamexp.learnflowapi.review.model.Review;

import java.time.Instant;

public record ReviewResponse(
    Long reviewId,
    Long lectureId,
    String userId,
    String nickname,    // [new] 작성자 닉네임
    Integer rating,
    String content,
    String instructorReply, // [new] 강사 답글
    Instant createdAt
) {
    public static ReviewResponse of(Review review, String nickname) {
        return new ReviewResponse(
            review.getId(),
            review.getLectureId(),
            review.getUserId(),
            nickname,
            review.getRating(),
            review.getContent(),
            review.getInstructorReply(),
            review.getCreatedAt()
        );
    }
}
