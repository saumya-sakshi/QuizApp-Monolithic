package com.somyu.quizapp.repository;

import com.somyu.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Integer> {
    List<Question> findAllByCategory(String category);

    Question findByQuestionTitle(String questionTitle);

    @Query(value = "SELECT * FROM question q Where q.category = :category ORDER BY RANDOM() LIMIT :noOfQuestion", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, Integer noOfQuestion);
}
