package com.somyu.quizapp.service;

import com.somyu.quizapp.model.Question;
import com.somyu.quizapp.model.QuestionWrapper;
import com.somyu.quizapp.model.Quiz;
import com.somyu.quizapp.model.Response;
import com.somyu.quizapp.repository.QuestionRepo;
import com.somyu.quizapp.repository.QuizRepo;
import lombok.extern.slf4j.Slf4j;
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
    QuestionRepo questionRepo;


    public ResponseEntity<String> createQuiz(String category, Integer noOfQuestion, String title) {
        try{
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category,noOfQuestion);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
       quizRepo.save(quiz);

        return  new ResponseEntity<>("Success", HttpStatus.CREATED);}
        catch(Exception e){
            return  new ResponseEntity<>("Error "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer id) {
       try {
           Optional<Quiz> quiz = quizRepo.findById(id);
           List<Question> questionsFromDB = quiz.get().getQuestions();
           List<QuestionWrapper> ques = new ArrayList<QuestionWrapper>();
            for (Question q :  questionsFromDB) {
                QuestionWrapper qw = new  QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
                ques.add(qw);

            }
           return new ResponseEntity<>(ques, HttpStatus.OK);
       } catch (Exception e) {
           log.error(e.getMessage());
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }


    }

    public ResponseEntity<String> calculateResult(Integer id, List<Response> responses) {
        try {
            Optional<Quiz> quiz= quizRepo.findById(id);
            List<Question> questionsFromDB = quiz.get().getQuestions();
            int right =0;
            int i =0;
            for(Response r:responses){
                if(r.getResponse().equals(questionsFromDB.get(i).getRightAnswer())){right++;}
                i++;
            }
            return new ResponseEntity<>("Your Result is "+right,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Getting Error in loading your Result because "+e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
