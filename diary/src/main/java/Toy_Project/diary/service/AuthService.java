package Toy_Project.diary.service;

import Toy_Project.diary.Entity.User;
import Toy_Project.diary.Repository.UserRepository;
import Toy_Project.diary.dto.ResponseDto;
import Toy_Project.diary.dto.SignInDto;
import Toy_Project.diary.dto.SignInResponseDto;
import Toy_Project.diary.dto.SignUpDto;
import Toy_Project.diary.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;


    public ResponseDto<?> signUp(SignUpDto dto) {

        String userEmail = dto.getEmail();

        try {
            // email 중복 확인
            if (userRepository.existsByEmail(userEmail))
                return ResponseDto.setFailed("Existed Email");

        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");

        }

        // UserEntity 생성
        User userEntity = new User(dto);

        try {
            // UserRepository를 이용해서 데이터베이스에 Entity 저장
            userRepository.save(userEntity);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        // 성공시 success response 반환
        return ResponseDto.setSuccess("SignUp success!", null);
    }

    public ResponseDto<SignInResponseDto> signIn(SignInDto dto) {
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();

        try {
            boolean existed = userRepository.existsByEmailAndPassword(userEmail, userPassword);
            if (!existed) return ResponseDto.setFailed("Sign In Information Does Not Match");
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        User user = null;
        try {
            user = userRepository.findByEmail(userEmail);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        user.setPassword("");

        String token = tokenProvider.create(userEmail);
        int exprTime = 3600000;

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, user);
        return ResponseDto.setSuccess("Sign In Success", signInResponseDto);
    }
}