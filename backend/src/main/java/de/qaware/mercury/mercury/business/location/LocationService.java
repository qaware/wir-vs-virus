package de.qaware.mercury.mercury.business.location;

import java.util.List;

public interface LocationService {
    GeoLocation lookup(String zipCode);

    /**
     * Search for all locations containing the given zip code.
     *
     * @param zipCode zip code to search for. Must have a length of at least 3, else an empty list will be returned.
     * @return all found hits
     */
    List<LocationSuggestion> suggest(String zipCode);
}
