package com.example.LogSentinel.Web;

import com.example.LogSentinel.Models.dto.FlightDto;
import com.example.LogSentinel.Models.dto.FlightStatisticsDto;
import com.example.LogSentinel.Service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api-flights")
public class FlightController {

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/flights")
    public String postAllFlights() throws IOException {
        this.flightService.importFlights();

        return "/flights";
    }

    @PutMapping("/flights/id")
    public FlightDto putFlights(@PathVariable long id) {
        return this.flightService.changeFlight(id);
    }

    @GetMapping("/flights")
    public List<FlightDto> getFlights() {
        return this.flightService.selectAllFlights();
    }

    @DeleteMapping("/flights")
    public int deleteFlights() {
       return this.flightService.countOfDeleteFlights();
    }

    @GetMapping("/statistics")
    public FlightStatisticsDto statistics() {
        return this.flightService.statisticsOfAllFlights();
    }
}
