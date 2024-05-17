package com.projects.QuizApp.service;

import com.projects.QuizApp.model.Question;
import com.projects.QuizApp.dao.QuestionDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    final
    QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getAllQuestionByCategory(String category) {
        return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question){
        questionDao.save(question) ;
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(Question question){
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> deleteQuestion(Question question){
        questionDao.delete(question);
        return new ResponseEntity<>("success",HttpStatus.NO_CONTENT);
    }
}
