package ru.naykama.imsdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.naykama.imsdb.dto.AccountUpdateDto;
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
    public PersonAccountDto createAccount(@Valid @RequestBody PersonAccountDto accountDto) {
        log.info("Creating account: {}", accountDto.getAccount());
        return service.createAccount(accountDto);
    }

    @DeleteMapping("/{account}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable long account) {
        log.info("Deleting account: {}", account);
        service.deleteAccount(account);
    }

    @PatchMapping("/{account}")
    public PersonAccountDto updateAccount(@PathVariable long account, @RequestBody AccountUpdateDto accountDto) {
        log.info("Updating account: {}", account);
        return service.updateAccount(account, accountDto);
    }

    @GetMapping("/{account}")
    public PersonAccountDto findAccountById(@PathVariable long account) {
        log.info("Getting account: {}", account);
        return service.findAccountById(account);
    }

    @GetMapping
    public List<PersonAccountDto> findAccounts(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) Double minValue,
                                              @RequestParam(required = false) Double maxValue) {
        log.info("Getting accounts for params: name = {}, minValue = {}, maxValue = {}", name, minValue, maxValue);
        return service.findAllByParams(name, minValue, maxValue);
    }
}
