package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportTownsDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

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
    private static final Path PATH = Path.of("src", "main", "resources", "files", "json", "towns.json");

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
        return Files.readString(PATH);
    }

    @Override
    public String importTowns() throws IOException {
        String townsPath = readTownsFileContent();

        ImportTownsDto[] townsDto = this.gson.fromJson(townsPath, ImportTownsDto[].class);

        return Arrays.stream(townsDto).map(this::validateTowns).collect(Collectors.joining("\n"));
    }

    private String validateTowns(ImportTownsDto townsDto) {
        Set<ConstraintViolation<ImportTownsDto>> validateExceptions = this.validator.validate(townsDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid town";
        }

        Optional<Town> byTownName = this.townRepository.findByTownName(townsDto.getTownName());

        if (byTownName.isPresent()) {
            return "Invalid town";
        }

        Town town = this.modelMapper.map(townsDto, Town.class);

        this.townRepository.save(town);

        return town.toString();
    }
}
