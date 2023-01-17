package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Option;
import com.example.bilingual.db.model.Question;
import com.example.bilingual.db.model.Test;
import com.example.bilingual.db.model.enums.ContentFormat;
import com.example.bilingual.db.model.enums.OptionType;
import com.example.bilingual.db.model.enums.QuestionType;
import com.example.bilingual.db.repository.QuestionRepository;
import com.example.bilingual.db.repository.TestRepository;
import com.example.bilingual.dto.request.OptionRequest;
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

        if (questionRequest.getDuration().equals(0) || questionRequest.getDuration() == null) {
            throw new BadRequestException("The duration shouldn't be zero or null");
        } else {
            if (questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) &&
                    questionRequest.getOptions().isEmpty() | questionRequest.getOptions() == null ||
                    questionRequest.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS) &&
                            questionRequest.getOptions().isEmpty() | questionRequest.getOptions() == null ||
                    questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE) &&
                            questionRequest.getOptions().isEmpty() | questionRequest.getOptions() == null ||
                    questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA) &&
                            questionRequest.getOptions().isEmpty() | questionRequest.getOptions() == null) {
                throw new BadRequestException("You should add at least one option!");
            } else if (questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) &&
                    questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT ||
                    questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA) &&
                            questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT ||
                    questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE) &&
                            questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT ||
                    questionRequest.getQuestionType().equals(QuestionType.HIGHLIGHT_THE_ANSWER) &&
                            questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT ||
                    questionRequest.getQuestionType().equals(QuestionType.RESPOND_IN_AT_LEAST_N_WORDS) &&
                            questionRequest.getContentRequest().getContentFormat() != ContentFormat.TEXT) {
                throw new BadRequestException("Content format should be <TEXT>!");
            } else if (questionRequest.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS) &&
                    questionRequest.getContentRequest().getContentFormat() != ContentFormat.AUDIO ||
                    questionRequest.getQuestionType().equals(QuestionType.TYPE_WHAT_YOU_HEAR) &&
                            questionRequest.getContentRequest().getContentFormat() != ContentFormat.AUDIO) {
                throw new BadRequestException("Content format should be <AUDIO>!");
            } else if (questionRequest.getQuestionType().equals(QuestionType.RECORD_SAYING_STATEMENT) &&
                    questionRequest.getContentRequest().getContentFormat() != ContentFormat.RECORD) {
                throw new BadRequestException("Content format should be <RECORD>!");
            } else if (questionRequest.getQuestionType().equals(QuestionType.DESCRIBE_IMAGE) &&
                    questionRequest.getContentRequest().getContentFormat() != ContentFormat.IMAGE) {
                throw new BadRequestException("Content format should be <IMAGE>!");
            } else {
                if (questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS) &&
                        questionRequest.getContentRequest().getContentFormat().equals(ContentFormat.TEXT) &&
                        questionRequest.getOptions() != null & !(questionRequest.getOptions().isEmpty()) ||
                        questionRequest.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS) &&
                                questionRequest.getContentRequest().getContentFormat().equals(ContentFormat.AUDIO) &&
                                questionRequest.getOptions() != null & !(questionRequest.getOptions().isEmpty())) {
                    int counter = 0;
                    for (OptionRequest option : questionRequest.getOptions()) {
                        if (option.getIsTrue().equals(true)) {
                            counter++;
                        }
                        if (option.getOption().isEmpty() || option.getOption() == null) {
                            throw new BadRequestException("The option shouldn't be empty!");
                        }
                    }
                    if (counter > 1) {
                        if (questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_REAL_ENGLISH_WORDS)) {
                            Question question = questionRepository.save(new Question(questionRequest, 1));
                            question.setTest(test);
                            test.getQuestions().add(question);
                            question.setOptionType(OptionType.MULTIPLE);
                        } else if (questionRequest.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS)) {
                            Question question = questionRepository.save(new Question(questionRequest, 2));
                            question.setTest(test);
                            test.getQuestions().add(question);
                            question.setOptionType(OptionType.MULTIPLE);
                        }
                        return new SimpleResponse("Question saved successfully!");
                    } else {
                        throw new BadRequestException("You should add more correct answers");
                    }
                } else if (questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA) ||
                        questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE) &&
                                questionRequest.getContentRequest().getContentFormat().equals(ContentFormat.TEXT) &&
                                questionRequest.getOptions() != null | !questionRequest.getOptions().isEmpty()) {
                    int counter = 0;
                    for (OptionRequest option : questionRequest.getOptions()) {
                        if (option.getIsTrue().equals(true)) {
                            counter++;
                        }
                    }
                    if (counter == 1) {
                        if (questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA)) {
                            Question question = questionRepository.save(new Question(questionRequest, 8));
                            question.setTest(test);
                            test.getQuestions().add(question);
                            question.setOptionType(OptionType.SINGLETON);
                        } else if (questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE)) {
                            Question question = questionRepository.save(new Question(questionRequest, 9));
                            question.setTest(test);
                            test.getQuestions().add(question);
                            question.setOptionType(OptionType.SINGLETON);
                        }
                        return new SimpleResponse("Question save successfully");
                    } else {
                        throw new BadRequestException("You should add only one correct answer");
                    }
                } else if (questionRequest.getQuestionType().equals(QuestionType.TYPE_WHAT_YOU_HEAR)) {
                    if (questionRequest.getQuestionType().equals(QuestionType.TYPE_WHAT_YOU_HEAR) &&
                            questionRequest.getNumberOfReplays() <= 0 | questionRequest.getNumberOfReplays() == null) {
                        throw new BadRequestException("You should fill in the field <Number of Replays>");
                    } else if (questionRequest.getQuestionType().equals(QuestionType.TYPE_WHAT_YOU_HEAR) &&
                            questionRequest.getCorrectAnswer().isEmpty()) {
                        throw new BadRequestException("You should fill the field <Correct Answer>");
                    } else {
                        Question question = new Question(questionRequest, 3);
                        question.setTest(test);
                        test.getQuestions().add(question);
                        questionRepository.save(question);
                    }
                }
            }
        }
        return new SimpleResponse("Question saved successfully");
    }

}
