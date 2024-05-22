package ru.naykama.imsdb;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@AllArgsConstructor
@Getter
public class PersonAccountDto {
    @NotNull(message = "Account is required")
    private Long account;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Value is required")
    private Double value;
}
