package ru.naykama.imsdb;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<PersonAccount, Long> {
    boolean existsByAccount(Long account);
}
