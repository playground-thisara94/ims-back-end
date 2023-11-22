package lk.ijse.dep11.ims.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseTO implements Serializable {

    @Null(message = "Id should be empty")
    private Integer id;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid name")
    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotNull(message = "Duration should not be empty")
    private Integer durationInMonths;
}
