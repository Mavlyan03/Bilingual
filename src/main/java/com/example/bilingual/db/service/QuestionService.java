package com.example.bilingual.db.service;

import com.example.bilingual.db.model.Option;
import com.example.bilingual.db.model.Question;
import com.example.bilingual.db.model.Test;
import com.example.bilingual.db.model.enums.ContentFormat;
import com.example.bilingual.db.model.enums.OptionType;
import com.example.bilingual.db.model.enums.QuestionType;
import com.example.bilingual.db.repository.OptionRepository;
import com.example.bilingual.db.repository.QuestionRepository;
import com.example.bilingual.db.repository.TestRepository;
import com.example.bilingual.dto.request.OptionRequest;
import com.example.bilingual.dto.request.QuestionRequest;
import com.example.bilingual.dto.request.UpdateQuestionRequest;
import com.example.bilingual.dto.response.OptionResponse;
import com.example.bilingual.dto.response.QuestionResponse;
import com.example.bilingual.dto.response.QuestionTestResponse;
import com.example.bilingual.dto.response.SimpleResponse;
import com.example.bilingual.exception.BadRequestException;
import com.example.bilingual.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
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
                            Question question = new Question(questionRequest, 1);
                            question.setTest(test);
                            test.getQuestions().add(question);
                            question.setOptionType(OptionType.MULTIPLE);
                            for (OptionRequest options : questionRequest.getOptions()) {
                                Option option = new Option(options);
                                question.getOptions().add(option);
                                option.setQuestion(question);
                            }
                            questionRepository.save(question);
                        } else if (questionRequest.getQuestionType().equals(QuestionType.LISTEN_AND_SELECT_ENGLISH_WORDS)) {
                            Question question = new Question(questionRequest, 2);
                            question.setTest(test);
                            test.getQuestions().add(question);
                            question.setOptionType(OptionType.MULTIPLE);
                            for (OptionRequest options : questionRequest.getOptions()) {
                                Option option = new Option(options);
                                question.getOptions().add(option);
                                option.setQuestion(question);
                            }
                            questionRepository.save(question);
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
                            Question question = new Question(questionRequest, 8);
                            question.setTest(test);
                            test.getQuestions().add(question);
                            question.setOptionType(OptionType.SINGLETON);
                            for (OptionRequest options : questionRequest.getOptions()) {
                                Option option = new Option(options);
                                question.getOptions().add(option);
                                option.setQuestion(question);
                            }
                            questionRepository.save(question);
                        } else if (questionRequest.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE)) {
                            Question question = new Question(questionRequest, 9);
                            question.setTest(test);
                            test.getQuestions().add(question);
                            question.setOptionType(OptionType.SINGLETON);
                            for (OptionRequest options : questionRequest.getOptions()) {
                                Option option = new Option(options);
                                question.getOptions().add(option);
                                option.setQuestion(question);
                            }
                            questionRepository.save(question);
                        }
                        return new SimpleResponse("Question save successfully");
                    } else {
                        throw new BadRequestException("You should add only one correct answer");
                    }
                } else if (questionRequest.getQuestionType().equals(QuestionType.TYPE_WHAT_YOU_HEAR)) {
                    if (questionRequest.getQuestionType().equals(QuestionType.TYPE_WHAT_YOU_HEAR) &&
                            questionRequest.getNumberOfReplays() <= 0 | questionRequest.getNumberOfReplays() == null) {
                        throw new BadRequestException("Number of Replays shouldn't be empty or zero");
                    } else if (questionRequest.getQuestionType().equals(QuestionType.TYPE_WHAT_YOU_HEAR) &&
                            questionRequest.getCorrectAnswer().isEmpty()) {
                        throw new BadRequestException("Correct answer shouldn't be empty");
                    } else {
                        Question question = new Question(questionRequest, 3);
                        question.setTest(test);
                        test.getQuestions().add(question);
                        questionRepository.save(question);
                    }
                } else if (questionRequest.getQuestionType().equals(QuestionType.DESCRIBE_IMAGE)) {
                    if (questionRequest.getCorrectAnswer().isEmpty() | questionRequest.getCorrectAnswer() == null) {
                        throw new BadRequestException("Correct answer shouldn't be empty");
                    } else {
                        Question question = new Question(questionRequest, 4);
                        question.setTest(test);
                        test.getQuestions().add(question);
                        questionRepository.save(question);
                    }
                } else if (questionRequest.getQuestionType().equals(QuestionType.RECORD_SAYING_STATEMENT)) {
                    if (questionRequest.getStatement().isEmpty() || questionRequest.getStatement() == null) {
                        throw new BadRequestException("Statement shouldn't be empty");
                    } else {
                        Question question = new Question(questionRequest, 5);
                        question.setTest(test);
                        test.getQuestions().add(question);
                        questionRepository.save(question);
                    }
                } else if (questionRequest.getQuestionType().equals(QuestionType.RESPOND_IN_AT_LEAST_N_WORDS)) {
                    if (questionRequest.getStatement().isEmpty() | questionRequest.getStatement() == null) {
                        throw new BadRequestException("Question statement shouldn't be empty");
                    } else if (questionRequest.getNumberOfWords() <= 0) {
                        throw new BadRequestException("Number of Words shouldn't be empty or zero");
                    } else {
                        Question question = new Question(questionRequest, 6);
                        question.setTest(test);
                        test.getQuestions().add(question);
                        questionRepository.save(question);
                    }
                } else if (questionRequest.getQuestionType().equals(QuestionType.HIGHLIGHT_THE_ANSWER)) {
                    if (questionRequest.getPassage().isEmpty() || questionRequest.getPassage() == null) {
                        throw new BadRequestException("Passage shouldn't be empty");
                    } else if (questionRequest.getCorrectAnswer().isEmpty() || questionRequest.getCorrectAnswer() == null) {
                        throw new BadRequestException("Highlight correct answer shouldn't be empty");
                    } else if (questionRequest.getStatement().isEmpty() || questionRequest.getContentRequest() == null) {
                        throw new BadRequestException("Question to the Passage shouldn't be empty");
                    } else {
                        Question question = new Question(questionRequest, 7);
                        question.setTest(test);
                        test.getQuestions().add(question);
                        questionRepository.save(question);
                    }
                }
            }
        }
        return new SimpleResponse("Question saved successfully");
    }

    public SimpleResponse deleteQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("Question with id %s not found", id)));
        question.setTest(null);
        questionRepository.delete(question);
        return new SimpleResponse("Question deleted successfully");
    }

    public QuestionResponse getById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("Question with id %s not found", id)));
        List<OptionResponse> options = optionRepository.getOptionsByQuestionId(question.getId());
        QuestionResponse questionResponse = new QuestionResponse(question);
        questionResponse.setOptionResponses(options);
        return questionResponse;
    }

    @Transactional
    public SimpleResponse enableDisable(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("Question with id %s not found", id)));
        question.setIsActive(!question.getIsActive());
        if (question.getIsActive()) {
            return new SimpleResponse("Question is enable");
        } else {
            return new SimpleResponse("Question is disable");
        }
    }


    public QuestionTestResponse updateQuestion(UpdateQuestionRequest question) {
        Question question1 = questionRepository.findById(question.getId()).orElseThrow(
                () -> new NotFoundException(
                        String.format("Question with id %s not found", question.getId())));
        List<OptionResponse> options = optionRepository.getOptionsByQuestionId(question1.getId());

        for (OptionRequest o : question.getOptionRequests()) {
            Option option = new Option(o);
            question1.getOptions().add(option);
        }

        for (OptionResponse o : options) {
            Long optionId = o.getId();

            for (Long id : question.getWillDelete()) {
                if (id.equals(optionId)) {
                    optionRepository.deleteById(id);
                }
            }
            for (Long id : question.getWillUpdate()) {
                if (question1.getQuestionType().equals(QuestionType.SELECT_THE_BEST_TITLE) ||
                        question1.getQuestionType().equals(QuestionType.SELECT_THE_MAIN_IDEA)) {
                    for (Option option : question1.getOptions()) {
                        if (option.getIsTrue().equals(true)) {
                            option.setIsTrue(false);
                        }
                    }
                    Option option = optionRepository.findById(id).orElseThrow(
                            () -> new NotFoundException("Option with id %s not found"));
                    option.setIsTrue(true);
                } else {
                    Option option = optionRepository.findById(o.getId()).orElseThrow(
                            () -> new NotFoundException("Option with id %s not found"));
                    option.setIsTrue(!option.getIsTrue());
                }
            }
        }

        questionRepository.updateQuestion(
                question1.getId(),
                question.getTitle(),
                question.getDuration());
        return new QuestionTestResponse(
                question1.getId(),
                question1.getTitle(),
                question1.getDuration(),
                question1.getQuestionType(),
                question1.getIsActive());
    }
}
