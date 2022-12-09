package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportAgentsDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;

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
public class AgentServiceImpl implements AgentService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "json", "agents.json");

    private AgentRepository agentRepository;
    private TownRepository townRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importAgents() throws IOException {
        String agentPath = readAgentsFromFile();

        ImportAgentsDto[] agentsDto = this.gson.fromJson(agentPath, ImportAgentsDto[].class);

        return Arrays.stream(agentsDto).map(this::validateAgent).collect(Collectors.joining("\n"));
    }

    private String validateAgent(ImportAgentsDto agentsDto) {
        Set<ConstraintViolation<ImportAgentsDto>> validateExceptions = this.validator.validate(agentsDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid agent";
        }

        Optional<Agent> byNameAgent = this.agentRepository.findByFirstName(agentsDto.getFirstName());

        if (byNameAgent.isPresent()) {
            return "Invalid agent";
        }

        Agent agent = this.modelMapper.map(agentsDto, Agent.class);
        Optional<Town> town = this.townRepository.findByTownName(agentsDto.getTown());

        agent.setTown(town.get());

        this.agentRepository.save(agent);

        return agent.toString();
    }
}
