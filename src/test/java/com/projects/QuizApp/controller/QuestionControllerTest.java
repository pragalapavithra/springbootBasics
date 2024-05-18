package com.projects.QuizApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.QuizApp.model.Question;
import com.projects.QuizApp.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Test
    void getAllQuestions_success() throws Exception {
        List<Question> questions =
                Arrays.asList(
                        new Question(1, "Question 1", "option1", "option 2", "option 3", "java", "option 1"),
                        new Question(2, "Question 2", "option1", "option 2", "option 3", "java", "option 1")

                );

        Mockito.when(questionService.getAllQuestions()).thenReturn(new ResponseEntity<>(questions, HttpStatus.OK));

        mockMvc.perform(get("/question/allQuestions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getAllQuestions_failure() throws Exception {

        Mockito.when(questionService.getAllQuestions()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/question/allQuestions"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getQuestionsByCategory_success() throws Exception {
        List<Question> questions =
                Arrays.asList(
                        new Question(1, "Question 1", "option1", "option 2", "option 3", "option 1", "python"),
                        new Question(2, "Question 2", "option1", "option 2", "option 3", "option 2", "java")

                );
        List<Question> filteredList = new ArrayList<>();
        filteredList.add(questions.get(1));

        Mockito.when(questionService.getAllQuestionByCategory("java")).thenReturn(new ResponseEntity<>(filteredList, HttpStatus.OK));

        mockMvc.perform(get("/question/category/java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getQuestionsByCategory_failure() throws Exception {

        Mockito.when(questionService.getAllQuestionByCategory("java")).thenThrow(new RuntimeException());

        mockMvc.perform(get("/question/category/java"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addQuestion_success() throws Exception {
        Question question = new Question(2, "Question 2", "option1", "option 2", "option 3", "option 2", "java");

        Mockito.when(questionService.addQuestion(Mockito.any(Question.class))).thenReturn(new ResponseEntity<>("success",HttpStatus.CREATED));

        mockMvc.perform(post("/question/addQuestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(question)))
                .andExpect(status().isCreated())
                .andExpect(content().string("success"));
    }

    @Test
    void addQuestion_failure() throws Exception {
        Question question = new Question(2, "Question 2", "option1", "option 2", "option 3", "option 2", "java");

        Mockito.when(questionService.addQuestion(Mockito.any(Question.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/question/addQuestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(question)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("failure"));
    }

    @Test
    void updateQuestion_success() throws Exception {
        Question question = new Question(2, "Question 2", "option1", "option 2", "option 3", "option 2", "java");
        question.setQuestion("new question");
        question.setAnswer("new answer");
        Mockito.when(questionService.updateQuestion(Mockito.any(Question.class))).thenReturn(new ResponseEntity<>("success",HttpStatus.NO_CONTENT));

        mockMvc.perform(put("/question/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(question)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("success"));
    }

    @Test
    void deleteQuestion_success() throws Exception {
        Question question = new Question(2, "Question 2", "option1", "option 2", "option 3", "option 2", "java");

        Mockito.when(questionService.deleteQuestion(Mockito.any(Question.class))).thenReturn(new ResponseEntity<>("success",HttpStatus.NO_CONTENT));

        mockMvc.perform(delete("/question/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(question)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("success"));
    }
}
