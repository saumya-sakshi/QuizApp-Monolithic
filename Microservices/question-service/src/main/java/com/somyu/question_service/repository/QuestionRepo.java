package com.somyu.question_service.repository;

import com.somyu.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Integer> {
    List<Question> findAllByCategory(String category);

    Question findByQuestionTitle(String questionTitle);

    @Query(value = "SELECT q.id FROM question q Where q.category = :category ORDER BY RANDOM() LIMIT :noOfQuestion", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, Integer noOfQuestion);
}
