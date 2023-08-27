package Toy_Project.diary.Repository;

import Toy_Project.diary.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);

    public boolean existsByEmailAndPassword(String email, String password);

    public boolean existsByNickname(String nickname);

    public User findByEmail(String email);

}