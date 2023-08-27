//package Toy_Project.diary.Controller;
//
//import Toy_Project.diary.Entity.User;
//import Toy_Project.diary.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/login")
//public class login{
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/check")
//    public ResponseEntity<String> login_check(@RequestBody User newUser) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            // 사용자 ID 확인
//            Optional<User> existingUserByUserId = userRepository.findByUserId(newUser.getUserId());
//            if (existingUserByUserId.isPresent()) { // ID가 존재 할 경우
//                User user = existingUserByUserId.get();
//                if(user.getPassword().equals(newUser.getPassword())){ // 비밀번호가 일치하는 경우
//                    // 사용자 정보를 JSON 형태로 반환
//                    String jsonUser = objectMapper.writeValueAsString(user);
//                    return new ResponseEntity<>(jsonUser, HttpStatus.OK);
//                }
//                else {
//                    return new ResponseEntity<>("비밀번호가 일치하지 않습니다.",  HttpStatus.UNAUTHORIZED);
//                }
//            } else {
//
//                // 결과 반환: HTTP 상태 코드 201 (생성됨) 및 메시지 "OK"
//                return new ResponseEntity<>("존재하지 않는 ID입니다.", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) { // 예외 발생 시
//            // 결과 반환: HTTP 상태 코드 500 (내부 서버 오류) 및 오류 메시지
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
