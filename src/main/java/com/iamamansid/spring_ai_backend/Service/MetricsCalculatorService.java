package com.iamamansid.spring_ai_backend.Service;

import java.util.List;

public interface MetricsCalculatorService {

    public double entityAccuracy(
            List<String> extracted,
            List<String> groundTruth);

    public double relationCompleteness(
            int extractedRelations,
            int expectedRelations);

    public boolean graphIdempotencyCheck(
            long nodesBefore,
            long nodesAfter);

    public double queryAnswerability(
            int successfulQueries,
            int totalQueries);
}
