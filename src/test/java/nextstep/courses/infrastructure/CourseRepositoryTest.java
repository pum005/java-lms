package nextstep.courses.infrastructure;

import nextstep.courses.domain.course.Course;
import nextstep.courses.domain.course.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class CourseRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        courseRepository = new JdbcCourseRepository(jdbcTemplate);
    }

    @Test
    public void showTables() {
        String sql = "SHOW TABLES";
        List<Map<String, Object>> tables = jdbcTemplate.queryForList(sql);
        tables.forEach(row -> System.out.println(row));
    }

    @Test
    public void showAllTablesData() {
        // Step 1: Get the list of all tables
        String showTablesSql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'PUBLIC'";
        List<String> tableNames = jdbcTemplate.queryForList(showTablesSql, String.class);

        // Step 2: For each table, run SELECT * and print the results
        tableNames.forEach(tableName -> {
            System.out.println("Contents of table: " + tableName);

            String selectSql = "SELECT * FROM " + tableName;
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectSql);

            rows.forEach(row -> System.out.println(row));
            System.out.println("----------------------------------------------------");
        });
    }

    @Test
    void crud() {
        Course course = new Course("TDD, 클린 코드 with Java", 1L);
        int count = courseRepository.save(course);
        assertThat(count).isEqualTo(1);
        Course savedCourse = courseRepository.findById(1L);
        assertThat(course.getTitle()).isEqualTo(savedCourse.getTitle());
        LOGGER.debug("Course: {}", savedCourse);
    }
}
