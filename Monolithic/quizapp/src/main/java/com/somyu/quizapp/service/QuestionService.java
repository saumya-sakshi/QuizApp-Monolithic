package com.somyu.quizapp.service;

import com.somyu.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.somyu.quizapp.repository.QuestionRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getAllQuestions() {

        try{
            return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }

    }

    public  ResponseEntity<List<Question>>getByCategory(String  category) {

        try{
            return new ResponseEntity<>(questionRepo.findAllByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> createQuestion(Question question) {
       try{
        questionRepo.save(question);
           return new ResponseEntity<>("Question created",HttpStatus.CREATED);
       }catch(Exception e){
           return new ResponseEntity<>("Error on saving question"+e.getMessage(), HttpStatus.BAD_REQUEST);
       }


    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            questionRepo.deleteById(id);
            return new ResponseEntity<>("Question deleted with id " + id + " Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error on deleting question with id " + id + " because - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Question> updateQuestion(Question question) {
       try {
           Question n = questionRepo.findById(question.getId()).get();
           n.setCategory(question.getCategory());
           n.setQuestionTitle(question.getQuestionTitle());
           n.setDifficultylevel(question.getDifficultylevel());
           n.setOption1(question.getOption1());
           n.setOption2(question.getOption2());
           n.setOption3(question.getOption3());
           n.setOption4(question.getOption4());
           n.setRightAnswer(question.getRightAnswer());
           questionRepo.save(n);
           return new ResponseEntity<>(n,HttpStatus.OK);

       }
       catch (Exception e){
           return new ResponseEntity<>(new Question(),HttpStatus.BAD_REQUEST);
       }
    }

    public ResponseEntity<String> deleteQuestionByName(String name) {
        try{
            Question n = questionRepo.findByQuestionTitle(name);
            questionRepo.delete(n);
            return new ResponseEntity<>("Question deleted with name " + name, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error on deleting question with name " + name, HttpStatus.BAD_REQUEST);
        }
    }
}
