package com.projects.QuizApp.service;

import com.projects.QuizApp.dao.QuestionDao;
import com.projects.QuizApp.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionService questionService;

    private List<Question> questionList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionList =
                Arrays.asList(
                        new Question(1, "Question 1", "option1", "option 2", "option 3", "java", "option 1"),
                        new Question(2, "Question 2", "option1", "option 2", "option 3", "java", "option 1")

                );
    }

    @Test
    void getAllQuestions_success() {
        when(questionDao.findAll()).thenReturn(questionList);
        ResponseEntity<List<Question>> result = questionService.getAllQuestions();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(), questionList);
        verify(questionDao, times(1)).findAll();
    }

    @Test
    void getAllQuestionsByCategory_success() {
        String category = "java";
        List<Question> filteredQuestions = new ArrayList<>();
        filteredQuestions.add(questionList.get(0));
        when(questionDao.findByCategory(category)).thenReturn(filteredQuestions);
        ResponseEntity<List<Question>> result = questionService.getAllQuestionByCategory(category);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(), filteredQuestions);
        verify(questionDao, times(1)).findByCategory(category);
    }

    @Test
    void AddQuestion_success() {
        Question newQuestion =  new Question(5, "Question 1", "option1", "option 2", "option 3", "java", "option 1");
        when(questionDao.save(newQuestion)).thenReturn(newQuestion);
        ResponseEntity<String> result = questionService.addQuestion(newQuestion);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(result.getBody(), "success");
        verify(questionDao, times(1)).save(newQuestion);
    }

    @Test
    void deleteQuestion_success() {
        Question questionToBeDeleted = questionList.get(0);
        ResponseEntity<String> result = questionService.deleteQuestion(questionToBeDeleted);
        assertEquals(result.getStatusCode(), HttpStatus.NO_CONTENT);
        assertEquals(result.getBody(), "success");
        verify(questionDao, times(1)).delete(questionToBeDeleted);
    }

    @Test
    void updateQuestion_success() {
        Question questionToBeUpdated = questionList.get(0);
        questionToBeUpdated.setQuestion("updated question");
        ResponseEntity<String> result = questionService.updateQuestion(questionToBeUpdated);
        assertEquals(result.getStatusCode(), HttpStatus.NO_CONTENT);
        assertEquals(result.getBody(), "success");
        verify(questionDao, times(1)).save(questionToBeUpdated);

    }
}
