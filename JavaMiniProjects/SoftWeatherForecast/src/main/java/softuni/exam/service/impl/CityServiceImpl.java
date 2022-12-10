package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCitiesDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;

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
public class CityServiceImpl implements CityService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "json", "cities.json");

    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importCities() throws IOException {
        String citiesPath = readCitiesFileContent();

        ImportCitiesDto[] citiesDto = this.gson.fromJson(citiesPath, ImportCitiesDto[].class);

        return Arrays.stream(citiesDto).map(this::validateCity).collect(Collectors.joining("\n"));
    }

    private String validateCity(ImportCitiesDto citiesDto) {
        Set<ConstraintViolation<ImportCitiesDto>> validateExceptions = this.validator.validate(citiesDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid city";
        }

        Optional<City> byCity = this.cityRepository.findByCityName(citiesDto.getCityName());

        if (byCity.isPresent()) {
            return "Invalid city";
        }

        City city = this.modelMapper.map(citiesDto, City.class);
        Optional<Country> country = this.countryRepository.findById(citiesDto.getCountry());

        city.setCountry(country.get());

        this.cityRepository.save(city);

        return city.toString();
    }
}
