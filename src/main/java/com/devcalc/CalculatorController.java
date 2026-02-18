package com.devcalc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/add")
    public Number add(@RequestParam double a, @RequestParam double b) {
        return normalize(calculatorService.add(a, b));
    }

    @GetMapping("/subtract")
    public Number subtract(@RequestParam double a, @RequestParam double b) {
        return normalize(calculatorService.subtract(a, b));
    }

    @GetMapping("/multiply")
    public Number multiply(@RequestParam double a, @RequestParam double b) {
        return normalize(calculatorService.multiply(a, b));
    }

    @GetMapping("/divide")
    public Number divide(@RequestParam double a, @RequestParam double b) {
        try {
            return normalize(calculatorService.divide(a, b));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    private Number normalize(double value) {
        if (value == Math.rint(value)) {
            System.out.println("Valor inteiro: " + value);
            return (long) value;
        }
        return value;
    }
}
