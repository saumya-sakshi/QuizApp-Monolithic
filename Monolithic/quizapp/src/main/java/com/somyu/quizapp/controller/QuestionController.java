package com.somyu.quizapp.controller;


import com.somyu.quizapp.model.Question;
import com.somyu.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {


    @Autowired
    private QuestionService questionService;

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





}
