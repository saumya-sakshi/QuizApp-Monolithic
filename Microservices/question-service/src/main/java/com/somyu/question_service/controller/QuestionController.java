package com.somyu.question_service.controller;


import com.somyu.question_service.model.Question;
import com.somyu.question_service.model.QuestionWrapper;
import com.somyu.question_service.model.Response;
import com.somyu.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestion(){

        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public  ResponseEntity<List<Question>> getByCategory(@PathVariable String category){

        return questionService.getByCategory(category);
    }

    @PostMapping("createQuestion")
    public ResponseEntity<String>  createQuestion(@RequestBody Question question){
        return questionService.createQuestion(question);
    }
    @DeleteMapping("deleteQuestion")
    public ResponseEntity<String>  deleteQuestion(@RequestParam Integer id){
        return questionService.deleteQuestion(id);
    }

    @PutMapping("update")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }
    @DeleteMapping("deleteQuestionByName")
    public ResponseEntity<String>  deleteQuestionByName(@RequestParam String name){
        return questionService.deleteQuestionByName(name);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestion(
            @RequestParam String category,
            @RequestParam Integer noOfQuestions
    ){
        return questionService.generateQuestions(category,noOfQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> ids){
        //System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestions(ids);
    }

    @PostMapping("score")
    public ResponseEntity<String> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }








}
