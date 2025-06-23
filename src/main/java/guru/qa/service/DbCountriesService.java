package guru.qa.service;

import guru.qa.data.CountryEntity;
import guru.qa.data.CountryRepository;
import guru.qa.domain.Country;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class DbCountriesService implements CountriesService {
    private final CountryRepository countryRepository;

    public DbCountriesService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> allCountries() {
        return countryRepository.findAll().stream()
                .map(fe -> new Country(
                        fe.getCountryName(),
                        fe.getCountryCode()
                )).toList();
    }

    @Override
    public void addCountry(String name, String code) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCountryName(name);
        countryEntity.setCountryCode(code);
        countryRepository.save(countryEntity);
    }

    @Override
    public void updateCountry(String countryCode, String newName) {
        CountryEntity country = countryRepository.findByCountryCode(countryCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Country not found with code: " + countryCode
                ));

        country.setCountryName(newName);
        countryRepository.save(country);
    }
}