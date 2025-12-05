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
import com.teamexp.learnflowapi.review.repository.ReviewRepository;
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
    public ReviewResponse createReview(Long userId, ReviewRequest request) {
        // 1. 강의 존재 확인
        Lecture lecture  = lectureRepository.findById(request.lectureId())
            .orElseThrow(()-> new IllegalArgumentException("존재하지 않은 강의입니다."));
        // 2. 강의 생성자 검증(본인 강의 리뷰 작성 불가)
        // TODO: Lecture 엔티티에 getInstructorId() 메서드가 있다고 가정
        if (lecture.getInstructorId().equals(userId)) {
            throw new IllegalStateException("본인의 강의에는 리뷰를 작성할 수 없습니다.");
        }

        // 3. 중복 작성 방지
        if (reviewRepository.existsByUserIdAndLectureId(userId, request.lectureId())) {
            throw new IllegalStateException("이미 해당 강의에 대한 리뷰를 작성하셨습니다.");
        }

        // 4. 수강생 검증(Enrollment 존재 여부)
        Enrollment enrollment = enrollmentRepository.findByUserIdAndLectureId(userId, request.lectureId())
            .orElseThrow(() -> new IllegalStateException("수강 신청하지 않은 강의입니다."));
        // 5. 진도율 검증(완료된 Lesson 3개 이상)
        int completedCount = completedLessonRepository.countByEnrollmentId(enrollment.getId());
        if (completedCount < 3) {
            throw new IllegalStateException("최소 3개의 레슨을 수강 완료해야 리뷰룰 작성할 수 있습니다.");
        }

        // 6. 리뷰 저장
        Review review = Review.create(userId, request.lectureId(), request.content(), request.rating());
        Review savedReview = reviewRepository.save(review);

        return ReviewResponse.from(savedReview);
    }
}
