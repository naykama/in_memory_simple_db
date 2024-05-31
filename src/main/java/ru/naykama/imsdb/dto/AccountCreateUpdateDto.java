package ru.naykama.imsdb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class AccountCreateUpdateDto {
    @NotNull(message = "Account is required")
    private Long account;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Value is required")
    private Double value;
}
