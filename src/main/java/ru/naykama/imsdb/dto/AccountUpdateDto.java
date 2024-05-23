package ru.naykama.imsdb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountUpdateDto {
    private String name;
    private Double value;
}
