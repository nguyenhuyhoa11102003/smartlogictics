package com.tdtu.logistics_identity_service.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tdtu.logistics_identity_service.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RedisCacheServiceImpl implements RedisService {

    final RedisTemplate<String, Object> redisTemplate;
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // ------------------ General Methods ------------------
    @Override
    public void setCache(String key, Object value, long ttlSeconds) {
        redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
    }

    @Override
    public <T> T getCache(String key, Class<T> clazz) {
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject == null) {
            return null;
        }
        return objectMapper.convertValue(cachedObject, clazz);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }
    public long getExpiration(String key) {
        Long expiration = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expiration != null ? expiration : -1;
    }

    public void setExpiration(String key, long ttlSeconds) {
        redisTemplate.expire(key, ttlSeconds, TimeUnit.SECONDS);
    }

    // ------------------ List Methods ------------------
    public void addToList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public Object popFromList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public long getListSize(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return size != null ? size : 0;
    }

    public void removeFromList(String key, Object value) {
        redisTemplate.opsForList().remove(key, 1, value);
    }

    // ------------------ Set Methods ------------------
    public void addToSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public boolean isMemberOfSet(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    public void removeFromSet(String key, Object value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    // ------------------ Hash Methods ------------------
    public void putToHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getFromHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Map<Object, Object> getAllFromHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void removeFromHash(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public boolean hasHashKey(String key, String hashKey) {
        return Boolean.TRUE.equals(redisTemplate.opsForHash().hasKey(key, hashKey));
    }

    public long getHashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    // ------------------ Sorted Set Methods ------------------
    public void addToSortedSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> getSortedSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    public void removeFromSortedSet(String key, Object value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    public long getSortedSetSize(String key) {
        Long size = redisTemplate.opsForZSet().size(key);
        return size != null ? size : 0;
    }

    public Set<Object> getSortedSetByScore(String key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore);
    }


}
