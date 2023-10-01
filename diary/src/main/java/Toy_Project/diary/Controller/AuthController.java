package Toy_Project.diary.Controller;

import Toy_Project.diary.dto.*;
import Toy_Project.diary.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

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

    // 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseDto<?> deleteUser(@AuthenticationPrincipal String userEmail) {
        ResponseDto<?> result = authService.deleteUser(userEmail);
        return result;
    }

    // 프로필 사진 등록 및 수정
    @PostMapping("/setPhoto")
    public ResponseDto<?> setProfilePhoto(@RequestBody MultipartFile file, @AuthenticationPrincipal String userEmail) {
        ResponseDto<?> result = authService.setProfilePhoto(file, userEmail);
        return result;
    }

    // 닉네임 등록 및 수정
    @PostMapping("/setNickname")
    public ResponseDto<?> setNickName(@RequestBody String nickname, @AuthenticationPrincipal String userEmail) {
        ResponseDto<?> result = authService.setNickname(nickname, userEmail);
        return result;
    }

    // 프로필 보기
    @GetMapping("/viewProfile")
    public ResponseDto<ProfileResponseDto> getProfilePhoto(@AuthenticationPrincipal String userEmail) {
        ResponseDto<ProfileResponseDto> result = authService.getProfilePhoto(userEmail);
        return result;
    }

    // 이메일 중복 체크
    @PostMapping("/emailCheck")
    public ResponseDto<?> emailCheck(@RequestBody ExistsCheckDto requestBody) {
        ResponseDto<?> result = authService.emailCheck(requestBody);
        return result;
    }

    // 닉네임 중복 체크
    @PostMapping("/nicknameCheck")
    public ResponseDto<?> nicknameCheck(@RequestBody ExistsCheckDto requestBody) {
        ResponseDto<?> result = authService.nicknameCheck(requestBody);
        return result;
    }

}
