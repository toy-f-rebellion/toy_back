package Toy_Project.diary.Entity;
import Toy_Project.diary.Entity.Diary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Message")
@Table(name = "message")
public class Message{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_name", referencedColumnName = "diary_name")
    private Diary diary;


    @Convert(converter = ConversationConverter.class) // @Convert 어노테이션을 사용하여 컨버터를 지정
    @Column(name = "conversation", columnDefinition = "TEXT")
    private Map<String,String> conversation;

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long message_id;

}

