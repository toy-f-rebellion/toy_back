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

    @GetMapping("/")
    public String getDiary(@AuthenticationPrincipal String userEmail) {
        return "로그인된 사용자는 " + userEmail + " 입니다. ";
    }

}
