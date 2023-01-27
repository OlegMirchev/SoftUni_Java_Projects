package com.example.LogSentinel.Service;

import com.example.LogSentinel.Models.dto.FlightDto;
import com.example.LogSentinel.Models.dto.FlightStatisticsDto;
import com.example.LogSentinel.Models.entities.Flight;
import com.example.LogSentinel.Repository.FlightRepository;
import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private static final Path PATH = Path.of("src", "main", "resources", "files", "flight.json");

    private FlightRepository flightRepository;

    private ModelMapper modelMapper;
    private Gson gson;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDateTime.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            }
        }).create();
    }

    public void importFlights() throws IOException {
        String pathFlights = Files.readString(PATH);

        FlightDto[] importFlights = this.gson.fromJson(pathFlights, FlightDto[].class);

        List<Flight> flights = Arrays.stream(importFlights).map(flightDto -> this.modelMapper.map(flightDto, Flight.class)).collect(Collectors.toList());

        this.flightRepository.saveAll(flights);
    }

    public FlightDto changeFlight(long id) {
        Flight flight = this.flightRepository.findByIdFlightAndChangeHisAmount(id);

        return this.modelMapper.map(flight, FlightDto.class);
    }

    public List<FlightDto> selectAllFlights() {
        List<Flight> flights = this.flightRepository.findAllFlights();

        return flights.stream().map(f -> this.modelMapper.map(f, FlightDto.class)).collect(Collectors.toList());
    }

    public int countOfDeleteFlights() {
        return this.flightRepository.findAllFightsAndCountOfDeleteThem();
    }

    public FlightStatisticsDto statisticsOfAllFlights() {
        return this.flightRepository.findStatisticsOfFlights();
    }
}
