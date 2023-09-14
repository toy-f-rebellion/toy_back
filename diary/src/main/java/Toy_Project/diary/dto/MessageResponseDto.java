package Toy_Project.diary.dto;

import Toy_Project.diary.Entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
    private String diaryName;
    private List<Map<String,String>> conversation;
}
