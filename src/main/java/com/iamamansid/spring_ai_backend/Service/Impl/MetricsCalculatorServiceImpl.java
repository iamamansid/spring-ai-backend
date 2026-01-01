package com.iamamansid.spring_ai_backend.Service.Impl;

import com.iamamansid.spring_ai_backend.Service.MetricsCalculatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricsCalculatorServiceImpl implements MetricsCalculatorService {

    public double entityAccuracy(
            List<String> extracted,
            List<String> groundTruth) {

        long correct = extracted.stream()
                .filter(groundTruth::contains)
                .count();

        return (double) correct / groundTruth.size();
    }

    public double relationCompleteness(
            int extractedRelations,
            int expectedRelations) {

        return Math.min(1.0, (double)extractedRelations / expectedRelations);
    }

    public boolean graphIdempotencyCheck(
            long nodesBefore,
            long nodesAfter) {

        return nodesBefore == nodesAfter;
    }

    public double queryAnswerability(
            int successfulQueries,
            int totalQueries) {

        return (double) successfulQueries / totalQueries;
    }
}
