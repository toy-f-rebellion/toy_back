package Toy_Project.diary.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    // JWT 생성 및 검증을 위한 키
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // JWT 생성하는 메서드
    public String create (String userEmail) {
        // 만료날짜를 현재 날짜 + 1시간으로 설정
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // JWT를 생성
        return Jwts.builder()
                // 암호화에 사용될 알고리즘, 키
                .signWith(key)
                // JWT 제목, 생성일, 만료일 주입
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(exprTime)
                // 생성
                .compact();
    }

    // JWT 검증
    public String validate(String token) {
        // 매개변수로 받은 token을 키를 사용해서 복호화 (디코딩)
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 복호화된 토큰의 payload에서 제목을 가져옴
        return claims.getSubject();  // 복호화된 정보가 반환됨
    }
}
