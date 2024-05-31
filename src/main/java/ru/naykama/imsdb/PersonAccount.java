package ru.naykama.imsdb;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class PersonAccount {
    private long id;
    private long account;
    private String name;
    private double value;

    public PersonAccount(long account, String name, double value) {
        this.account = account;
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonAccount)) return false;
        return id == ((PersonAccount) o).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
