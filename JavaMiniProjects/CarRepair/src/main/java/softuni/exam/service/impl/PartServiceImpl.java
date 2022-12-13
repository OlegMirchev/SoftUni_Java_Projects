package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportPartsDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;

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
public class PartServiceImpl implements PartService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "json", "parts.json");

    private PartRepository partRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importParts() throws IOException {
        String partPath = readPartsFileContent();

        ImportPartsDto[] importParts = this.gson.fromJson(partPath, ImportPartsDto[].class);

        return Arrays.stream(importParts).map(this::validatePart).collect(Collectors.joining("\n"));
    }

    private String validatePart(ImportPartsDto partsDto) {
        Set<ConstraintViolation<ImportPartsDto>> validateExceptions = this.validator.validate(partsDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid part";
        }

        Optional<Part> byPart = this.partRepository.findByPartName(partsDto.getPartName());

        if (byPart.isPresent()) {
            return "Invalid part";
        }

        Part part = this.modelMapper.map(partsDto, Part.class);

        this.partRepository.save(part);

        return part.toString();
    }
}
