package Toy_Project.diary.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "diary")
@Getter
@Setter
@ToString
public class Diary{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nickname", referencedColumnName = "nickname")
    private User user;

    @Id
    @Column(name = "diary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long diaryId;

    @Column(name = "diary_name", unique = true)
    private String diaryName;

    @Column(name = "diary_detail")
    private String diaryDetail;

    @Column(name = "emotion")
    private String emotion;

    @Column(name = "add_time", unique = true)
    private LocalDate addDate;

//    public Map<String, Object> toMap() {
//        Map<String, Object> diaryMap = new HashMap<>();
//        diaryMap.put("user", user);
//        diaryMap.put("diary_name", diaryName);
//        diaryMap.put("diary_detail", diaryDetail);
//        diaryMap.put("emotion", emotion);
//        diaryMap.put("addDate", addDate);
//        return diaryMap;
//    }

    public static Diary createDiary(User user){
        Diary diary = new Diary();
        diary.setUser(user);
        diary.setDiaryName(diary.getDiaryName());
        diary.setDiaryDetail(diary.getDiaryDetail());
        diary.setEmotion(diary.getEmotion());
        diary.setAddDate(diary.getAddDate());
        return diary;
    }
}

