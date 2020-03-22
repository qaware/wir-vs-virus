package de.qaware.mercury.mercury.storage.location.impl;

import de.qaware.mercury.mercury.business.location.GeoLocation;
import lombok.*;

import javax.persistence.*;

@Entity
@IdClass(GeoLocationId.class)
@Getter
// See https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "geolocation")
class GeoLocationEntity {
    @Id
    private String countryCode;

    @Id
    private String zipCode;

    @Id
    private String placeName;

    @Setter
    @Column(nullable = false)
    private double latitude;

    @Setter
    @Column(nullable = false)
    private double longitude;


    public GeoLocation toGeoLocation() {
        return new GeoLocation(latitude, longitude);
    }
}
