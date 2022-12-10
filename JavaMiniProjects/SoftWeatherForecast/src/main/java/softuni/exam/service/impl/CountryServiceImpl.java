package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCountriesDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;

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
public class CountryServiceImpl implements CountryService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "json", "countries.json");

    private CountryRepository countryRepository;

    private ModelMapper modelMapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importCountries() throws IOException {
        String countriesPath = readCountriesFromFile();

        ImportCountriesDto[] countriesDto = this.gson.fromJson(countriesPath, ImportCountriesDto[].class);

        return Arrays.stream(countriesDto).map(this::validateCountry).collect(Collectors.joining("\n"));
    }

    private String validateCountry(ImportCountriesDto countriesDto) {
        Set<ConstraintViolation<ImportCountriesDto>> validateExceptions = this.validator.validate(countriesDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid country";
        }

        Optional<Country> byCountry = this.countryRepository.findByCountryName(countriesDto.getCountryName());

        if (byCountry.isPresent()) {
            return "Invalid country";
        }

        Country country = this.modelMapper.map(countriesDto, Country.class);

        this.countryRepository.save(country);

        return country.toString();
    }
}
