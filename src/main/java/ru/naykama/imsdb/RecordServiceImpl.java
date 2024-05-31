package ru.naykama.imsdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.naykama.imsdb.cache.Cache;
import ru.naykama.imsdb.dto.RecordCreateUpdateDto;
import ru.naykama.imsdb.dto.RecordDto;
import ru.naykama.imsdb.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.naykama.imsdb.RecordMapper.convertToDto;
import static ru.naykama.imsdb.RecordMapper.convertToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordServiceImpl implements RecordService {
    private final Cache repository = new Cache();

    @Override
    public RecordDto createRecord(RecordCreateUpdateDto recordDto) {
        return convertToDto(repository.save(convertToEntity(recordDto)));
    }

    @Override
    public void deleteRecord(long id) {
        repository.deleteById(id).orElseThrow((() -> {
            log.error("Record with id = {} not found", id);
            return new NotFoundException(String.format("Record with id = %d not found", id));
        }));
    }

    @Override
    public RecordDto findRecordById(long id) {
        return convertToDto(repository.findById(id).orElseThrow(() -> {
            log.error("Record with id = {} not found", id);
            return new NotFoundException(String.format("Record with id = %d not found", id));
        }));
    }

    @Override
    public List<RecordDto> findAllByParams(Long account, String name, Double value) {
        List<Record> result = null;
        if (name != null) {
            result = repository.findByName(name).orElseGet(ArrayList::new);
        }
        if (value != null) {
            List<Record> resultForValue = repository.findByValue(value).orElseGet(ArrayList::new);
            result = result == null ? resultForValue : result.stream()
                    .filter(resultForValue::contains)
                    .collect(Collectors.toList());
        }
        if (account != null) {
            List<Record> resultForAccount = repository.findByAccount(account).orElseGet(ArrayList::new);
            result = result == null ? resultForAccount : result.stream()
                    .filter(resultForAccount::contains)
                    .collect(Collectors.toList());
        }
        if (value == null && name == null && account == null) {
            result = repository.findAll();
        }
        return result.stream()
                .map(RecordMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecordDto updateRecord(long id, RecordCreateUpdateDto recordDto) {
        return convertToDto(repository.updateById(id, convertToEntity(recordDto)).orElseThrow((() -> {
            log.error("Record with id = {} not found", id);
            return new NotFoundException(String.format("Record with id = %d not found", id));
        })));
    }
}
