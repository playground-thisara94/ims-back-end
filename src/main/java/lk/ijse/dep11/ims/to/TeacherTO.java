package lk.ijse.dep11.ims.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherTO implements Serializable {
    @Null(message = "Id should be null")
    private Integer id;
    @NotBlank(message = "Name can not be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid Name")
    private String name;
    @NotBlank(message = "Contact can not be blank")
    @Pattern(regexp = "^\\d{3}-\\d{7}$", message = "Invalid Contact")
    private String contact;
}
