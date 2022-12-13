package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportMechanicsDto;
import softuni.exam.models.dto.ImportPartsDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;

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
public class MechanicServiceImpl implements MechanicService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "json", "mechanics.json");

    private MechanicRepository mechanicRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importMechanics() throws IOException {
        String mechanicPath = readMechanicsFromFile();

        ImportMechanicsDto[] importMechanics = this.gson.fromJson(mechanicPath, ImportMechanicsDto[].class);

        return Arrays.stream(importMechanics).map(this::validateMechanic).collect(Collectors.joining("\n"));
    }

    private String validateMechanic(ImportMechanicsDto mechanicsDto) {
        Set<ConstraintViolation<ImportMechanicsDto>> validateExceptions = this.validator.validate(mechanicsDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid mechanic";
        }

        Optional<Mechanic> byMechanic = this.mechanicRepository.findByEmail(mechanicsDto.getEmail());

        if (byMechanic.isPresent()) {
            return "Invalid mechanic";
        }

        Mechanic mechanic = this.modelMapper.map(mechanicsDto, Mechanic.class);

        this.mechanicRepository.save(mechanic);

        return mechanic.toString();
    }
}
