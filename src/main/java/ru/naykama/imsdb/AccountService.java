package ru.naykama.imsdb;

import ru.naykama.imsdb.dto.AccountUpdateDto;
import ru.naykama.imsdb.dto.PersonAccountDto;

import java.util.List;

public interface AccountService {
    PersonAccountDto createAccount(PersonAccountDto accountDto);
    void deleteAccount(long account);
    PersonAccountDto updateAccount(long account, AccountUpdateDto accountDto);
    PersonAccountDto findAccountById(long account);
    List<PersonAccountDto> findAllByParams(String name, Double minValue, Double maxValue);
}
