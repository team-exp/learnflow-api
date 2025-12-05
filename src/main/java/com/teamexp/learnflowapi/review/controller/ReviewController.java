package com.teamexp.learnflowapi.review.controller;

import com.teamexp.learnflowapi.global.response.BaseResponse;
import com.teamexp.learnflowapi.review.dto.ReviewRequest;
import com.teamexp.learnflowapi.review.dto.ReviewResponse;
import com.teamexp.learnflowapi.review.service.ReviewService;
import com.teamexp.learnflowapi.user.config.CustomUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public BaseResponse<ReviewResponse> createReview(
        @AuthenticationPrincipal CustomUserPrincipal principal,
        @Valid @RequestBody ReviewRequest request
        ) {
        String userId = principal.getId();

        ReviewResponse response = reviewService.createReview(userId, request);
        return BaseResponse.ok(response);
    }
}
