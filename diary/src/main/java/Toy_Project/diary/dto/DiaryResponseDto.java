package Toy_Project.diary.dto;

import Toy_Project.diary.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryResponseDto {
    private String DiaryDetail;
    private LocalDate addDate;
    private String Emotion;
}
