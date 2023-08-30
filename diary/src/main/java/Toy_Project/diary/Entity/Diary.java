package Toy_Project.diary.Entity;
import Toy_Project.diary.dto.DiaryDto;
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
    @JoinColumn(name = "email", referencedColumnName = "email")
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

