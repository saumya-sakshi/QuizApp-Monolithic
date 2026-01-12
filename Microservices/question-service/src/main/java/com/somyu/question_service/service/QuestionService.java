package com.somyu.question_service.service;

import com.somyu.question_service.model.Question;
import com.somyu.question_service.model.QuestionWrapper;
import com.somyu.question_service.model.Response;
import com.somyu.question_service.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getAllQuestions() {

        try {
            return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<List<Question>> getByCategory(String category) {

        try {
            return new ResponseEntity<>(questionRepo.findAllByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> createQuestion(Question question) {
        try {
            questionRepo.save(question);
            return new ResponseEntity<>("Question created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error on saving question" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(n, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new Question(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteQuestionByName(String name) {
        try {
            Question n = questionRepo.findByQuestionTitle(name);
            questionRepo.delete(n);
            return new ResponseEntity<>("Question deleted with name " + name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error on deleting question with name " + name, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Integer>> generateQuestions(String category, Integer noOfQuestions) {
        try {
            List<Integer> questions = questionRepo.findRandomQuestionsByCategory(category, noOfQuestions);
            return new ResponseEntity<>(questions, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> ids) {
        try {
            List<QuestionWrapper> questions = new ArrayList<>();
            for (Integer id : ids) {
                QuestionWrapper qw = new QuestionWrapper();
                Question q = questionRepo.findById(id).get();
                qw.setQuestionTitle(q.getQuestionTitle());
                qw.setId(q.getId());
                qw.setOption1(q.getOption1());
                qw.setOption2(q.getOption2());
                qw.setOption3(q.getOption3());
                qw.setOption4(q.getOption4());
                questions.add(qw);
            }
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }


    public ResponseEntity<String> getScore(List<Response> responses) {
        try{
            int right = 0;
            for (Response r : responses) {
                if (r.getResponse().equals(questionRepo.findById(r.getId()).get().getRightAnswer())) {
                    right++;
                }
            }
            return new ResponseEntity<>("Your Result is " + right, HttpStatus.OK);
        }
     catch(Exception e){
        return new ResponseEntity<>("Getting Error in loading your Result because " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

}
