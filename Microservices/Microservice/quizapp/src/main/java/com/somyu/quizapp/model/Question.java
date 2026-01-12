package com.somyu.quizapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Integer id;
        private String category;
        private String difficultylevel;
        private String questionTitle;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private String rightAnswer;
    }

