package ru.naykama.imsdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.naykama.imsdb.dto.RecordCreateUpdateDto;
import ru.naykama.imsdb.dto.RecordDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/accounts")
public class RecordController {
    private final RecordService service;

    @PostMapping
    public RecordDto createRecord(@Valid @RequestBody RecordCreateUpdateDto recordDto) {
        log.info("Creating record: {}", recordDto);
        return service.createRecord(recordDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecord(@PathVariable long id) {
        log.info("Deleting record by id: {}", id);
        service.deleteRecord(id);
    }

    @PutMapping("/{id}")
    public RecordDto updateRecord(@PathVariable long id, @RequestBody RecordCreateUpdateDto recordDto) {
        log.info("Updating record by id: {}", id);
        return service.updateRecord(id, recordDto);
    }

    @GetMapping("/{id}")
    public RecordDto findRecordById(@PathVariable long id) {
        log.info("Getting record by id: {}", id);
        return service.findRecordById(id);
    }

    @GetMapping
    public List<RecordDto> findAllByParams(@RequestParam(required = false) Long account,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) Double value) {
        log.info("Getting records for params: account = {}, name = {}, value = {}", account, name, value);
        return service.findAllByParams(account, name, value);
    }
}
