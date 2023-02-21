package com.example.LikeBook.Repository;

import com.example.LikeBook.Model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserId(long id);

    List<Post> findPostsByUserIdNot(long id);
}
