package com.example.football.service.impl;

import com.example.football.models.dto.ImportTeamsDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private TownRepository townRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "teams.json");

        return Files.readString(path);
    }

    @Override
    public String importTeams() throws IOException {
        String teamPath = readTeamsFileContent();

        ImportTeamsDto[] importTeams = this.gson.fromJson(teamPath, ImportTeamsDto[].class);

        return Arrays.stream(importTeams).map(this::validateTeam).collect(Collectors.joining("\n"));
    }

    private String validateTeam(ImportTeamsDto teamDto) {
        Set<ConstraintViolation<ImportTeamsDto>> validateTeam = this.validator.validate(teamDto);

        if (!validateTeam.isEmpty()) {
            return "Invalid Team";
        }

        Optional<Team> byNameTeam = this.teamRepository.findByName(teamDto.getName());

        if (byNameTeam.isPresent()) {
            return "Invalid Team";
        }

        Team team = this.modelMapper.map(teamDto, Team.class);
        Optional<Town> town = this.townRepository.findByName(teamDto.getTownName());

        team.setTown(town.get());

        this.teamRepository.save(team);

        return team.toString();
    }
}
