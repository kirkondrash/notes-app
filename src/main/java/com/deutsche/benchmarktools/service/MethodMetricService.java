package com.deutsche.benchmarktools.service;

import com.deutsche.benchmarktools.dao.MethodMetricRepo;
import com.deutsche.benchmarktools.model.MethodMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MethodMetricService {

    private final MethodMetricRepo methodMetricRepo;

    @Autowired
    public MethodMetricService(MethodMetricRepo methodMetricRepo) {
        this.methodMetricRepo = methodMetricRepo;
    }

    public void saveMethodMetric(MethodMetric methodMetric){
        methodMetricRepo.save(methodMetric);
    }
}
