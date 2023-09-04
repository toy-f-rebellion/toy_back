package Toy_Project.diary.Controller;

import Toy_Project.diary.Entity.Diary;
import Toy_Project.diary.Entity.User;
import Toy_Project.diary.Repository.DiaryRepository;
import Toy_Project.diary.dto.DiaryDto;
import Toy_Project.diary.dto.DiaryResponseDto;
import Toy_Project.diary.dto.ResponseDto;
import Toy_Project.diary.dto.SignUpDto;
import Toy_Project.diary.service.DiaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    DiaryService diaryService;

    @GetMapping("/")
    public String getDiary(@AuthenticationPrincipal String userEmail) {
        return "로그인된 사용자는 " + userEmail + " 입니다. ";
    }

    @PostMapping("/create")
    public ResponseDto<?> createDiary(@RequestBody DiaryDto requestBody, @AuthenticationPrincipal String userEmail) {
        ResponseDto<?> result = diaryService.createDiary(requestBody, userEmail);
        return result;
    }

    @GetMapping("/view")
    public ResponseDto<DiaryResponseDto> viewDiary(@RequestParam LocalDate addDate, @AuthenticationPrincipal String userEmail) {
        ResponseDto<DiaryResponseDto> result = diaryService.getDiaryByDate(addDate, userEmail);
        return result;
    }

    @PutMapping("/edit")
    public ResponseDto<?> editDiary(@RequestBody DiaryDto requestBody, @AuthenticationPrincipal String userEmail) {
        ResponseDto<?> result = diaryService.editDiary(requestBody, userEmail);
        return result;
    }

    @DeleteMapping("/delete")
    public ResponseDto<?> deleteDiary(@RequestParam LocalDate addDate, @AuthenticationPrincipal String userEmail) {
        ResponseDto<?> result = diaryService.deleteDiaryByDate(addDate, userEmail);
        return result;
    }


}
