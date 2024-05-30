package ru.naykama.imsdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.naykama.imsdb.cache.LRUCache;
import ru.naykama.imsdb.dto.AccountUpdateDto;
import ru.naykama.imsdb.dto.PersonAccountDto;
import ru.naykama.imsdb.exception.AlreadyExistException;
import ru.naykama.imsdb.exception.NotFoundException;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.naykama.imsdb.AccountMapper.convertToDto;
import static ru.naykama.imsdb.AccountMapper.convertToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final LRUCache cache = new LRUCache(3);

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
        return convertToDto(cache.get(account).orElseGet(() -> getAccountFromDb(account)));
    }

    private PersonAccount getAccountFromDb(long account) {
        log.info("getting Account from DB");
        PersonAccount personAccount = repository.findById(account).orElseThrow(() -> {
            log.error("Account {} not found", account);
            return new NotFoundException(String.format("Account %d not found", account));
        });
        cache.put(personAccount);
        return personAccount;
    }

    @Override
    public List<PersonAccountDto> findAllByParams(String name, Double value) {
        List<PersonAccount> result = null;
        if (name != null) {
            result = cache.getByName(name).orElseGet(() -> findAllByNameFromDb(name));
        }
        if (value != null) {
            List<PersonAccount> resultForValue = cache.getByValue(value).orElseGet(
                    () -> repository.findByValue(value, BigDecimal.valueOf(value).scale()));
            result = result == null ? resultForValue : result.stream()
                    .filter(resultForValue::contains)
                    .collect(Collectors.toList());
        }
        if(value == null && name == null) {
            result = repository.findAll();
        }
        return result.stream()
                .map(AccountMapper::convertToDto)
                .collect(Collectors.toList());

//        return repository.findAllByParams(name, value,
//                        value == null ? null : BigDecimal.valueOf(value).scale()
//                ).stream()
//                .map(AccountMapper::convertToDto)
//                .collect(Collectors.toList());
    }

    private List<PersonAccount> findAllByNameFromDb(String name) {
        log.info("getting Account from DB");
        List<PersonAccount> personAccounts = repository.findByName(name);
        if (!personAccounts.isEmpty()) {
            cache.putByName(name, personAccounts);
        }
        return personAccounts;
    }

    private List<PersonAccount> findAllByValueFromDb(Double value) {
        log.info("getting Account from DB");
        List<PersonAccount> personAccounts = repository.findByValue(value, BigDecimal.valueOf(value).scale());
        if (!personAccounts.isEmpty()) {
            cache.putByValue(value, personAccounts);
        }
        return personAccounts;
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
