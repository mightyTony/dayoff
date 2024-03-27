package kr.tony.dayoff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching // 레디스 캐싱 위해 추가, @Cacheable 어노테이션 인식
public class DayoffApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayoffApplication.class, args);
    }

}
