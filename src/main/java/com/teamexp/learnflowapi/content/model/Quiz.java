package com.teamexp.learnflowapi.content.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lesson_quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long lessonId;
    private int orderIndex;
    private String question;
    private boolean correct;
}
