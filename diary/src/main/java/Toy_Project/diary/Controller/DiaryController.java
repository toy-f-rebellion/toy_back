package Toy_Project.diary.Controller;

import Toy_Project.diary.Entity.Diary;
import Toy_Project.diary.Entity.User;
import Toy_Project.diary.Repository.DiaryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/diary")
@Controller
@RequiredArgsConstructor
public class DiaryController {
    @Autowired
    private DiaryRepository diaryRepository;

    // 일기 작성
    @PostMapping("/save")
    public ResponseEntity<String> saveDiary(@RequestBody Diary newDiary) {
        try {
            // 해당 날짜 & 해당 닉네임으로 이미 쓰여진 일기가 있는지 확인
            Optional<Diary> existingDiaryByDate = diaryRepository.findByAddDateAndUser(newDiary.getAddDate(), newDiary.getUser());

            if (existingDiaryByDate.isPresent()) {
                // 기존 날짜에 유저의 일기가 이미 존재하는 경우, 작성 불가능 -> 수정 제안
                return new ResponseEntity<>("해당 날짜에 이미 작성된 일기가 있습니다.", HttpStatus.CONFLICT);
            } else {
                // 새로운 일기 저장
                diaryRepository.save(newDiary);
                // 결과 반환: HTTP 상태 코드 201 (생성됨) 및 메시지 "OK"
                return new ResponseEntity<>(newDiary.getAddDate() + " 날짜에 일기가 작성됐습니다.", HttpStatus.CREATED);
            }
        } catch (Exception e) { // 예외 발생 시
            // 결과 반환: HTTP 상태 코드 500 (내부 서버 오류) 및 오류 메시지
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 일기 조회
//    @GetMapping("/{addDate}")
//    public ResponseEntity<Map<String, Object>> viewDiaryByAddDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate addDate, Authentication authentication) {
//        User currentUser = (User) authentication.getPrincipal();
//
//        Optional<Diary> diaries = diaryRepository.findByAddDateAndUser(addDate, currentUser);
//
//        Map<String, Object> response = new HashMap<>();
//
//        if (diaries.isEmpty()) {
//            response.put("message", "No diary found for the given date and user.");
//        } else {
//            Diary diary = diaries.get(); // Assuming there's only one diary per user per date
//            response.put("diaryDetail", diary.getDiaryDetail());
//            response.put("emotion", diary.getEmotion());
//        }
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


    // 일기 수정


    // 일기 삭제


}
