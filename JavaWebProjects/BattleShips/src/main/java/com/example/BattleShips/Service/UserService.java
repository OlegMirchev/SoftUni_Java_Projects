package com.example.BattleShips.Service;

import com.example.BattleShips.Models.dto.UserLoginDto;
import com.example.BattleShips.Models.dto.UserRegisterDto;
import com.example.BattleShips.Models.entities.User;
import com.example.BattleShips.Repository.UserRepository;
import com.example.BattleShips.Utils.CurrentUser;
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

    public void register(UserRegisterDto userRegisterDto) {
        User user = this.modelMapper.map(userRegisterDto, User.class);

        this.currentUser.setId(user.getId());
        this.currentUser.setLogged(true);

        this.userRepository.save(user);
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
}
