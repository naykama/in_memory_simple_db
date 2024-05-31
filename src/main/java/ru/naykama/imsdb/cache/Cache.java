package ru.naykama.imsdb.cache;

import lombok.extern.slf4j.Slf4j;
import ru.naykama.imsdb.Record;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Cache {
    private long id;

    private final HashMap<Long, Record> mainCache;
    private final HashMap<Long, Set<Long>> accountCache;
    private final HashMap<String, Set<Long>> nameCache;
    private final HashMap<String, Set<Long>> valueCache;

    public Cache() {
        mainCache = new HashMap<>();
        accountCache = new HashMap<>();
        nameCache = new HashMap<>();
        valueCache = new HashMap<>();
        id = 0L;
    }

    public Record save(Record record) {
        record.setId(++id);
        putRecordInAllCaches(record);
        return record;
    }

    public Optional<Record> findById(long id) {
        return Optional.ofNullable(mainCache.get(id));
    }

    public Optional<Record> deleteById(long id) {
        Record record = mainCache.get(id);
        if (record == null) {
            return Optional.empty();
        }
        accountCache.get(record.getAccount()).remove(id);
        nameCache.get(record.getName()).remove(id);
        valueCache.remove(String.valueOf(record.getValue())).remove(id);
        return Optional.of(mainCache.remove(id));
    }

    public Optional<Record> updateById(long id, Record record) {
        if (mainCache.get(id) == null) {
            return Optional.empty();
        }
        deleteById(id);
        record.setId(id);
        putRecordInAllCaches(record);
        return Optional.of(mainCache.get(id));
    }

    public Optional<List<Record>> findByName(String name) {
        return Optional.ofNullable(!nameCache.containsKey(name) ? null : nameCache.get(name).stream()
                .map(mainCache::get)
                .collect(Collectors.toList()));
    }

    public Optional<List<Record>> findByValue(Double value) {
        return Optional.ofNullable(!valueCache.containsKey(String.valueOf(value)) ? null : valueCache.get(String.valueOf(value)).stream()
                .map(mainCache::get)
                .collect(Collectors.toList()));
    }

    public Optional<List<Record>> findByAccount(Long account) {
        return Optional.ofNullable(!accountCache.containsKey(account) ? null : accountCache.get(account).stream()
                .map(mainCache::get)
                .collect(Collectors.toList()));
    }

    public List<Record> findAll() {
        return new ArrayList<>(mainCache.values());
    }

    public boolean isEmpty() {
        return mainCache.isEmpty();
    }

    public void clear() {
        mainCache.clear();
    }

    private void putRecordInAllCaches(Record record) {
        mainCache.put(record.getId(), record);
        accountCache.computeIfAbsent(record.getAccount(), k -> new HashSet<>()).add(record.getId());
        nameCache.computeIfAbsent(record.getName(), k -> new HashSet<>()).add(record.getId());
        valueCache.computeIfAbsent(String.valueOf(record.getValue()), k -> new HashSet<>()).add(record.getId());
    }
}
