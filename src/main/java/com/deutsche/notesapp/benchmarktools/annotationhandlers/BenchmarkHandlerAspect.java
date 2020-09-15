package com.deutsche.notesapp.benchmarktools.annotationhandlers;


import com.deutsche.notesapp.benchmarktools.model.MethodMetric;
import com.deutsche.notesapp.benchmarktools.service.MethodMetricService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
public class BenchmarkHandlerAspect {

    private final MethodMetricService methodMetricService;

    @Autowired
    public BenchmarkHandlerAspect(MethodMetricService methodMetricService) {
        this.methodMetricService = methodMetricService;
    }

    @Pointcut("@annotation(com.deutsche.notesapp.benchmarktools.annotations.Benchmark))")
    public void benchmarkedMethods(){}

    @Pointcut("@within(com.deutsche.notesapp.benchmarktools.annotations.Benchmark))")
    public void benchmarkedClasses(){}

    @Around("benchmarkedMethods() || benchmarkedClasses()")
    public Object benchMarkMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodMetric.MethodMetricBuilder methodMetricBuilder = MethodMetric
                .builder()
                .name(proceedingJoinPoint.getSignature().toLongString());
        try {
            long startTime = System.currentTimeMillis();
            Object result = proceedingJoinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - startTime;
            methodMetricBuilder
                    .duration(timeTaken)
                    .successful(true);
            return result;
        } catch (Throwable ex) {
            String concatenatedStringArgs = Stream.of(proceedingJoinPoint.getArgs())
                    .map(Object::toString)
                    .collect(Collectors.joining("; "));
            methodMetricBuilder
                    .exceptionName(ex.toString())
                    .args(concatenatedStringArgs)
                    .successful(false);
            throw ex;
        } finally {
            methodMetricService.saveMethodMetric(methodMetricBuilder.build());
        }
    }

}
