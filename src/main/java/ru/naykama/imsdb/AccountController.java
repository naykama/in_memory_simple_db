package ru.naykama.imsdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

//    @DeleteMapping("/{account")
//    public void deleteAccount(@PathVariable long account) {
//        log.info("Deleting account: {}", account);
//        service.deleteAccount(account);
//    }
//
//    @PutMapping
//    public PersonAccountDto updateAccount(@Valid @RequestBody PersonAccountDto accountDto) {
//        log.info("Updating account: {}", accountDto.getAccount());
//        return service.updateAccount(accountDto);
//    }
//
//    @GetMapping("/{account}")
//    public PersonAccountDto getAccountById(@PathVariable long account) {
//        log.info("Getting account: {}", account);
//        return service.getAccountById(account);
//    }
//
//    @GetMapping
//    public List<PersonAccountDto> getAccounts(@RequestParam(required = false) String name,
//                                             @RequestParam(required = false) Double value) {
//        log.info("Getting accounts for params: name = {}, value = {}", name, value);
//        return service.getAccounts(name, value);
//    }
}
