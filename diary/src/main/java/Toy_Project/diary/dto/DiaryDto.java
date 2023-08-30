package Toy_Project.diary.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {
    // 필수로 값이 오도록 지정
    @NotBlank
    private String diaryDetail;
    @Column(name = "add_time", unique = true)
    private LocalDate addDate;

}
