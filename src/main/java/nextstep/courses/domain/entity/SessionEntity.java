package nextstep.courses.domain.entity;

import lombok.Getter;
import nextstep.courses.domain.session.Enrollment;
import nextstep.courses.domain.session.SessionDate;
import nextstep.courses.domain.session.SessionImage;
import nextstep.courses.domain.session.SessionState;
import nextstep.courses.domain.strategy.PaymentStrategy;

import java.time.LocalDate;

@Getter
public class SessionEntity {

    private final Long id;
    private final SessionImage sessionImage;
    private final SessionState sessionState;
    private final int price;
    private final Enrollment enrollment;
    private final SessionDate sessionDate;

    public SessionEntity(Long id, int size, String imageType, int width, int height, SessionState sessionState, int price, int capacity, LocalDate startDate, LocalDate endDate) {
        this(id, new SessionImage(size, imageType, width, height), sessionState, price, new Enrollment(capacity), new SessionDate(startDate, endDate));
    }

    public SessionEntity(Long id, SessionImage sessionImage, SessionState sessionState, int price, Enrollment enrollment, SessionDate sessionDate) {
        this.id = id;
        this.sessionImage = sessionImage;
        this.sessionState = sessionState;
        this.price = price;
        this.enrollment = enrollment;
        this.sessionDate = sessionDate;
    }


}
