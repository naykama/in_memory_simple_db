package ru.naykama.imsdb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<PersonAccount, Long> {
    @Query("SELECT account FROM PersonAccount account\n" +
            "WHERE (:name IS NULL OR LOWER(account.name) LIKE CONCAT('%', LOWER(:name), '%')) AND\n" +
            "(:minValue IS NULL OR account.value >= :minValue) AND\n" +
            "(:maxValue IS NULL OR account.value <= :maxValue)")
    List<PersonAccount> findAllByParams(String name, Double minValue, Double maxValue);
}
