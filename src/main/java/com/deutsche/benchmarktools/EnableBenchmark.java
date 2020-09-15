package com.deutsche.benchmarktools;

import com.deutsche.benchmarktools.annotationhandlers.BenchmarkHandlerAspect;
import com.deutsche.benchmarktools.dao.MethodMetricRepo;
import com.deutsche.benchmarktools.service.MethodMetricService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableAspectJAutoProxy
@Import(BenchmarkToolsRegistrar.class)
public @interface EnableBenchmark {
}
