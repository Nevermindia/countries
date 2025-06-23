package guru.qa.controller;

import guru.qa.domain.Country;
import guru.qa.service.CountriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/country")
public class CountriesController {

    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/all")
    public List<Country> all() {
        return countriesService.allCountries();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCountry(
            @RequestParam String name,
            @RequestParam String code) {
        countriesService.addCountry(name, code);
        return ResponseEntity.ok("Country added successfully");
    }

    @PatchMapping("/{code}/name")
    public void updateCountryName(
            @PathVariable String code,
            @RequestParam String newName) {
        countriesService.updateCountry(code, newName);
    }
}