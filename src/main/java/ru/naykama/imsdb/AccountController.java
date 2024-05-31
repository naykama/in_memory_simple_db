package ru.naykama.imsdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.naykama.imsdb.dto.AccountCreateUpdateDto;
import ru.naykama.imsdb.dto.PersonAccountDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;

    @PostMapping
    public PersonAccountDto createAccount(@Valid @RequestBody AccountCreateUpdateDto accountDto) {
        log.info("Creating account: {}", accountDto.getAccount());
        return service.createAccount(accountDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable long id) {
        log.info("Deleting id: {}", id);
        service.deleteAccount(id);
    }

    @PutMapping("/{id}")
    public PersonAccountDto updateAccount(@PathVariable long id, @RequestBody AccountCreateUpdateDto accountDto) {
        log.info("Updating account: {}", id);
        return service.updateAccount(id, accountDto);
    }

    @GetMapping("/{id}")
    public PersonAccountDto findAccountById(@PathVariable long id) {
        log.info("Getting account: {}", id);
        return service.findAccountById(id);
    }

    @GetMapping
    public List<PersonAccountDto> findAccounts(@RequestParam(required = false) Long account,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) Double value) {
        log.info("Getting accounts for params: account = {}, name = {}, value = {}", account, name, value);
        return service.findAllByParams(account, name, value);
    }
}
