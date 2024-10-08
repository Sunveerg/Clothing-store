package com.example.customerservice.customersubdomain.datalayer;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
@Embeddable
@EqualsAndHashCode
@Getter
public class Address {

    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    @SuppressWarnings("unused")
    public Address() {
    }

    public Address(@NotNull String streetAddress, @NotNull String city, @NotNull String province, @NotNull String country, @NotNull String postalCode) {

        Objects.requireNonNull(this.streetAddress = streetAddress);
        Objects.requireNonNull(this.city = city);
        Objects.requireNonNull(this.province = province);
        Objects.requireNonNull(this.country = country);
        Objects.requireNonNull(this.postalCode = postalCode);

    }

}
