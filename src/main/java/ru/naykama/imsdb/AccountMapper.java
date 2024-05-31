package ru.naykama.imsdb;

import ru.naykama.imsdb.dto.AccountCreateUpdateDto;
import ru.naykama.imsdb.dto.PersonAccountDto;

public class AccountMapper {
    public static PersonAccount convertToEntity(AccountCreateUpdateDto dto) {
        return new PersonAccount(dto.getAccount(), dto.getName(), dto.getValue());
    }

    public static PersonAccountDto convertToDto(PersonAccount entity) {
        return new PersonAccountDto(entity.getId(), entity.getAccount(), entity.getName(), entity.getValue());
    }
}
