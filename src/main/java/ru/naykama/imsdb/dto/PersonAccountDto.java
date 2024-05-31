package ru.naykama.imsdb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@AllArgsConstructor
@Getter
public class PersonAccountDto {
    private long id;
    private Long account;
    private String name;
    private Double value;
}

