package Toy_Project.diary.Repository;

import Toy_Project.diary.Entity.Diary;
import Toy_Project.diary.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, String> {

    Diary findByAddDateAndUser(LocalDate addDate, User user);
    boolean existsByAddDateAndUser(LocalDate addDate, User user);

    boolean existsByDiaryName(String diaryName);
    Diary findByDiaryName(String diaryName);
}
