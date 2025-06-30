package guru.qa.controller.graphql;

import guru.qa.domain.graphql.CountryGql;
import guru.qa.domain.graphql.InputCountryGql;
import guru.qa.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountriesMutationController {

    private final CountriesService countriesService;

    @Autowired
    public CountriesMutationController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @MutationMapping
    public CountryGql addCountry(@Argument InputCountryGql input) {
        return countriesService.addGqlCountry(input);
    }

    @MutationMapping
    public CountryGql updateCountryName(@Argument InputCountryGql input) {
        return countriesService.updateGqlCountry(input.countryCode(), input.countryName());
    }
}