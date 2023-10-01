package Toy_Project.diary.service;

import Toy_Project.diary.Entity.Diary;
import Toy_Project.diary.Entity.User;
import Toy_Project.diary.Repository.UserRepository;
import Toy_Project.diary.dto.*;
import Toy_Project.diary.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    // 프로필 사진 설정
    public ResponseDto<?> setProfilePhoto(MultipartFile file, String userEmail) {

        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseDto.setFailed("user not existed");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        String originalFilename = file.getOriginalFilename();

        // 확장자 검사
        if (originalFilename != null && !originalFilename.isEmpty()) {
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

            if (!(extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg"))) {
                return ResponseDto.setFailed("Invalid file type. Only PNG, JPG and JPEG files are allowed.");
            }

        } else {
            return ResponseDto.setFailed("Invalid file name.");
        }

        // 파일 사이즈 검사(1MB)
        long fileSizeInBytes = file.getSize();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;

        if(fileSizeInMB > 1){
            return ResponseDto.setFailed("File size should be less than or equal to 1 MB.");
        }

        Path projectPath = Paths.get(System.getProperty("user.dir"), "diary", "src", "main", "resources", "static", "files");
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + originalFilename;
        File saveFile = new File(projectPath.toString(), fileName);

        File directory = new File(projectPath.toString());
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리가 존재하지 않으면 생성
        }

        // 이전 프로필 사진 파일 삭제
        if (user.getFilename() != null) {
            String oldFileName = user.getFilename();
            File oldFile = new File(projectPath.toString(), oldFileName);
            if (oldFile.exists()) {
                oldFile.delete(); // 이전 파일 삭제
            }
        }

        try {
            // userRepository에 profile photo 변경 사항 update
            user.setFilename(fileName);
            user.setFilepath("/files/" + fileName);
            userRepository.save(user);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        try {
            // static/files에 user profile 사진 저장
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseDto.setFailed("파일 저장 중 오류 발생");
        }

        return ResponseDto.setSuccess("Profile Photo saved successfully", null);
    }

    // 닉네임 등록 및 수정
    public ResponseDto<?> setNickname(String nickname, String userEmail) {

        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseDto.setFailed("user not existed");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        try {
            // userRepository에 닉네임 변경 사항 update
            user.setNickname(nickname);
            userRepository.save(user);
        } catch (Exception error) {
            return ResponseDto.setFailed("Data Base Error");
        }

        return ResponseDto.setSuccess("Nickname saved successfully", nickname);
    }

    // 닉네임 조회를 위한 프로필 조회
    public ResponseDto<ProfileResponseDto> getProfilePhoto(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseDto.setFailed("User not existed");
            }
        } catch (Exception error) {
            return ResponseDto.setFailed("Database Error");
        }

        if (user.getFilename() != null) {
            String filename = user.getFilename();
            Path file = Paths.get(System.getProperty("user.dir"), "diary", "src", "main", "resources", "static", "files", filename);
            Resource resource = null;

            try {
                resource = new UrlResource(file.toUri());
                if (resource.exists() && resource.isReadable()) {
                    // ProfileResponseDto 생성 및 필요한 정보 설정
                    ProfileResponseDto profileResponseDto = new ProfileResponseDto(user.getNickname(), resource.getURL().toString());

                    // 성공 응답 반환
                    return ResponseDto.setSuccess("Profile Photo retrieved successfully", profileResponseDto);
                } else {
                    return ResponseDto.setFailed("Resource not found or not readable");
                }
            } catch (IOException e) {
                return ResponseDto.setFailed("Bad Request Error");
            }
        }
        else {
            ProfileResponseDto profileResponseDto = new ProfileResponseDto(user.getNickname(), null);
            return ResponseDto.setSuccess("Profile 사진은 없음", profileResponseDto);
        }

    }


    // 프로필 사진만 조회
    public ResponseEntity<FileSystemResource> getPhoto(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        try {
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (user.getFilename() != null) {
            String filename = user.getFilename();
            Path file = Paths.get(System.getProperty("user.dir"), "diary", "src", "main", "resources", "static", "files", filename);
            Resource resource = null;

            // 이미지 파일을 바이트 배열로 읽기
            byte[] imageBytes;
            try {
                imageBytes = Files.readAllBytes(file);
            } catch (IOException e) {
                // 파일 읽기 실패 시 404 에러 반환
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // 이미지 파일을 응답에 포함시키기 위해 Resource로 변환
            FileSystemResource imageResource = new FileSystemResource(file.toFile());

            // HTTP 응답 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 파일 형식에 따라 MediaType 설정

            // 이미지 파일을 ResponseEntity로 감싸서 반환
            return new ResponseEntity<>(imageResource, headers, HttpStatus.OK);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(null, headers, HttpStatus.OK);
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
