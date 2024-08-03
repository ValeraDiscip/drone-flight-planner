package org.example.controller;

import org.example.dto.FlightPossibilityResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/flight")
public interface FlightController {
    @GetMapping("/evaluateCurrentPossibility")
    FlightPossibilityResult evaluateFlightPossibility();
}
