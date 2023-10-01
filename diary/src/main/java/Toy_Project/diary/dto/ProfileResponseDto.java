package Toy_Project.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {

    // 프로필 사진
    private String userNickname;  // 원본 파일 이름
    private String resourceUrl;  // URL 문자열로 파일 경로를 저장

}
