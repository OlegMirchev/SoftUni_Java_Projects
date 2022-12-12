package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.ImportPostsDto;
import softuni.exam.instagraphlite.models.dto.PostDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "posts.xml");

    private PostRepository postRepository;
    private UserRepository userRepository;
    private PictureRepository pictureRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PictureRepository pictureRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportPostsDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportPostsDto importPosts = (ImportPostsDto) unmarshaller.unmarshal(fileReader);

        return importPosts.getPosts().stream().map(this::validatePost).collect(Collectors.joining("\n"));
    }

    private String validatePost(PostDto postDto) {
        Set<ConstraintViolation<PostDto>> validateExceptions = this.validator.validate(postDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid Post";
        }

        Optional<Post> byPost = this.postRepository.findByCaption(postDto.getCaption());

        if (byPost.isPresent()) {
            return "Invalid Post";
        }

        Post post = this.modelMapper.map(postDto, Post.class);
        Optional<User> user = this.userRepository.findByUsername(postDto.getUser().getUsername());
        Optional<Picture> picture = this.pictureRepository.findByPath(postDto.getPicture().getPath());

        if (user.isEmpty() || picture.isEmpty()) {
            return "Invalid Post";
        }

        post.setUser(user.get());
        post.setPicture(picture.get());

        this.postRepository.save(post);

        return post.toString();
    }
}
