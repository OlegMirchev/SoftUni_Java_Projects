package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.dto.ExportUsersDto;
import softuni.exam.instagraphlite.models.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query("SELECT new softuni.exam.instagraphlite.models.dto.ExportUsersDto(u.username, COUNT(p) AS countOfPosts, p AS posts)" +
            " FROM User AS u" +
            " JOIN u.posts AS p" +
            " JOIN p.picture AS pic" +
            " GROUP BY u.id" +
            " ORDER BY countOfPosts DESC, u.id ASC, pic.size ASC")
    List<ExportUsersDto> findUsersAndTheirPosts();
}
