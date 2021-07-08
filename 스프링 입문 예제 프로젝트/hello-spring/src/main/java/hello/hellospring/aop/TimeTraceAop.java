package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP 사용하려면 @Aspect 어노테이션이 필요함.
 * @Component 어노테이션을 붙이거나 SpringConfig에 @Bean으로 등록해줘야함. 직접 등록해주는게 더 가시적이라 선호되긴함.
 */
@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))")    // hello.hellospring 하위에 다 적용시켜라. 적용 시킬 대상을 내가 정할 수 있음
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
