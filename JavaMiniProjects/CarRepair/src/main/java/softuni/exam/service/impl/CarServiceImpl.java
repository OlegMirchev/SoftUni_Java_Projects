package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarDto;
import softuni.exam.models.dto.ImportCarsDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;

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
public class CarServiceImpl implements CarService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "xml", "cars.xml");

    private CarRepository carRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportCarsDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportCarsDto importCars = (ImportCarsDto) unmarshaller.unmarshal(fileReader);

        return importCars.getCars().stream().map(this::validateCar).collect(Collectors.joining("\n"));
    }

    private String validateCar(CarDto carDto) {
        Set<ConstraintViolation<CarDto>> validateExceptions = this.validator.validate(carDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid car";
        }

        Optional<Car> byCar = this.carRepository.findByPlateNumber(carDto.getPlateNumber());

        if (byCar.isPresent()) {
            return "Invalid car";
        }

        Car car = this.modelMapper.map(carDto, Car.class);

        this.carRepository.save(car);

        return car.toString();
    }
}
