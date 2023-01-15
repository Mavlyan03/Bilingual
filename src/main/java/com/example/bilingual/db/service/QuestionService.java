package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Question;
import com.example.bilingual.db.model.Test;
import com.example.bilingual.db.model.enums.ContentFormat;
import com.example.bilingual.db.model.enums.QuestionType;
import com.example.bilingual.db.repository.QuestionRepository;
import com.example.bilingual.db.repository.TestRepository;
import com.example.bilingual.dto.request.QuestionRequest;
import com.example.bilingual.dto.response.QuestionResponse;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.exception.BadRequestException;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    public SimpleResponse saveQuestion(QuestionRequest questionRequest) {
        Test test = testRepository.findById(questionRequest.getTestId()).orElseThrow(
                () -> new NotFoundException("Test not found"));

        if(questionRequest.getDuration().equals(0) || questionRequest.getDuration() == null) {
            throw new BadRequestException("The duration shouldn't be zero or null");
        }
        if(questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) &&
                questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null ||
                questionRequest.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS) &&
                        questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null ||
                questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE) &&
                        questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null ||
                questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA) &&
                        questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null) {
            throw new BadRequestException("You should at least one option!");
        }
        if(questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) &&
                questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT ||
                questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA) &&
                        questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT ||
                questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE) &&
                        questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT ||
                questionRequest.getQuestionType().equals(QuestionType.HIGHLIGHT_THE_ANSWER) &&
                        questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT ||
                questionRequest.getQuestionType().equals(QuestionType.RESPOND_IN_AT_LEAST_50_WORDS) &&
                        questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT) {
            throw new BadRequestException("Content format should be <TEXT>!");
        }
//        if(questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) &&
//                questionRequest.getContentRequest().getContentFormat().equals(ContentFormat.TEXT) &&
//                questionRequest.getOptions() != null) {
//            Question question = new Question(questionRequest, 1);
//            questionRepository.save(question);
//            return new SimpleResponse("Question saved successfully!");
//        }
//        if(questionRequest.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS) &&
//                questionRequest.getContentRequest().getContentFormat().equals(ContentFormat.AUDIO) &&
//                questionRequest.getOptions() != null) {
//            Question question = new Question(questionRequest, 2);
//            questionRepository.save(question);
//            return new SimpleResponse("Question saved successfully");
//        }

        return new SimpleResponse("Question save successfully");
    }

}
