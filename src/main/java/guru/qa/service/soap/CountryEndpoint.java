package guru.qa.service.soap;


import guru.qa.config.AppConfig;
import guru.qa.domain.graphql.CountryGql;
import guru.qa.domain.graphql.InputCountryGql;
import guru.qa.service.CountriesService;
import guru.qa.xml.country.CountriesResponse;
import guru.qa.xml.country.Country;
import guru.qa.xml.country.CountryInputRequest;
import guru.qa.xml.country.CountryResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CountryEndpoint {
    private final CountriesService countryService;

    public CountryEndpoint(CountriesService countryService) {
        this.countryService = countryService;
    }


    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "allRequest")
    @ResponsePayload
    public CountriesResponse all(@RequestPayload PageRequest request) {
        List<CountryGql> countries = countryService.allGqlCountries();
        CountriesResponse response = new CountriesResponse();
        response.getCountries().addAll(
                countries.stream()
                        .map(countryGql -> {
                            Country xmlCountry = fromGql(countryGql);
                            return xmlCountry;
                        }).toList()
        );

        return response;
    }

    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "countryInputRequest")
    @ResponsePayload
    public CountryResponse add(@RequestPayload CountryInputRequest request) {
        CountryGql country = countryService.addGqlCountry(
                new InputCountryGql(request.getName(), request.getCode())
        );
        CountryResponse response = new CountryResponse();
        Country xmlCountry = fromGql(country);
        response.setCountry(xmlCountry);

        return response;
    }

    private static Country fromGql(CountryGql country) {
        Country xmlCountry = new Country();
        xmlCountry.setId(country.id().toString());
        xmlCountry.setName(country.countryName());
        xmlCountry.setCode(country.countryCode());
        return xmlCountry;
    }
}
