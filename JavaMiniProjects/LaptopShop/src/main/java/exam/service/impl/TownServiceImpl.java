package exam.service.impl;

import exam.model.dto.ImportTownsDto;
import exam.model.dto.TownDto;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "xml", "towns.xml");

    private TownRepository townRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;

        this.modelMapper = new ModelMapper();
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
    public String importTowns() throws JAXBException, FileNotFoundException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportTownsDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportTownsDto importTowns = (ImportTownsDto) unmarshaller.unmarshal(fileReader);

        return importTowns.getTowns().stream().map(this::validateTown).collect(Collectors.joining("\n"));
    }

    private String validateTown(TownDto townDto) {
        Set<ConstraintViolation<TownDto>> validateExceptions = this.validator.validate(townDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid town";
        }

        Optional<Town> byTown = this.townRepository.findByName(townDto.getName());

        if (byTown.isPresent()) {
            return "Invalid town";
        }

        Town town = this.modelMapper.map(townDto, Town.class);

        this.townRepository.save(town);

        return town.toString();
    }
}
