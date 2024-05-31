package ru.naykama.imsdb.cache;

import lombok.extern.slf4j.Slf4j;
import ru.naykama.imsdb.PersonAccount;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Cache {
    private long id;

    private final HashMap<Long, PersonAccount> mainCache;
    private final HashMap<Long, Set<Long>> accountCache;
    private final HashMap<String, Set<Long>> nameCache;
    private final HashMap<Double, Set<Long>> valueCache;

    public Cache() {
        mainCache = new HashMap<>();
        accountCache = new HashMap<>();
        nameCache = new HashMap<>();
        valueCache = new HashMap<>();
        id = 0L;
    }

    public PersonAccount saveAccount(PersonAccount personAccount) {
        personAccount.setId(++id);
        mainCache.put(personAccount.getId(), personAccount);
        accountCache.computeIfAbsent(personAccount.getAccount(), k -> new HashSet<>()).add(personAccount.getId());
        nameCache.computeIfAbsent(personAccount.getName(), k -> new HashSet<>()).add(personAccount.getId());
        valueCache.computeIfAbsent(personAccount.getValue(), k -> new HashSet<>()).add(personAccount.getId());
        return personAccount;
    }

    public Optional<PersonAccount> get(long id) {
        return Optional.ofNullable(mainCache.get(id));
    }

    public Optional<PersonAccount> delete(long id) {
        PersonAccount personAccount = mainCache.get(id);
        if (personAccount == null) {
            return Optional.empty();
        }
        accountCache.get(personAccount.getAccount()).remove(id);
        nameCache.get(personAccount.getName()).remove(id);
        valueCache.remove(personAccount.getValue()).remove(id);
        return Optional.of(mainCache.remove(id));
    }

    public Optional<PersonAccount> update(long id, PersonAccount personAccount) {
        if (mainCache.get(id) == null) {
            return Optional.empty();
        }
        delete(id);
        personAccount.setId(id);
        mainCache.put(id, personAccount);
        accountCache.computeIfAbsent(personAccount.getAccount(), k -> new HashSet<>()).add(personAccount.getId());
        nameCache.computeIfAbsent(personAccount.getName(), k -> new HashSet<>()).add(personAccount.getId());
        valueCache.computeIfAbsent(personAccount.getValue(), k -> new HashSet<>()).add(personAccount.getId());
        return Optional.of(mainCache.get(id));
    }

    public Optional<List<PersonAccount>> getByName(String name) {
        return Optional.ofNullable(!nameCache.containsKey(name) ? null : nameCache.get(name).stream()
                .map(mainCache::get)
                .collect(Collectors.toList()));
    }

    public Optional<List<PersonAccount>> getByValue(Double value) {
        return Optional.ofNullable(!valueCache.containsKey(value) ? null : valueCache.get(value).stream()
                .map(mainCache::get)
                .collect(Collectors.toList()));
    }

    public Optional<List<PersonAccount>> getByAccount(Long account) {
        return Optional.ofNullable(!accountCache.containsKey(account) ? null : accountCache.get(account).stream()
                .map(mainCache::get)
                .collect(Collectors.toList()));
    }

    public List<PersonAccount> findAll() {
        return new ArrayList<>(mainCache.values());
    }

    public boolean isEmpty() {
        return mainCache.isEmpty();
    }

    public void clear() {
        mainCache.clear();
    }
}
