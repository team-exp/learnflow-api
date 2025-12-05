package com.teamexp.learnflowapi.enrollment.repository;


import com.teamexp.learnflowapi.enrollment.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByUserIdAndLectureId(Long userId, Long lectureId);
}
