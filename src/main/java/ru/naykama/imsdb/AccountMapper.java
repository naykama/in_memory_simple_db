package ru.naykama.imsdb;

public class AccountMapper {
    public static PersonAccount convertToEntity(PersonAccountDto dto) {
        return new PersonAccount(dto.getAccount(), dto.getName(), dto.getValue());
    }

    public static PersonAccountDto convertToDto(PersonAccount entity) {
        return new PersonAccountDto(entity.getAccount(), entity.getName(), entity.getValue());
    }
}
