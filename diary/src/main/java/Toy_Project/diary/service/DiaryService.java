package Toy_Project.diary.service;

import Toy_Project.diary.Entity.Diary;
import Toy_Project.diary.Entity.User;
import Toy_Project.diary.Repository.DiaryRepository;
import Toy_Project.diary.Repository.UserRepository;
import Toy_Project.diary.dto.DiaryDto;
import Toy_Project.diary.dto.DiaryResponseDto;
import Toy_Project.diary.dto.ResponseDto;
import Toy_Project.diary.dto.SignInResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    @Autowired DiaryRepository diaryRepository;
    @Autowired UserRepository userRepository;

    // 일기 작성
    public ResponseDto<?> createDiary(DiaryDto dto, String userEmail) {

        String userDiaryDetail = dto.getDiaryDetail();
        LocalDate userAddDate = dto.getAddDate();
        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseDto.setFailed("user not existed");
            }
            if (diaryRepository.existsByAddDateAndUser(userAddDate, user)) {
                return ResponseDto.setFailed("diary already exists");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        // email + 생상 날짜로 이뤄진 일기 이름 생성
        String userDiaryName = user.getEmail() + ' ' + userAddDate;

        // 감정 판단 과정 - userDiaryDetail을 이용하여 요청을 생성하고 특정 API에 보내서 감정 값을 받아옴
        String userEmotion = "기쁨";
        // Webclient

        // DiaryEntity 생성
        Diary userDiary = new Diary();
        userDiary.setUser(user);
        userDiary.setDiaryName(userDiaryName);
        userDiary.setDiaryDetail(userDiaryDetail);
        userDiary.setAddDate(userAddDate);
        userDiary.setEmotion(userEmotion);

        try {
            // userDiary를 이용해서 데이터베이스에 Entity 저장
            diaryRepository.save(userDiary);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        // 성공시 success response 반환
        return ResponseDto.setSuccess("Complete Create Diary", userEmotion);
    }

    // 날짜로 일기 검색
    public ResponseDto<DiaryResponseDto> getDiaryByDate(LocalDate addDate, String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseDto.setFailed("user not existed");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        Diary diary = diaryRepository.findByAddDateAndUser(addDate, user);

        try {
            if (diary == null) {
                return ResponseDto.setFailed("user didn't write diary");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        DiaryResponseDto diaryResponseDto = new DiaryResponseDto(diary.getDiaryDetail(), diary.getAddDate(), diary.getEmotion());

        return ResponseDto.setSuccess("Find Diary", diaryResponseDto);

    }


    // 일기 수정
    public ResponseDto<?> editDiary(DiaryDto dto, String userEmail) {

        String userDiaryDetail = dto.getDiaryDetail();
        LocalDate userAddDate = dto.getAddDate();
        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseDto.setFailed("user not existed");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        // email + 생상 날짜로 이뤄진 일기 이름 생성
        String userDiaryName = user.getEmail() + ' ' + userAddDate;

        // 감정 판단 과정 - userDiaryDetail을 이용하여 요청을 생성하고 특정 API에 보내서 감정 값을 받아옴
        String userEmotion = "기쁨";
        // Webclient

        // DiaryEntity 생성
        Diary userDiary = diaryRepository.findByAddDateAndUser(userAddDate, user);
        userDiary.setDiaryDetail(userDiaryDetail);
        userDiary.setEmotion(userEmotion);

        try {
            // userDiary를 이용해서 데이터베이스에 Entity 저장
            // JPA에서는 기존 데이터를 수정할 때도 save() 메소드를 사용
            // 이유는 save() 메소드가 새로운 객체일 경우에는 저장을 하고, 이미 데이터베이스에 존재하는 객체일 경우에는 업데이트를 수행하기 때문
            diaryRepository.save(userDiary);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        // 성공시 success response 반환
        return ResponseDto.setSuccess("edit Successful", userEmotion);
    }

    public ResponseDto<?> deleteDiaryByDate(LocalDate addDate, String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseDto.setFailed("user not existed");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        Diary diary = diaryRepository.findByAddDateAndUser(addDate, user);

        try {
            if (diary == null) {
                return ResponseDto.setFailed("user didn't write diary");
            }
            diaryRepository.delete(diary);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        return ResponseDto.setSuccess("Delete Successful", addDate);

    }

}
