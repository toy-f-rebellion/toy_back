package Toy_Project.diary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    // 필수로 값이 오도록 지정
    @NotBlank
    private String userEmail;
    @NotBlank
    private String userPassword;
}
