package ru.naykama.imsdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.naykama.imsdb.exception.AlreadyExistException;

import static ru.naykama.imsdb.AccountMapper.convertToDto;
import static ru.naykama.imsdb.AccountMapper.convertToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    public PersonAccountDto createAccount(PersonAccountDto accountDto) {
        if (repository.existsByAccount(accountDto.getAccount())) {
            log.error("Person with account {} is already exists", accountDto.getAccount());
            throw new AlreadyExistException(String.format("Person with account %d is already exists",
                                                            accountDto.getAccount()));
        }
        return convertToDto(repository.save(convertToEntity(accountDto)));
    }
}
