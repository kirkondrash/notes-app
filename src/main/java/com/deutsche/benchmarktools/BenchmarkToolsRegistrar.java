package com.deutsche.benchmarktools;

import com.deutsche.benchmarktools.dao.MethodMetricRepo;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

public class BenchmarkToolsRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setAbstract(false);
        beanDefinition.setBeanClass(MethodMetricRepo.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("path", "metrics");
        beanDefinition.setPropertyValues(propertyValues);

        registry.registerBeanDefinition(ClassUtils.getShortName(getClass()), beanDefinition);
    }
}
