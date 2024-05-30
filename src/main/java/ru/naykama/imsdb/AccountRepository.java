package ru.naykama.imsdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<PersonAccount, Long> {
    @Query("SELECT account FROM PersonAccount account\n" +
            "WHERE (:name IS NULL OR account.name = :name) AND\n" +
            "(:value IS NULL OR ROUND(account.value, :scale) = :value)")
    List<PersonAccount> findAllByParams(String name, Double value, Integer scale);

    List<PersonAccount> findByName(String name);

    @Query("SELECT account FROM PersonAccount account\n" +
            "WHERE ROUND(account.value, :scale) = :value")
    List<PersonAccount> findByValue(Double value, Integer scale);
}
