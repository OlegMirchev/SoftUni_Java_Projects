package com.example.LikeBook.Service;

import com.example.LikeBook.Model.dto.AddPostsDto;
import com.example.LikeBook.Model.dto.PostOtherDto;
import com.example.LikeBook.Model.dto.PostUserDto;
import com.example.LikeBook.Model.entities.Mood;
import com.example.LikeBook.Model.entities.Post;
import com.example.LikeBook.Model.entities.User;
import com.example.LikeBook.Repository.MoodRepository;
import com.example.LikeBook.Repository.PostRepository;
import com.example.LikeBook.Repository.UserRepository;
import com.example.LikeBook.Utils.CurrentUser;
import com.example.LikeBook.Utils.Enum.MoodType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private MoodRepository moodRepository;
    private CurrentUser currentUser;

    private ModelMapper modelMapper;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, MoodRepository moodRepository, CurrentUser currentUser) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.moodRepository = moodRepository;
        this.currentUser = currentUser;

        this.modelMapper = new ModelMapper();
    }

    public void create(AddPostsDto addPostsDto) {
        Optional<User> user = this.userRepository.findById(this.currentUser.getId());
        Optional<Mood> mood = this.moodRepository.findByName(MoodType.valueOf(addPostsDto.getMood()));

        Post post = this.modelMapper.map(addPostsDto, Post.class);

        post.setUser(user.get());
        post.setMood(mood.get());

        this.postRepository.save(post);
    }

    public List<PostUserDto> selectUserByIdAndFindTheirAllPosts(long id) {
        return this.postRepository.findAllByUserId(id).stream()
                .map(p -> this.modelMapper.map(p, PostUserDto.class))
                .collect(Collectors.toList());
    }

    public List<PostOtherDto> selectOtherAndFindTheirAllPosts(long id) {
        return this.postRepository.findPostsByUserIdNot(id).stream().map(p -> this.modelMapper.map(p, PostOtherDto.class))
                .collect(Collectors.toList());
    }

    public void likePostsOnOtherUserWithId(long id, long userId) {
        Optional<Post> post = this.postRepository.findById(id);
        Optional<User> user = this.userRepository.findById(userId);

        post.get().getLikes().add(user.get());

        this.postRepository.save(post.get());
    }

    public void removePostWithId(long id) {
        this.postRepository.deleteById(id);
    }
}
