package nextstep.courses.infrastructure;

import nextstep.courses.domain.course.Course;
import nextstep.courses.domain.entity.SessionEntity;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.SessionState;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JdbcSessionRepository implements SessionRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionEntity sessionEntity) {
        String sql = "insert into session (id, session_image_id, session_state, price, max_capacity, start_date, end_date) " +
                "values(?, ?, ?, ?, ? ,? ,?)";
        return jdbcTemplate.update(sql, sessionEntity.getId(), sessionEntity.getSessionImage().getId(),
                                    sessionEntity.getSessionState().name(), sessionEntity.getPrice(),
                                    sessionEntity.getEnrollment().getCapacity(),
                                    sessionEntity.getSessionDate().getStart(), sessionEntity.getSessionDate().getEnd());
    }

    @Override
    public SessionEntity findById(Long id) {
        String sql = "select s.id, si.image_size, si.type, si.width, si.height, s.session_state, s.price, s.max_capacity, s.start_date, s.end_date " +
                     "from session s join session_image si on s.id = si.id where s.id = ?";
        RowMapper<SessionEntity> rowMapper = (rs, rowNum) -> new SessionEntity(
                rs.getLong(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5),
                SessionState.valueOf(rs.getString(6)),
                rs.getInt(7),
                rs.getInt(8),
                toLocalDateTime(rs.getTimestamp(9)),
                toLocalDateTime(rs.getTimestamp(10)));

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private LocalDate toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().toLocalDate();
    }
}
