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
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/signup")
//public class sign_up {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/save")
//    public ResponseEntity<String> saveUser(@RequestBody User newUser) {
//        try {
//            // 저장하려는 사용자의 이메일, user_id, nickname으로 기존 사용자 확인 (혹시모를 검수)
//            Optional<User> existingUserByEmail = userRepository.findByEmail(newUser.getEmail());
//            Optional<User> existingUserByUserId = userRepository.findByUserId(newUser.getUserId());
//            Optional<User> existingUserByNickname = userRepository.findByNickname(newUser.getNickname());
//
//            if (existingUserByEmail.isPresent() || existingUserByUserId.isPresent() || existingUserByNickname.isPresent()) {
//                // 기존 사용자가 이미 존재하는 경우, 가입 불가능
//                return new ResponseEntity<>("아이디, 이메일, 닉네임 중복확인을 다시 확인해주세요.", HttpStatus.CONFLICT);
//            } else {
//                // 새로운 사용자 저장
//                userRepository.save(newUser);
//
//                // 결과 반환: HTTP 상태 코드 201 (생성됨) 및 메시지 "OK"
//                return new ResponseEntity<>("가입이 완료되었습니다.", HttpStatus.CREATED);
//            }
//        } catch (Exception e) { // 예외 발생 시
//            // 결과 반환: HTTP 상태 코드 500 (내부 서버 오류) 및 오류 메시지
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/id_check")
//    public ResponseEntity<String> id_check(@RequestBody User newUser) {
//        try {
//            Optional<User> existingUserByUserId = userRepository.findByUserId(newUser.getUserId());
//            if (existingUserByUserId.isPresent()){
//                return new ResponseEntity<>("이미 있는 ID입니다. 다른 ID를 사용해 주세요.", HttpStatus.CONFLICT);
//            }
//            else{
//                return new ResponseEntity<>("사용 가능한 ID입니다.", HttpStatus.CREATED);
//            }
//        } catch(Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/mail_check")
//    public ResponseEntity<String> mail_check(@RequestBody User newUser) {
//        try {
//            Optional<User> existingUserByEmail = userRepository.findByEmail(newUser.getEmail());
//            if (existingUserByEmail.isPresent()){
//                return new ResponseEntity<>("이미 사용중인 이메일입니다. 다른 이메일를 사용해 주세요.", HttpStatus.CONFLICT);
//            }
//            else{
//                return new ResponseEntity<>("사용 가능한 이메일입니다.", HttpStatus.CREATED);
//            }
//        } catch(Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/nickname_check")
//    public ResponseEntity<String> nickname_check(@RequestBody User newUser) {
//        try {
//            Optional<User> existingUserByNickname = userRepository.findByUserId(newUser.getNickname());
//            if (existingUserByNickname.isPresent()){
//                return new ResponseEntity<>("이미 있는 닉네임입니다. 다른 닉네임를 사용해 주세요.", HttpStatus.CONFLICT);
//            }
//            else{
//                return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.CREATED);
//            }
//        } catch(Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//}
//
