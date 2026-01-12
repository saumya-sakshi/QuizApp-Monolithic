package com.somyu.quiz_service.feign;


import com.somyu.quiz_service.model.QuestionWrapper;
import com.somyu.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> generateQuestion(
            @RequestParam String category,
            @RequestParam Integer noOfQuestions
    );

    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> ids);

    @PostMapping("question/score")
    public ResponseEntity<String> getScore(@RequestBody List<Response> responses);
}
