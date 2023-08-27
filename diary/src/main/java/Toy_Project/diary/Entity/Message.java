package Toy_Project.diary.Entity;
import Toy_Project.diary.Entity.Diary;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "message")
public class Message{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_name", referencedColumnName = "diary_name")
    private Diary diary;

    @Column(name = "conversation")
    private String conversation;

    @Id
    @Column(name = "message_id")
    private String message_id;

    @Column(name = "add_time")
    private Date add_time;

}

