package backend.newssseuk;

import backend.newssseuk.springbootmongodb.Exception.CustomAsyncExceptionHandler;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class NewsSseukApplication implements AsyncConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(NewsSseukApplication.class, args);
    }

    // 어플리케이션 레벨로 실행자 오버라이드하기
    @Bean(name="executor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(6); // 스레드 개수 6개로 지정
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(500);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
