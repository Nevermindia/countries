package guru.qa.controller.graphql;

import guru.qa.domain.graphql.CountryGql;
import guru.qa.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CountriesQueryController {

    private final CountriesService countriesService;

    @Autowired
    public CountriesQueryController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @QueryMapping()
    public List<CountryGql> allCountries() {
        return countriesService.allGqlCountries();
    }
}