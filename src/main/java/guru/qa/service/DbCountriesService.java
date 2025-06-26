package guru.qa.service;

import guru.qa.data.CountryEntity;
import guru.qa.data.CountryRepository;
import guru.qa.domain.Country;
import guru.qa.domain.graphql.CountryGql;
import guru.qa.domain.graphql.CountryInputGql;
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
    public List<CountryGql> allGqlCountries() {
        return countryRepository.findAll().stream()
                .map(fe -> new CountryGql(
                        fe.getId(),
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
    public CountryGql addGqlCountry(CountryInputGql input) {
        CountryEntity ce = new CountryEntity();
        ce.setCountryCode(input.countryCode());
        ce.setCountryName(input.countryName());
        CountryEntity saved = countryRepository.save(ce);
        return new CountryGql(
                saved.getId(),
                saved.getCountryName(),
                saved.getCountryCode()
        );
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

    @Override
    public CountryGql updateGqlCountry(String countryCode, String newName) {
        CountryEntity ce = countryRepository.findByCountryCode(countryCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Country not found with code: " + countryCode
                ));
        ce.setCountryName(newName);
        CountryEntity updated = countryRepository.save(ce);
        return new CountryGql(
                updated.getId(),
                updated.getCountryName(),
                updated.getCountryCode()
        );
    }
}