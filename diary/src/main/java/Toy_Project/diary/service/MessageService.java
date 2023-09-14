package Toy_Project.diary.service;

import Toy_Project.diary.Entity.Diary;
import Toy_Project.diary.Entity.Message;
import Toy_Project.diary.Entity.User;
import Toy_Project.diary.Repository.DiaryRepository;
import Toy_Project.diary.Repository.MessageRepository;
import Toy_Project.diary.Repository.UserRepository;
import Toy_Project.diary.dto.MessageDto;
import Toy_Project.diary.dto.MessageResponseDto;
import Toy_Project.diary.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    UserRepository userRepository;

    // 메시지 저장
    public ResponseDto<?> saveMessage(MessageDto dto, String userEmail) {

        String userDiaryName = dto.getDiaryName();
        Map<String,String> userConversation = dto.getConversation();
        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseDto.setFailed("user not existed");
            }
            if (!diaryRepository.existsByDiaryName(userDiaryName)) {
                return ResponseDto.setFailed("diary not existed");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        Diary userDiary = diaryRepository.findByDiaryName(userDiaryName);

        Message userMessage = new Message();
        userMessage.setConversation(userConversation);
        userMessage.setDiary(userDiary);
        System.out.println(userMessage.getConversation());

        try {
            // userMessage를 이용해서 데이터베이스에 Entity 저장
            messageRepository.save(userMessage);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        // 성공시 success response 반환
        return ResponseDto.setSuccess("Message saved complete", userMessage.getConversation());

    }

    // 메시지 날짜로 조회
    public ResponseDto<MessageResponseDto> getMessageByDate(LocalDate addDate, String userEmail) {
        String userDiaryName = userEmail + ' ' + addDate;
        Diary userDiary = diaryRepository.findByDiaryName(userDiaryName);

        try {
            if (userDiary == null) {
                return ResponseDto.setFailed("user didn't write diary");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        List<Message> userMessage = messageRepository.findAllByDiary(userDiary);
        // conversation만 추출하여 저장할 리스트 생성
        List<Map<String,String>> conversations = new ArrayList<>();
        for (Message message : userMessage) {
            Map<String,String> conversation = message.getConversation();
            conversations.add(conversation);
        }

        try {
            if (userMessage == null) {
                return ResponseDto.setFailed("Message not existed");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        MessageResponseDto messageResponseDto = new MessageResponseDto(userDiaryName, conversations);

        // 성공시 success response 반환
        return ResponseDto.setSuccess("Message Find Complete", messageResponseDto);
    }

    // 메시지 추가(업데이트) -> 메시지 한 줄 한 줄 저장하면 따로 업데이트 코드가 필요하지 않을 것 같음.

    // 메시지 삭제
    public ResponseDto<?> deleteMessageByDate(LocalDate addDate, String userEmail) {
        String userDiaryName = userEmail + ' ' + addDate;
        Diary userDiary = diaryRepository.findByDiaryName(userDiaryName);

        try {
            if (userDiary == null) {
                return ResponseDto.setFailed("user didn't write diary");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        List<Message> userMessage = messageRepository.findAllByDiary(userDiary);

        try {
            if (userMessage == null) {
                return ResponseDto.setFailed("Message not existed");
            }
            messageRepository.deleteAllByDiary(userDiary);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        return ResponseDto.setSuccess("Message Deletion Completed", addDate);

    }


}
