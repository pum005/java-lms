package nextstep.courses.infrastructure;

import nextstep.courses.domain.course.Course;
import nextstep.courses.domain.entity.SessionEntity;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.SessionState;
import nextstep.courses.domain.strategy.PaidPaymentStrategy;
import nextstep.courses.domain.strategy.PaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class JdbcSessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Order(1)
    @Test
    void save() {
        SessionEntity session = new SessionEntity(1L, 1000000, "jpg", 300, 200, SessionState.RECRUITING, 3000, 10, LocalDate.now(), LocalDate.now().plusDays(3));
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);
        SessionEntity savedSessionEntity = sessionRepository.findById(1L);
        assertThat(savedSessionEntity.getId()).isEqualTo(1L);
    }

    @Order(2)
    @Test
    void findById() {
        SessionEntity savedSessionEntity = sessionRepository.findById(1L);
        assertThat(savedSessionEntity.getId()).isEqualTo(1L);
    }
}
