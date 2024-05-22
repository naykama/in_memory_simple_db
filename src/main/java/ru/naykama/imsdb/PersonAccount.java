package ru.naykama.imsdb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "accounts")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonAccount {
    @Id
    private long account;
    private String name;
    private double value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonAccount)) return false;
        return account == ((PersonAccount) o).getAccount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}
