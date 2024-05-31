package ru.naykama.imsdb;

import ru.naykama.imsdb.dto.RecordCreateUpdateDto;
import ru.naykama.imsdb.dto.RecordDto;

import java.util.List;

public interface RecordService {
    RecordDto createRecord(RecordCreateUpdateDto recordDto);
    void deleteRecord(long id);
    RecordDto updateRecord(long account, RecordCreateUpdateDto recordDto);
    RecordDto findRecordById(long id);
    List<RecordDto> findAllByParams(Long account, String name, Double value);
}
