package com.projects.QuizApp.service;

import com.projects.QuizApp.model.Question;
import com.projects.QuizApp.dao.QuestionDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    final
    QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }
}
