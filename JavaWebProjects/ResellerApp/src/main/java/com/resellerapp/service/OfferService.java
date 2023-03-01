package com.resellerapp.service;

import com.resellerapp.model.dto.AddOfferDto;
import com.resellerapp.model.dto.OfferOtherDto;
import com.resellerapp.model.dto.OfferUserDto;
import com.resellerapp.model.entities.Condition;
import com.resellerapp.model.entities.Offer;
import com.resellerapp.model.entities.User;
import com.resellerapp.repository.ConditionRepository;
import com.resellerapp.repository.OfferRepository;
import com.resellerapp.repository.UserRepository;
import com.resellerapp.util.Enum.ConditionType;
import com.resellerapp.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private OfferRepository offerRepository;
    private UserRepository userRepository;
    private ConditionRepository conditionRepository;
    private CurrentUser currentUser;

    private ModelMapper modelMapper;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, ConditionRepository conditionRepository, CurrentUser currentUser) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.conditionRepository = conditionRepository;
        this.currentUser = currentUser;

        this.modelMapper = new ModelMapper();
    }

    public void addOffer(AddOfferDto addOfferDto) {
        Optional<User> user = this.userRepository.findById(this.currentUser.getId());
        Optional<Condition> condition = this.conditionRepository.findByName(ConditionType.valueOf(addOfferDto.getCondition()));

        Offer offer = this.modelMapper.map(addOfferDto, Offer.class);

        offer.setUser(user.get());
        offer.setCondition(condition.get());

        this.offerRepository.save(offer);
    }

    public List<OfferUserDto> selectAllOfferWithUserId(long id) {
        return this.offerRepository.findAllByUserId(id).stream().map(o -> this.modelMapper.map(o, OfferUserDto.class))
                .collect(Collectors.toList());
    }

    public List<OfferOtherDto> selectAllOfferWithOtherUserIdNot(long id) {
        return this.offerRepository.findPostsByUserIdNot(id).stream().map(o -> this.modelMapper.map(o, OfferOtherDto.class))
                .collect(Collectors.toList());
    }

    public void buyOffer(long id, long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        Optional<Offer> offer = this.offerRepository.findById(id);

        user.get().getBoughtOffers().add(offer.get());

        this.userRepository.save(user.get());
    }

    public void removeOffer(long id) {
        this.offerRepository.deleteById(id);
    }
}
