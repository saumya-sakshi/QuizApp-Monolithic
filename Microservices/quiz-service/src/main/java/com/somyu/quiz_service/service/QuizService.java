package com.somyu.quiz_service.service;


import com.somyu.quiz_service.feign.QuizInterface;
import com.somyu.quiz_service.model.QuestionDTO;
import com.somyu.quiz_service.model.QuestionWrapper;
import com.somyu.quiz_service.model.Quiz;
import com.somyu.quiz_service.model.Response;
import com.somyu.quiz_service.repository.QuizRepo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuizService {

    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(QuestionDTO questionDTO) {
        try{
        List<Integer> questions = quizInterface.generateQuestion(questionDTO.getCategory(),questionDTO.getNoOfQuestion()).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(questionDTO.getTitle());
        quiz.setQuestions(questions);
        quizRepo.save(quiz);
        return  new ResponseEntity<>("Success", HttpStatus.CREATED);}
        catch(Exception e){
            return  new ResponseEntity<>("Error "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer id) {
       try {
           Quiz quiz = quizRepo.findById(id).get();
           List<Integer> ids = quiz.getQuestions();
           List<QuestionWrapper> ques = quizInterface.getQuestions(ids).getBody();
           return new ResponseEntity<>(ques, HttpStatus.OK);
       } catch (Exception e) {
           log.error(e.getMessage());
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }


    }

    public ResponseEntity<String> calculateResult(Integer id, List<Response> responses) {
        try {
            String result = quizInterface.getScore(responses).getBody();
            return new ResponseEntity<>(result,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Getting Error in loading your Result because "+e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
