package ru.naykama.imsdb;

import java.util.List;

public interface AccountService {
    PersonAccountDto createAccount(PersonAccountDto accountDto);
    void deleteAccount(long account);
//    PersonAccountDto updateAccount(PersonAccountDto accountDto);
    PersonAccountDto findAccountById(long account);
    List<PersonAccountDto> findAllByParams(String name, Double minValue, Double maxValue);
}
