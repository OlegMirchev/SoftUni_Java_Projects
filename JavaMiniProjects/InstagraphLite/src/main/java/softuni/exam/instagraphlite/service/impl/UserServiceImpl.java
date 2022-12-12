package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.ExportPostDto;
import softuni.exam.instagraphlite.models.dto.ExportUsersDto;
import softuni.exam.instagraphlite.models.dto.ImportUsersDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "users.json");

    private UserRepository userRepository;
    private PictureRepository pictureRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importUsers() throws IOException {
        String usersPath = readFromFileContent();

        ImportUsersDto[] importUsers = this.gson.fromJson(usersPath, ImportUsersDto[].class);

        return Arrays.stream(importUsers).map(this::validateUser).collect(Collectors.joining("\n"));
    }

    // TODO: This method is uncompleted -> reason: I'm not succeed to map list from Posts with ExportUsersDto
    @Override
    public String exportUsersWithTheirPosts() {
        List<ExportUsersDto> users = this.userRepository.findUsersAndTheirPosts();

        StringBuilder output = new StringBuilder();

        for (ExportUsersDto user : users) {
            output.append(String.format("""
                    User: %s
                    Post count: %d
                    """, user.getUsername(), user.getCountOfPosts()));

            for (ExportPostDto post : user.getPosts()) {
                output.append(String.format("""
                        ==Post Details:
                        ----Caption: %s
                        ----Picture Size: %.2f
                        """, post.getCaption(), post.getPictureSize()));
            }
        }

        return output.toString().trim();
    }

    private String validateUser(ImportUsersDto usersDto) {
        Set<ConstraintViolation<ImportUsersDto>> validateExceptions = this.validator.validate(usersDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid User";
        }

        Optional<User> byUser = this.userRepository.findByUsername(usersDto.getUsername());

        if (byUser.isPresent()) {
            return "Invalid User";
        }

        User user = this.modelMapper.map(usersDto, User.class);
        Optional<Picture> picture = this.pictureRepository.findByPath(usersDto.getProfilePicture());

        if (picture.isEmpty()) {
            return "Invalid User";
        }

        user.setProfilePicture(picture.get());

        this.userRepository.save(user);

        return user.toString();
    }
}
