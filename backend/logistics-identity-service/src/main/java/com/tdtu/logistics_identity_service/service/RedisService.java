package com.tdtu.logistics_identity_service.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    // ------------------ General Methods ------------------
    void setCache(String key, Object value, long ttlSeconds);

    <T> T getCache(String key, Class<T> clazz);

    boolean hasKey(String key);

    void deleteCache(String key);

    long getExpiration(String key);

    void setExpiration(String key, long ttlSeconds);

    // ------------------ List Methods ------------------
    void addToList(String key, Object value);

    List<Object> getList(String key);

    Object popFromList(String key);

    long getListSize(String key);

    void removeFromList(String key, Object value);

    // ------------------ Set Methods ------------------
    void addToSet(String key, Object value);

    Set<Object> getSet(String key);

    boolean isMemberOfSet(String key, Object value);

    void removeFromSet(String key, Object value);

    // ------------------ Hash Methods ------------------
    void putToHash(String key, String hashKey, Object value);

    Object getFromHash(String key, String hashKey);

    Map<Object, Object> getAllFromHash(String key);

    void removeFromHash(String key, String hashKey);

    boolean hasHashKey(String key, String hashKey);

    long getHashSize(String key);

    // ------------------ Sorted Set Methods ------------------
    void addToSortedSet(String key, Object value, double score);

    Set<Object> getSortedSet(String key);

    void removeFromSortedSet(String key, Object value);

    long getSortedSetSize(String key);

    Set<Object> getSortedSetByScore(String key, double minScore, double maxScore);
}
