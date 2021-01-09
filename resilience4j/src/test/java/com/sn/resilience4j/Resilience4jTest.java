package com.sn.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Resilience4jTest {

    @Test
    public void test1() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                // 故障率阈值百分比，超过这个阈值，断路器机会打开
                .failureRateThreshold(50)
                // 设置断路器打开的时间，达到设置的时间后，断路器会进入到 half open 状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                // 断路器处于半关闭状态时的，环形缓冲区大小
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();

        CircuitBreaker circuitBreaker = CircuitBreaker.of("sn", config);
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> "hello");
        Try<String> result = Try.of(supplier).map(v -> v + " world");
        System.out.println(result.isSuccess());
        System.out.println(result.get());
    }

    // 断路器
    @Test
    public void test2() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                // 故障率阈值百分比，超过这个阈值，断路器机会打开
                .failureRateThreshold(50)
                // 设置断路器打开的时间，达到设置的时间后，断路器会进入到 half open 状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                // 断路器处于关闭状态时的，环形缓冲区大小，有两条异常数据时开始统计故障率
                .ringBufferSizeInClosedState(2)
                .build();

        CircuitBreaker circuitBreaker = CircuitBreaker.of("sn-breaker", config);

        System.out.println(circuitBreaker.getState());

        // 制造两个异常，打开断路器
        circuitBreaker.onError(0, TimeUnit.MILLISECONDS, new RuntimeException());
        System.out.println(circuitBreaker.getState());
        circuitBreaker.onError(0, TimeUnit.MILLISECONDS, new RuntimeException());
        System.out.println(circuitBreaker.getState());

        // 断路器已经打开，后续请求不会继续执行
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> "hello");
        Try<String> result = Try.of(supplier).map(v -> v + " world");
        System.out.println(result.isSuccess());
        System.out.println(result.get());

        // 重置断路器
//        circuitBreaker.reset();
    }

    // 限流
    @Test
    public void test3() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                // 限流后，后续请求的等待时间
                .timeoutDuration(Duration.ofSeconds(2))
                // 每个时间段处理请求的个数
                .limitForPeriod(2)
                // 设置上边的时间段，不要大于timeoutDuration
                .limitRefreshPeriod(Duration.ofSeconds(2))
                .build();

        RateLimiter rateLimiter = RateLimiter.of("sn-limiter", config);

        CheckedRunnable runnable = RateLimiter.decorateCheckedRunnable(rateLimiter, () -> {
            System.out.println(new Date());
        });

        Try.run(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .onFailure(t -> System.out.println(t.getMessage()));
    }

    // 请求重试
    @Test
    public void test4() {
        RetryConfig config = RetryConfig.custom()
                // 最大重试次数
                .maxAttempts(5)
                // 重试的间隔时间
                .waitDuration(Duration.ofSeconds(1))
                // 发生那些异常进行重试
                .retryExceptions(RuntimeException.class)
                // 忽略那些异常
//                .ignoreExceptions()
                .build();

        Retry retry = Retry.of("sn-retry", config);
        Retry.decorateRunnable(retry, new Runnable() {
            int count = 0;

            @Override
            public void run() {
                count++;
                if (count < 3) {
                    System.out.println("exception");
                    throw new RuntimeException();
                } else {
                    System.out.println("success");
                }
            }
        }).run();
    }
}
