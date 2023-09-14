package Toy_Project.diary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    @NotBlank
    String diaryName;
    @NotBlank
    Map<String,String> conversation;
}
