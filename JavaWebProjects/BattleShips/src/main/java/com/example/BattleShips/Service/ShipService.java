package com.example.BattleShips.Service;

import com.example.BattleShips.Models.dto.AddShipDto;
import com.example.BattleShips.Models.dto.ShipDto;
import com.example.BattleShips.Models.entities.Category;
import com.example.BattleShips.Models.entities.Ship;
import com.example.BattleShips.Models.entities.User;
import com.example.BattleShips.Repository.CategoryRepository;
import com.example.BattleShips.Repository.ShipRepository;
import com.example.BattleShips.Repository.UserRepository;
import com.example.BattleShips.Utils.CurrentUser;
import com.example.BattleShips.Utils.Enum.CategoryType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private ShipRepository shipRepository;
    private CurrentUser currentUser;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ShipService(ShipRepository shipRepository, CurrentUser currentUser, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.shipRepository = shipRepository;
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;

        this.modelMapper = new ModelMapper();
    }

    public void addShip(AddShipDto addShipDto) {
        Optional<User> user = this.userRepository.findById(this.currentUser.getId());
        Optional<Category> category = this.categoryRepository.findByName(convertTypeCategory(addShipDto));

        Ship ship = this.modelMapper.map(addShipDto, Ship.class);

        ship.setCategory(category.get());
        ship.setUser(user.get());

        this.shipRepository.save(ship);
    }

    public List<ShipDto> selectShipsOwnedBy(Long userId) {
        return this.shipRepository.findByUserId(userId).stream()
                .map(s -> this.modelMapper.map(s, ShipDto.class)).collect(Collectors.toList());

    }

    public List<ShipDto> selectShipsOtherBy(Long otherId) {
        return this.shipRepository.findByUserIdNot(otherId).stream()
                .map(s -> this.modelMapper.map(s, ShipDto.class)).collect(Collectors.toList());

    }

    public List<ShipDto> getSortedAllShips() {
        return this.shipRepository.findByOrderByNameDescHealthAscPowerAsc().stream()
                .map(s -> this.modelMapper.map(s, ShipDto.class)).collect(Collectors.toList());
    }

    private CategoryType convertTypeCategory(AddShipDto addShipDto) {

        return switch (addShipDto.getCategory()) {
            case 0 -> CategoryType.BATTLE;
            case 1 -> CategoryType.CARGO;
            case 2 -> CategoryType.PATROL;
            default -> null;
        };
    }
}
