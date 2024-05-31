package ru.naykama.imsdb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@ToString
public class RecordCreateUpdateDto {
    @NotNull(message = "Account is required")
    private final Long account;
    @NotBlank(message = "Name is required")
    private final String name;
    @NotNull(message = "Value is required")
    private final Double value;
}
