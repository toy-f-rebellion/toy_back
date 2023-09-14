package Toy_Project.diary.Entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ConversationConverterTest {
    @Test
    public void testConvertToDatabaseColumn() {
        ConversationConverter converter = new ConversationConverter();

        Map<String, String> map1 = new HashMap<>();
        map1.put("role", "user");
        map1.put("content", "오늘은 정말 힘든 하루였어...");

        Map<String, String> map2 = new HashMap<>();
        map2.put("role", "assistant");
        map2.put("content", "그랬구나 어떤 일이 있었어? 이야기 해보자.");

        // 다른 대화 내용도 추가...

        try {
            String result = converter.convertToDatabaseColumn(map1);
            System.out.println("result: " + result);  // 결과 출력
            String result2 = converter.convertToDatabaseColumn(map2);
            System.out.println("result: " + result2);  // 결과 출력
        } catch (Exception e) {
            fail("Conversion failed with exception: " + e.getMessage());
        }
    }
}