package nextstep.courses.domain.session;

import nextstep.courses.domain.entity.SessionEntity;

public interface SessionRepository {
    int save(SessionEntity course);

    SessionEntity findById(Long id);
}
