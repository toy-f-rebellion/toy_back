package Toy_Project.diary.Repository;

import Toy_Project.diary.Entity.Diary;
import Toy_Project.diary.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findAllByDiary(Diary diary);

    boolean existsByDiary(Diary diary);

    void deleteAllByDiary(Diary diary);

}
