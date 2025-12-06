package com.teamexp.learnflowapi.review.repository;

import com.teamexp.learnflowapi.review.model.Review;
import com.teamexp.learnflowapi.review.model.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserIdAndLectureId(String userId, Long lectureId);
    // [new] 강의별 리뷰 조회(상태 필터링 + 페이징)
    Page<Review> findByLectureIdAndStatus(Long lectureId, ReviewStatus status, Pageable pageable);

    Page<Review> findByUserId(String userId, Pageable pageable);
}
