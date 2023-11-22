package lk.ijse.dep11.ims.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lk.ijse.dep11.ims.to.TeacherTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/teachers")
public class TeacherHttpController {
    private final HikariDataSource pool;
    public TeacherHttpController() {
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setPassword("1234");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/dep11_ims");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("maximumPoolSize", 10);
        pool = new HikariDataSource(config);
    }

    @PreDestroy
    public void destroy() {
        pool.close();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public TeacherTO createTeacher(@RequestBody @Validated TeacherTO teacher) {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO teacher (name, contact) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, teacher.getName());
            stm.setString(2, teacher.getContact());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            teacher.setId(id);
            return teacher;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public TeacherTO updateTeacher(@PathVariable int id,
                              @RequestBody @Validated TeacherTO teacher) {
        System.out.println("Update Teacher");
        return null;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable int id) {
        System.out.println("Delete Teacher");
    }

    @GetMapping(value = "/{id}")
    public TeacherTO getTeacher(@PathVariable int id) {
        System.out.println("Get Teacher");
        return null;
    }

    @GetMapping
    public List<TeacherTO> getAllTeachers() {
        System.out.println("Get All Teachers");
        return null;
    }
}

