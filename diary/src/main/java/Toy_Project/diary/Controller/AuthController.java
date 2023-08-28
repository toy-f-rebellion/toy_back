package Toy_Project.diary.Controller;

import Toy_Project.diary.dto.ResponseDto;
import Toy_Project.diary.dto.SignInDto;
import Toy_Project.diary.dto.SignInResponseDto;
import Toy_Project.diary.dto.SignUpDto;
import Toy_Project.diary.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    // 회원가입
    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

    // 로그인
    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody) {
        ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
        return result;
    }

    // 이메일 중복 체크
    @PostMapping("/emailCheck")
    public ResponseDto<?> emailCheck(@RequestBody String email) {
        ResponseDto<?> result = authService.emailCheck(email);
        return result;
    }

    // 닉네임 중복 체크
    @PostMapping("/nicknameCheck")
    public ResponseDto<?> nicknameCheck(@RequestBody String nickname) {
        ResponseDto<?> result = authService.nicknameCheck(nickname);
        return result;
    }
}
