package guru.qa.domain.graphql;

import java.util.UUID;

public record CountryGql(
        UUID id,
        String countryName,
        String countryCode
) {
}
