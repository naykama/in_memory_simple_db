package ru.naykama.imsdb.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.naykama.imsdb.PersonAccount;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class LRUCache {

    private final LinkedHashMap<Long, PersonAccount> mainCache;
    private final LinkedHashMap<String, List<Long>> nameCache;
    private final LinkedHashMap<Double, List<Long>> valueCache;

    private final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        mainCache = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<Long, PersonAccount> eldest) {
                return size() > capacity;
            }
        };
        nameCache = new LinkedHashMap<>();
        valueCache = new LinkedHashMap<>();
    }

    public PersonAccount put(PersonAccount value) {
        if (mainCache.size() == capacity) {
            PersonAccount eldestPerson = mainCache.entrySet().iterator().next().getValue();
                nameCache.remove(eldestPerson.getName());
                valueCache.remove(eldestPerson.getValue());
        }
        return mainCache.put(value.getAccount(), value);
    }

    public void putByName(String name, List<PersonAccount> personAccounts) {
        if (personAccounts.size() > capacity) {
            return;
        }
        for (PersonAccount personAccount : personAccounts) {
            put(personAccount);
        }
        nameCache.put(name, personAccounts.stream()
                                                        .map(PersonAccount::getAccount)
                                                        .collect(Collectors.toList()));
    }

    public void putByValue(double value, List<PersonAccount> personAccounts) {
        if (personAccounts.size() > capacity) {
            return;
        }
        for (PersonAccount personAccount : personAccounts) {
            put(personAccount);
        }
        valueCache.put(value, personAccounts.stream()
                .map(PersonAccount::getAccount)
                .collect(Collectors.toList()));
    }

    public Optional<PersonAccount> get(Long key) {
        log.info("PersonAccount {} is gotten from mainCache", mainCache.get(key));
        return Optional.ofNullable(mainCache.get(key));
    }

    public Optional<List<PersonAccount>> getByName(String name) {
        return Optional.ofNullable(!nameCache.containsKey(name) ? null : nameCache.get(name).stream()
                .map(mainCache::get)
                .collect(Collectors.toList()));
    }

    public Optional<List<PersonAccount>> getByValue(Double value) {
        return Optional.ofNullable(!valueCache.containsKey(value) ? null : nameCache.get(value).stream()
                .map(mainCache::get)
                .collect(Collectors.toList()));
    }

    public boolean isEmpty() {
        return mainCache.isEmpty();
    }

    public int capacity() {
        return capacity;
    }

    public void clear() {
        mainCache.clear();
    }
}
