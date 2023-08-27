//package Toy_Project.diary.service;
//
//import Toy_Project.diary.Entity.Diary;
//import Toy_Project.diary.Entity.User;
//import Toy_Project.diary.Repository.DiaryRepository;
//import Toy_Project.diary.Repository.UserRepository;
//import Toy_Project.diary.dto.DiaryDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class DiaryService {
//    private final UserRepository userRepository;
//    private final DiaryRepository diaryRepository;
//
//    public Long diary(DiaryDto diaryDto, String nickname){
//        Optional<User> user = userRepository.findByNickname(nickname);
//        Diary diary = diary.createDiary(user, DiaryDto)
//    }
//}
