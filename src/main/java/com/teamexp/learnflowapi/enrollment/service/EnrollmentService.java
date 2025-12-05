package com.teamexp.learnflowapi.enrollment.service;

import com.teamexp.learnflowapi.enrollment.dto.EnrollmentRequest;
import com.teamexp.learnflowapi.enrollment.dto.EnrollmentResponse;
import com.teamexp.learnflowapi.enrollment.model.Enrollment;
import com.teamexp.learnflowapi.enrollment.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public EnrollmentResponse createEnrollment(EnrollmentRequest request) {

        // 수강이 되있는지 확인
        if (enrollmentRepository.findByUserIdAndLectureId(request.userId(), request.lectureId()).isPresent()) {
//          TODO 예외 처리 수정 예정
            throw new IllegalStateException("Enrollment already exists");
        }

        Enrollment newEnrollment = enrollmentRepository.save(Enrollment.create(request.userId(), request.lectureId()));

//      TODO 생성된 Enrollment 확인(테스트), 반환 값 수정 예정
        return EnrollmentResponse.from(newEnrollment);
    }

}
