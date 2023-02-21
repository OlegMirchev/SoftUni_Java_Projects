package com.example.LikeBook.Service;

import com.example.LikeBook.Model.dto.LoginUserDto;
import com.example.LikeBook.Model.dto.RegisterUserDto;
import com.example.LikeBook.Model.entities.User;
import com.example.LikeBook.Repository.UserRepository;
import com.example.LikeBook.Utils.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private CurrentUser currentUser;

    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;

        this.modelMapper = new ModelMapper();
    }

    public void register(RegisterUserDto registerUserDto) {
        User user = this.modelMapper.map(registerUserDto, User.class);

        this.userRepository.save(user);

        this.currentUser.setId(user.getId());
        this.currentUser.setLogged(true);
    }

    public boolean login(LoginUserDto loginUserDto) {
        Optional<User> userOpt = this.userRepository.findByUsernameAndPassword(loginUserDto.getUsername(), loginUserDto.getPassword());

        if (userOpt.isPresent()) {
            this.currentUser.setId(userOpt.get().getId());
            this.currentUser.setLogged(true);
        }else {
            return false;
        }

        return true;
    }

    public void logout() {
        this.currentUser.clear();
    }

    public Optional<User> selectCurrentUserBy(long id) {
        return this.userRepository.findById(id);
    }
}
