package com.somyu.quizapp.controller;

import com.somyu.quizapp.model.QuestionWrapper;
import com.somyu.quizapp.model.Quiz;
import com.somyu.quizapp.model.Response;
import com.somyu.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam Integer noOfQuestion, @RequestParam String title) {
    return quizService.createQuiz(category, noOfQuestion, title);
    }

    @GetMapping("getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable Integer id) {

        return quizService.getQuizById(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id,responses);
    }

}
