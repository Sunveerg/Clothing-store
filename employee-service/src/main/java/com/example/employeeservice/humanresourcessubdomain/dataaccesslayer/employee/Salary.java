package com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

/**
 * @author Christine Gerard
 * @created 02/06/2024
 * @project cardealership-ws-2024
 */

@Embeddable
@NoArgsConstructor
@Getter
public class Salary {

    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    public Salary(@NotNull BigDecimal salary, @NotNull CurrencyType currency) {
        this.salary = salary;
        this.currency = currency;
    }
}
