package com.example.football.service.impl;

import com.example.football.models.dto.ImportTownsDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "teams.json");

        return Files.readString(path);
    }

    @Override
    public String importTowns() throws IOException {
        String townPath = readTownsFileContent();

        ImportTownsDto[] importTowns = this.gson.fromJson(townPath, ImportTownsDto[].class);

        return Arrays.stream(importTowns).map(this::validateTowns).collect(Collectors.joining("\n"));
    }

    private String validateTowns(ImportTownsDto townsDto) {
        Set<ConstraintViolation<ImportTownsDto>> validateTown = this.validator.validate(townsDto);

        if (!validateTown.isEmpty()) {
            return "Invalid Town";
        }

        Optional<Town> byNameTown = this.townRepository.findByName(townsDto.getName());

        if (byNameTown.isPresent()) {
            return "Invalid Town";
        }

        Town town = this.modelMapper.map(townsDto, Town.class);

        this.townRepository.save(town);

        return town.toString();
    }
}
