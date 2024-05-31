package ru.naykama.imsdb;

import ru.naykama.imsdb.dto.RecordCreateUpdateDto;
import ru.naykama.imsdb.dto.RecordDto;

public class RecordMapper {
    public static Record convertToEntity(RecordCreateUpdateDto dto) {
        return new Record(dto.getAccount(), dto.getName(), dto.getValue());
    }

    public static RecordDto convertToDto(Record entity) {
        return new RecordDto(entity.getId(), entity.getAccount(), entity.getName(), entity.getValue());
    }
}
