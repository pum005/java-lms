package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.util.List;
import java.util.Objects;

public class QuestionContents {
    private final String title;
    private final Comments comments;
    private final Answers answers;

    public QuestionContents(String title, Comments comments, Answers answers) {
        this.title = title;
        this.comments = comments;
        this.answers = answers;
    }

    public NsUser getWriter() {
        return this.comments.getWriter();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionContents)) return false;
        QuestionContents that = (QuestionContents) o;
        return Objects.equals(title, that.title) && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, comments);
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public List<Answer> getAnswers() {
        return this.answers.getAnswers();
    }

    public void throwExceptionIfAnswerIsOwner(NsUser loginUser) {
        this.answers.throwExceptionIfOwner(loginUser);
    }

    public void deleteAnswer() {
        this.answers.deleteAnswer();
    }
}
