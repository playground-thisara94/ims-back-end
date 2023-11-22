package lk.ijse.dep11.ims.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lk.ijse.dep11.ims.to.CourseTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin
public class CourseHttpController {

    private final HikariDataSource pool;

    public CourseHttpController(){
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setPassword("sql23");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/dep11_ims");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("maximumPoolSize", 10);
        pool = new HikariDataSource(config);
    }

    @PreDestroy
    public void destroy(){
        pool.close();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public CourseTO createCourse(@Validated @RequestBody CourseTO course){
        try (Connection connection = pool.getConnection()){
            PreparedStatement stm = connection.prepareStatement("INSERT INTO course (name, duration_in_months) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, course.getName());
            stm.setInt(2, course.getDurationInMonths());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            course.setId(id);
            return course;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{id}", consumes = "application/json")
    public void updateCourse(@PathVariable int id,
                             @RequestBody @Validated CourseTO course){
        try (Connection connection = pool.getConnection()){
            PreparedStatement stmExist = connection.prepareStatement("SELECT * FROM course WHERE id = ?");
            stmExist.setInt(1, id);
            if(!stmExist.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
            }

            PreparedStatement stm = connection.prepareStatement("UPDATE course SET name = ?, duration_in_months = ? WHERE id = ?");
            stm.setString(1, course.getName());
            stm.setInt(2, course.getDurationInMonths());
            stm.setInt(3, id);
            stm.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id){
        try (Connection connection = pool.getConnection()){
            PreparedStatement stmExist = connection.prepareStatement("SELECT * FROM course WHERE id = ?");
            stmExist.setInt(1, id);
            if(!stmExist.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
            }

            PreparedStatement stm = connection.prepareStatement("DELETE FROM course WHERE id = ?");
            stm.setInt(1, id);
            stm.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public CourseTO getCourseDetails(@PathVariable int id){
        System.out.println("getCourseDetails()");
        return null;
    }

    @GetMapping(produces = "application/json")
    public List<CourseTO> getAllCourses(){
        System.out.println("getAllCourses()");
        return null;
    }



}
