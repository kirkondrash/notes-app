package com.deutsche.notesapp.benchmarktools.service;

import com.deutsche.notesapp.benchmarktools.dao.MethodMetricRepo;
import com.deutsche.notesapp.benchmarktools.model.MethodMetric;
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
