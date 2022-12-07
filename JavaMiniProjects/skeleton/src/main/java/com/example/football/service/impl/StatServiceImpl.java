package com.example.football.service.impl;

import com.example.football.models.dto.ImportStatsDto;
import com.example.football.models.dto.StatDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements StatService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "xml", "stats.xml");

    private StatRepository statRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importStats() throws IOException, JAXBException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportStatsDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportStatsDto statsDto = (ImportStatsDto) unmarshaller.unmarshal(fileReader);

        return statsDto.getStats().stream().map(this::validateStat).collect(Collectors.joining("\n"));
    }

    private String validateStat(StatDto statsDto) {
        Set<ConstraintViolation<StatDto>> validateStat = this.validator.validate(statsDto);

        if (!validateStat.isEmpty()) {
            return "Invalid Stat";
        }

        Optional<Stat> statOp = this.statRepository.findByShootingAndPassingAndEndurance(statsDto.getShooting(), statsDto.getPassing(), statsDto.getEndurance());

        if (statOp.isPresent()) {
            return "Invalid Stat";
        }

        Stat stat = this.modelMapper.map(statsDto, Stat.class);

        this.statRepository.save(stat);

        return stat.toString();
    }
}
