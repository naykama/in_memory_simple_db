package ru.naykama.imsdb.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RecordDto extends RecordCreateUpdateDto {
    private final long id;

    public RecordDto(long id, long account, String name, double value) {
        super(account, name, value);
        this.id = id;
    }
}

