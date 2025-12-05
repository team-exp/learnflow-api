package com.teamexp.learnflowapi.review.service;

// 1. 강의 존재 확인
// 2. 생성자 검증
// 3. 중복 확인
// 4. 수강 확인
// 5. 진도율 확인
// 6. 저장

import com.teamexp.learnflowapi.enrollment.model.Enrollment;
import com.teamexp.learnflowapi.enrollment.repository.CompletedLessonRepository;
import com.teamexp.learnflowapi.enrollment.repository.EnrollmentRepository;
import com.teamexp.learnflowapi.lecture.model.Lecture;
import com.teamexp.learnflowapi.lecture.repository.LectureRepository;
import com.teamexp.learnflowapi.review.dto.ReviewRequest;
import com.teamexp.learnflowapi.review.dto.ReviewResponse;
import com.teamexp.learnflowapi.review.model.Review;
import com.teamexp.learnflowapi.review.model.ReviewStatus;
import com.teamexp.learnflowapi.review.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final LectureRepository lectureRepository;
    private  final EnrollmentRepository enrollmentRepository;
    private final CompletedLessonRepository completedLessonRepository;

    public ReviewService(ReviewRepository reviewRepository, LectureRepository lectureRepository, EnrollmentRepository enrollmentRepository, CompletedLessonRepository completedLessonRepository) {
        this.reviewRepository = reviewRepository;
        this.lectureRepository = lectureRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.completedLessonRepository = completedLessonRepository;
    }

    @Transactional
    public ReviewResponse createReview(String userId, ReviewRequest request) {
        Lecture lecture  = lectureRepository.findById(request.lectureId())
            .orElseThrow(()-> new IllegalArgumentException("존재하지 않은 강의입니다."));
        // TODO: Lecture 엔티티에 getInstructorId() 메서드가 있다고 가정
        if (lecture.getInstructorId().equals(userId)) {
            throw new IllegalStateException("본인의 강의에는 리뷰를 작성할 수 없습니다.");
        }

        if (reviewRepository.existsByUserIdAndLectureId(userId, request.lectureId())) {
            throw new IllegalStateException("이미 해당 강의에 대한 리뷰를 작성하셨습니다.");
        }

        Enrollment enrollment = enrollmentRepository.findByUserIdAndLectureId(userId, request.lectureId())
            .orElseThrow(() -> new IllegalStateException("수강 신청하지 않은 강의입니다."));

        int completedCount = completedLessonRepository.countByEnrollmentId(enrollment.getId());
        if (completedCount < 3) {
            throw new IllegalStateException("최소 3개의 레슨을 수강 완료해야 리뷰룰 작성할 수 있습니다.");
        }

        Review review = Review.create(userId, request.lectureId(), request.content(), request.rating());
        Review savedReview = reviewRepository.save(review);
        String tempNickname = "User_" + userId.substring(0, 8);
        return ReviewResponse.of(savedReview,tempNickname);
    }

    public Page<ReviewResponse> getReviewsByLecture(Long lectureId, Pageable pageable) {
        // 1. POSTED 상태인 리뷰만 페이징 조회
        Page<Review> reviewPage = reviewRepository.findByLectureIdAndStatus(
            lectureId,
            ReviewStatus.POSTED,
            pageable
        );

        // 2. DTO 변환(닉네임 임시 처리)
        return reviewPage.map(review->{
            String tempNickname = "User_" + review.getUserId().substring(0,8);
            return ReviewResponse.of(review, tempNickname);
        });
    }

    // [new] 내 리뷰 조회
    public Page<ReviewResponse> getMyReviews(String userId, Pageable pageable) {
        // 1. 내 리뷰 조회(상태 상관없이 모두 조회 or 정책에 따라 POSTED만 조회)
        // 정책: 내 리뷰는 BLINDED 된 것도 내가 볼 수는 있어야 하므로 상태 필터링 없이 조회
        Page<Review> reviewPage = reviewRepository.findByUserId(userId, pageable);

        return reviewPage.map(review->{
            // 내 닉네임은 "Me"로 표시하거나 동일하게 처리
            String tempNickname = "Me";
            return ReviewResponse.of(review, tempNickname);
        });
    }
}
