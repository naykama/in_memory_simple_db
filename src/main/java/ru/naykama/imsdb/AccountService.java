package ru.naykama.imsdb;

import ru.naykama.imsdb.dto.AccountCreateUpdateDto;
import ru.naykama.imsdb.dto.PersonAccountDto;

import java.util.List;

public interface AccountService {
    PersonAccountDto createAccount(AccountCreateUpdateDto accountDto);
    void deleteAccount(long id);
    PersonAccountDto updateAccount(long account, AccountCreateUpdateDto accountDto);
    PersonAccountDto findAccountById(long id);
    List<PersonAccountDto> findAllByParams(Long account, String name, Double value);
}
