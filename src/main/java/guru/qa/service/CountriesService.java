package guru.qa.service;

import guru.qa.domain.Country;
import guru.qa.domain.graphql.CountryGql;
import guru.qa.domain.graphql.InputCountryGql;

import java.util.List;

public interface CountriesService {
    List<Country> allCountries();
    List<CountryGql> allGqlCountries();
    void addCountry(String name, String code);
    CountryGql addGqlCountry(InputCountryGql input);
    void updateCountry(String countryCode, String newName);
    CountryGql updateGqlCountry(String countryCode, String newName);
}