package nextstep.qna.service;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.*;
import nextstep.qna.domain.DeleteHistory.ContentType;
import nextstep.qna.domain.DeleteHistory.DeleteHistory;
import nextstep.qna.domain.answer.Answer;
import nextstep.qna.domain.answer.Comments;
import nextstep.qna.domain.question.Question;
import nextstep.qna.domain.question.QuestionRepository;
import nextstep.qna.domain.question.QuestionTest;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class QnaServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @InjectMocks
    private QnAService qnAService;

    private Long questionId;
    private Long answerId;
    private Question question;
    private Answer answer;

    @BeforeEach
    public void setUp() throws Exception {
        questionId = 1L;
        answerId = 11L;
        question = new Question(questionId, NsUserTest.JAVAJIGI, "title1", "contents1");
        answer = new Answer(answerId, NsUserTest.JAVAJIGI, "Answers Contents1");
        question.addAnswer(answer);
    }

    @Test
    public void delete_성공() throws Exception {
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        assertThat(question).isEqualTo(new Question(questionId, NsUserTest.JAVAJIGI, "title1", "contents1", false));
        qnAService.deleteQuestion(NsUserTest.JAVAJIGI, questionId);

        assertThat(question).isEqualTo(new Question(questionId, NsUserTest.JAVAJIGI, "title1", "contents1", true));
        verifyDeleteHistories();
    }

    @Test
    public void delete_다른_사람이_쓴_글() throws Exception {
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> {
            qnAService.deleteQuestion(NsUserTest.SANJIGI, questionId);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void delete_성공_질문자_답변자_같음() throws Exception {
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        qnAService.deleteQuestion(NsUserTest.JAVAJIGI, questionId);

        assertThat(question).isEqualTo(new Question(questionId, NsUserTest.JAVAJIGI, "title1", "contents1", true));
        assertThat(answer).isEqualTo(new Answer(answerId, NsUserTest.JAVAJIGI, "Answers Contents1", true));
        verifyDeleteHistories();
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() throws Exception {
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> {
            qnAService.deleteQuestion(NsUserTest.SANJIGI, questionId);
        }).isInstanceOf(CannotDeleteException.class);
    }

    private void verifyDeleteHistories() {
        List<DeleteHistory> deleteHistories = Arrays.asList(
                new DeleteHistory(ContentType.QUESTION, questionId, NsUserTest.JAVAJIGI, LocalDateTime.now()),
                new DeleteHistory(ContentType.ANSWER, answerId, NsUserTest.JAVAJIGI, LocalDateTime.now()));
        verify(deleteHistoryService).saveAll(deleteHistories);
    }
}
