package Toy_Project.diary.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private String email;

    private String name;

    private String password;

    private String nickname;

    private String phoneNum;

    private String gender;

    private int age;

    private boolean agreement;
}
