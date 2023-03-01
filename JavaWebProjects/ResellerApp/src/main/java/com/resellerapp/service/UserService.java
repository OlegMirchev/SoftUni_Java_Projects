package com.resellerapp.service;

import com.resellerapp.model.dto.UserLoginDto;
import com.resellerapp.model.dto.UserRegisterDto;
import com.resellerapp.model.entities.Offer;
import com.resellerapp.model.entities.User;
import com.resellerapp.repository.UserRepository;
import com.resellerapp.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void register(UserRegisterDto userRegisterDto) {
        User user = this.modelMapper.map(userRegisterDto, User.class);

        this.userRepository.save(user);

        this.currentUser.setId(user.getId());
        this.currentUser.setLogged(true);
    }

    public boolean login(UserLoginDto userLoginDto) {
        Optional<User> user = this.userRepository.findByUsernameAndPassword(userLoginDto.getUsername(), userLoginDto.getPassword());

        if (user.isPresent()) {
            this.currentUser.setId(user.get().getId());
            this.currentUser.setLogged(true);
        }else {
            return false;
        }

        return true;
    }

    public void logout() {
        this.currentUser.clear();
    }

    public Optional<User> selectCurrentUserWithId(long id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> selectBoughtOffersWithUserId(long id) {
        return this.userRepository.findById(id);
    }
}
