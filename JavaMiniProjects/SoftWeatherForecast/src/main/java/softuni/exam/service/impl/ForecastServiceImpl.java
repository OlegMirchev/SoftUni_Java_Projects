package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportForecastsDto;
import softuni.exam.models.dto.ForecastDto;
import softuni.exam.models.dto.ImportForecastsDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;

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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {
    private static final Path PATH = Path.of("src", "main", "resources", "files", "xml", "forecasts.xml");

    private ForecastRepository forecastRepository;
    private CityRepository cityRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(PATH);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        FileReader fileReader = new FileReader(PATH.toAbsolutePath().toString());

        JAXBContext context = JAXBContext.newInstance(ImportForecastsDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportForecastsDto forecastsDto = (ImportForecastsDto) unmarshaller.unmarshal(fileReader);

        return forecastsDto.getForecasts().stream().map(this::validateForecast).collect(Collectors.joining("\n"));
    }

    @Override
    public String exportForecasts() {
        List<ExportForecastsDto> exportForecasts = this.forecastRepository.findSundayForecasts();

        StringBuilder output = new StringBuilder();

        for (ExportForecastsDto forecast : exportForecasts) {
            output.append(String.format("""
                    City: %s:
                       		-min temperature: %.2f
                       		--max temperature: %.2f
                       		---sunrise: %s
                            ----sunset: %s
                    """, forecast.getCityName(), forecast.getMinTemperature(), forecast.getMaxTemperature(), forecast.getSunrise(), forecast.getSunset()));
        }

        return output.toString().trim();
    }

    private String validateForecast(ForecastDto forecastDto) {
        Set<ConstraintViolation<ForecastDto>> validateExceptions = this.validator.validate(forecastDto);

        if (!validateExceptions.isEmpty()) {
            return "Invalid forecast";
        }

        Optional<Forecast> byForecasts = this.forecastRepository.findForecastsWith(forecastDto.getCity(), forecastDto.getDayOfWeek());

        if (byForecasts.isPresent()) {
            return "Invalid forecast";
        }

        Forecast forecast = this.modelMapper.map(forecastDto, Forecast.class);
        Optional<City> city = this.cityRepository.findById(forecastDto.getCity());

        forecast.setCity(city.get());

        this.forecastRepository.save(forecast);

        return forecast.toString();
    }
}
