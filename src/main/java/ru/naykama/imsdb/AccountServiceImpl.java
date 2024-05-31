package ru.naykama.imsdb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.naykama.imsdb.cache.Cache;
import ru.naykama.imsdb.dto.AccountCreateUpdateDto;
import ru.naykama.imsdb.dto.PersonAccountDto;
import ru.naykama.imsdb.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.naykama.imsdb.AccountMapper.convertToDto;
import static ru.naykama.imsdb.AccountMapper.convertToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final Cache repository = new Cache();

    @Override
    public PersonAccountDto createAccount(AccountCreateUpdateDto accountDto) {
        return convertToDto(repository.saveAccount(convertToEntity(accountDto)));
    }

    @Override
    public void deleteAccount(long id) {
        repository.delete(id).orElseThrow((() -> {
            log.error("Account {} not found", id);
            return new NotFoundException(String.format("Account %d not found", id));
        }));
    }

    @Override
    public PersonAccountDto findAccountById(long account) {
        return convertToDto(repository.get(account).orElseThrow(() -> {
            log.error("Account {} not found", account);
            return new NotFoundException(String.format("Account %d not found", account));
        }));
    }

    @Override
    public List<PersonAccountDto> findAllByParams(Long account, String name, Double value) {
        List<PersonAccount> result = null;
        if (name != null) {
            result = repository.getByName(name).orElseGet(ArrayList::new);
        }
        if (value != null) {
            List<PersonAccount> resultForValue = repository.getByValue(value).orElseGet(ArrayList::new);
            result = result == null ? resultForValue : result.stream()
                    .filter(resultForValue::contains)
                    .collect(Collectors.toList());
        }
        if (account != null) {
            List<PersonAccount> resultForAccount = repository.getByAccount(account).orElseGet(ArrayList::new);
            result = result == null ? resultForAccount : result.stream()
                    .filter(resultForAccount::contains)
                    .collect(Collectors.toList());
        }
        if (value == null && name == null && account == null) {
            result = repository.findAll();
        }
        return result.stream()
                .map(AccountMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonAccountDto updateAccount(long id, AccountCreateUpdateDto accountDto) {
        return convertToDto(repository.update(id, convertToEntity(accountDto)).orElseThrow((() -> {
            log.error("Account {} not found", id);
            return new NotFoundException(String.format("Account %d not found", id));
        })));
    }
}
