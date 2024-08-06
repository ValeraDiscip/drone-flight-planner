package org.example.controller;

import org.example.dto.FlightDto;
import org.example.dto.FlightPossibilityResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequestMapping("flight")
public interface FlightController {
    @GetMapping("/evaluateCurrentPossibility")
    FlightPossibilityResult evaluateFlightPossibility();

    @PostMapping("/add")
    FlightDto addFlight(LocalDateTime timeOfFlight, Boolean successful);
}
