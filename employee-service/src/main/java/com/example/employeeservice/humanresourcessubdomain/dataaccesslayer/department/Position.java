package com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@Getter
@NoArgsConstructor
public class Position {

    private String title;
    private String code;

    public Position(@NotNull String title, @NotNull String code) {
        this.title = title;
        this.code = code;
    }
}
