package ru.naykama.imsdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.naykama.imsdb.dto.AccountUpdateDto;
import ru.naykama.imsdb.dto.PersonAccountDto;
import ru.naykama.imsdb.exception.AlreadyExistException;
import ru.naykama.imsdb.exception.NotFoundException;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static ru.naykama.imsdb.AccountMapper.convertToDto;
import static ru.naykama.imsdb.AccountMapper.convertToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    @Override
    public PersonAccountDto createAccount(PersonAccountDto accountDto) {
        if (repository.existsById(accountDto.getAccount())) {
            log.error("Person with account {} is already exists", accountDto.getAccount());
            throw new AlreadyExistException(String.format("Person with account %d is already exists",
                                                            accountDto.getAccount()));
        }
        return convertToDto(repository.save(convertToEntity(accountDto)));
    }

    @Override
    @Transactional
    public void deleteAccount(long account) {
        if (!repository.existsById(account)) {
            log.error("Account {} not found", account);
            throw new NotFoundException(String.format("Account %d not found", account));
        }
        repository.deleteById(account);
    }

    @Override
    public PersonAccountDto findAccountById(long account) {
        return convertToDto(repository.findById(account)
                .orElseThrow(() -> {
                    log.error("Account {} not found", account);
                    return new NotFoundException(String.format("Account %d not found", account));
                })
        );
    }

    @Override
    public List<PersonAccountDto> findAllByParams(String name, Double minValue, Double maxValue) {
        return repository.findAllByParams(name, minValue, maxValue).stream()
                .map(AccountMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonAccountDto updateAccount(long account, AccountUpdateDto accountDto) {
        PersonAccount oldAccount = repository.findById(account).orElseThrow(() -> {
            log.error("Account {} not found", account);
            return new NotFoundException(String.format("Account %d not found", account));
        });
        PersonAccount updatedAccount = new PersonAccount(account,
                accountDto.getName() == null ? oldAccount.getName() : accountDto.getName(),
                accountDto.getValue() == null ? oldAccount.getValue() : accountDto.getValue());
        return convertToDto(repository.save(updatedAccount));
    }
}
