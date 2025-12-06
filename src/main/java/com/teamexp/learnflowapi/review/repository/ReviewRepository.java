package com.teamexp.learnflowapi.review.repository;

import com.teamexp.learnflowapi.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 중복 작성 방지
    boolean existsByUserIdAndLectureId(String userId, Long lectureId);
    // 강의별 리뷰 조회
    // TODO: 페이징 상태 필터링은 Service에서 처리하거나 추후 쿼리 메서드 추가
    Page<Review> findByLectureId(Long lectureId, Pageable pageable);
    // 내 리뷰 조회
    Page<Review> findByUserId(String userId, Pageable pageable);

}
