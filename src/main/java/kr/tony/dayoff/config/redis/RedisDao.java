package kr.tony.dayoff.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisDao {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 값 저장
     * @param key : email
     * @param refreshToken
     * @param refreshTokenTime
     */
    public void setRefreshToken(String key, String refreshToken, long refreshTokenTime) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(refreshToken.getClass())); // 리프레시 토큰 직렬화 하는 코드, 직렬화 해서 데이터 양 줄임
        redisTemplate.opsForValue().set(key, refreshToken, refreshTokenTime);
        //opsForValue(): RedisTemplate에서 ValueOperations를 가져옵니다.
        //ValueOperations 객체를 통해 Redis의 값을 저장, 조회, 삭제하는 작업을 수행합니다.
    }

    /**
     * 키로 값 조회
     * @param key : email
     * @return
     */
    public String getRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 키로 값 삭제
     * @param key : email
     */
    public void deleteRefreshToken(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 키 존재 유무 확인
     * @param key : email
     * @return
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setBlackList(String accessToken, String msg, Long minutes) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(msg.getClass()));
        redisTemplate.opsForValue().set(accessToken, msg, minutes, TimeUnit.MINUTES);
    }

    public String getBlackList(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    // 레디스 모든 데이터 삭제
    public void flushAll() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }
}
