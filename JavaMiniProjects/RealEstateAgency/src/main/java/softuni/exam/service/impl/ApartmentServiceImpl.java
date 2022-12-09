package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dto.ApartmentDto;
import softuni.exam.models.dto.ImportApartmentsDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;

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
public class ApartmentServiceImpl implements ApartmentService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "xml", "apartments.xml");

    private ApartmentRepository apartmentRepository;
    private TownRepository townRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportApartmentsDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportApartmentsDto unmarshal = (ImportApartmentsDto) unmarshaller.unmarshal(fileReader);

        return unmarshal.getApartments().stream().map(this::validateApartment).collect(Collectors.joining("\n"));
    }

    private String validateApartment(ApartmentDto apartmentDto) {
        Set<ConstraintViolation<ApartmentDto>> validateExceptions = this.validator.validate(apartmentDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid apartment";
        }

        Optional<Apartment> byApartment = this.apartmentRepository.findApartmentTypeAndAreaAndTown(apartmentDto.getApartmentType(), apartmentDto.getArea(), apartmentDto.getTown());

        if (byApartment.isPresent()) {
            return "Invalid apartment";
        }

        Apartment apartment = this.modelMapper.map(apartmentDto, Apartment.class);
        Optional<Town> town = this.townRepository.findByTownName(apartmentDto.getTown());

        apartment.setTown(town.get());

        this.apartmentRepository.save(apartment);

        return apartment.toString();
    }
}
