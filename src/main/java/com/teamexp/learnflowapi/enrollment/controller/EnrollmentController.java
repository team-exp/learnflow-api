package com.teamexp.learnflowapi.enrollment.controller;

import com.teamexp.learnflowapi.enrollment.dto.EnrollmentRequest;
import com.teamexp.learnflowapi.enrollment.dto.EnrollmentResponse;
import com.teamexp.learnflowapi.enrollment.service.EnrollmentService;
import com.teamexp.learnflowapi.global.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<EnrollmentResponse>> create(@Valid @RequestBody EnrollmentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(enrollmentService.createEnrollment(request)));
    }
}
