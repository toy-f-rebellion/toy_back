package Toy_Project.diary.service;

import Toy_Project.diary.Entity.Diary;
import Toy_Project.diary.Entity.User;
import Toy_Project.diary.Repository.UserRepository;
import Toy_Project.diary.dto.*;
import Toy_Project.diary.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public ResponseDto<?> signUp(SignUpDto dto) {

        String userEmail = dto.getEmail();
        String userPassword = dto.getPassword();
        String userPhoneNum = dto.getPhoneNum();

        try {
            // email 중복 확인
            if (userRepository.existsByEmail(userEmail))
                return ResponseDto.setFailed("Existed Email");
            // phoneNum 중복 확인
            if (userRepository.existsByPhoneNum(userPhoneNum)) {
                return ResponseDto.setFailed("Existed PhoneNum");
            }

        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");

        }

        // UserEntity 생성
        User userEntity = new User(dto);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userPassword);
        userEntity.setPassword(encodedPassword);

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

        User user = null;
        try {
            user = userRepository.findByEmail(userEmail);
            // 잘못된 이메일의 경우
            if (user == null) return ResponseDto.setFailed("Sign In Failed");
            // 잘못된 패스워드의 경우
            if (!passwordEncoder.matches(userPassword, user.getPassword()))
                return ResponseDto.setFailed("Sign In Failed");
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        user.setPassword("");

        String token = tokenProvider.create(userEmail);
        int exprTime = 3600000;

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, user);
        return ResponseDto.setSuccess("Sign In Success", signInResponseDto);
    }

    public ResponseDto<?> deleteUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseDto.setFailed("user not existed");
            }
            userRepository.delete(user);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        return ResponseDto.setSuccess("Successfully deleted user", userEmail);

    }

    public ResponseDto<?> emailCheck(ExistsCheckDto dto) {
        String checkEmail = dto.getCheck();
        // email 중복 확인
        if (userRepository.existsByEmail(checkEmail)) {
            return ResponseDto.setFailed("Existed Email");
        }
        // 성공시 success response 반환
        return ResponseDto.setSuccess("can use this email", null);
    }

    public ResponseDto<?> nicknameCheck(ExistsCheckDto dto) {
        String checkNickname = dto.getCheck();
        // nickname 중복 확인
        if (userRepository.existsByNickname(checkNickname)) {
            return ResponseDto.setFailed("Existed nickname");
        }
        // 성공시 success response 반환
        return ResponseDto.setSuccess("can use this nickname", null);
    }
}
