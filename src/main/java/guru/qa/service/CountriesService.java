package guru.qa.service;

import guru.qa.domain.Country;

import java.util.List;

public interface CountriesService {
    List<Country> allCountries();
    void addCountry(String name, String code);
    void updateCountry(String countryCode, String newName);
}