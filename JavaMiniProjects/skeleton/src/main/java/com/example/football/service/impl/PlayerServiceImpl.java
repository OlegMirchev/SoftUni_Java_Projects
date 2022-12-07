package com.example.football.service.impl;

import com.example.football.models.dto.ExportPlayersDto;
import com.example.football.models.dto.ImportPlayersDto;
import com.example.football.models.dto.PlayerDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "xml", "players.xml");

    private PlayerRepository playerRepository;
    private TownRepository townRepository;
    private TeamRepository teamRepository;
    private StatRepository statRepository;

    private ModelMapper modelMapper;
    private Validator validator;


    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importPlayers() throws IOException, JAXBException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportPlayersDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportPlayersDto playersDto = (ImportPlayersDto) unmarshaller.unmarshal(fileReader);

        return playersDto.getPlayers().stream().map(this::validatePlayer).collect(Collectors.joining("\n"));
    }

    @Override
    public String exportBestPlayers() {
        List<ExportPlayersDto> playersDto = this.playerRepository.findBestPlayersFromFootballTeam();

        StringBuilder output = new StringBuilder();

        for (ExportPlayersDto p : playersDto) {
           output.append(String.format("""
                   Player - %s %s
                         Position - %s
                         Team - %s
                         Stadium - %s
                   """, p.getFirstName(), p.getLastName(), p.getPosition(), p.getTeamName(), p.getStadiumName()));
        }

        return output.toString().trim();
    }

    private String validatePlayer(PlayerDto playerDto) {
        Set<ConstraintViolation<PlayerDto>> validatePlayer = this.validator.validate(playerDto);

        if (!validatePlayer.isEmpty()) {
            return "Invalid Player";
        }

        Optional<Player> playersOp = this.playerRepository.findByFirstNameAndLastName(playerDto.getFirstName(), playerDto.getLastName());

        if (playersOp.isPresent()) {
            return "Invalid Player";
        }

        Player player = this.modelMapper.map(playerDto, Player.class);

        Optional<Town> town = this.townRepository.findByName(playerDto.getTown().getName());
        Optional<Team> team = this.teamRepository.findByName(playerDto.getTeam().getName());
        Optional<Stat> stat = this.statRepository.findById(playerDto.getStat().getId());

        player.setTown(town.get());
        player.setTeam(team.get());
        player.setStat(stat.get());

        this.playerRepository.save(player);

        return player.toString();
    }
}
