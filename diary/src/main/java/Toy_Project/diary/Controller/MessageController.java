package Toy_Project.diary.Controller;

import Toy_Project.diary.dto.MessageDto;
import Toy_Project.diary.dto.MessageResponseDto;
import Toy_Project.diary.dto.ResponseDto;
import Toy_Project.diary.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    // 메시지 저장
    @PostMapping("/save")
    public ResponseDto<?> saveMessage(@RequestBody MessageDto requestBody, @AuthenticationPrincipal String userEmail) {
        ResponseDto<?> result = messageService.saveMessage(requestBody, userEmail);
        return result;
    }

    // 메시지 불러오기
    @GetMapping("/view")
    public ResponseDto<MessageResponseDto> viewMessage(@RequestParam LocalDate addDate, @AuthenticationPrincipal String userEmail) {
        ResponseDto<MessageResponseDto> result = messageService.getMessageByDate(addDate, userEmail);
        return result;
    }

    // 메시시 수정
//    @PutMapping("/update")
//    public ResponseDto<MessageResponseDto> updateMessage(@RequestBody MessageDto requestBody, @AuthenticationPrincipal String userEmail) {
//        ResponseDto<MessageResponseDto> result = messageService.updateMessage(requestBody, userEmail);
//        return result;
//    }

    // 메시지 삭제
    @DeleteMapping("/delete")
    public ResponseDto<?> deleteMessage(@RequestParam LocalDate addDate, @AuthenticationPrincipal String userEmail) {
        ResponseDto<?> result = messageService.deleteMessageByDate(addDate, userEmail);
        return result;
    }
}

