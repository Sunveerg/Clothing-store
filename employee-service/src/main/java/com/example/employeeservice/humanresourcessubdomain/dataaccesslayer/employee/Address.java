package com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

/**
 * @author Christine Gerard
 * @created 01/30/2024
 * @project cardealership-ws-2024
 */

@Embeddable
@NoArgsConstructor
@Getter
public class Address {

    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    public Address(@NotNull String streetAddress, @NotNull String city, @NotNull String province, @NotNull String country, @NotNull String postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }
}
