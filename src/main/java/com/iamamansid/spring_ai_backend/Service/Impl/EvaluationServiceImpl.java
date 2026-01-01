package com.iamamansid.spring_ai_backend.Service.Impl;

import com.iamamansid.spring_ai_backend.Service.EvaluationService;
import com.iamamansid.spring_ai_backend.Service.GroundTruthService;
import com.iamamansid.spring_ai_backend.Service.KnowledgeGraphService;
import com.iamamansid.spring_ai_backend.Service.MetricsCalculatorService;
import com.iamamansid.spring_ai_backend.models.response.EvaluationResult;
import com.iamamansid.spring_ai_backend.models.response.GroundTruthInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    GroundTruthService groundTruthService;
    @Autowired
    MetricsCalculatorService metricsCalculator;
    @Autowired
    KnowledgeGraphService kgService;

    public EvaluationResult evaluateInvoice(
            String invoiceNo,
            Map<String, List<String>> extractedEntities) {

        GroundTruthInvoice gt =
                groundTruthService.loadGroundTruth(invoiceNo);

        double dateAccuracy = metricsCalculator.entityAccuracy(
                extractedEntities.get("DATE"), gt.getDates());

        double amountAccuracy = metricsCalculator.entityAccuracy(
                extractedEntities.get("AMOUNT"), gt.getAmounts());

        double companyAccuracy = metricsCalculator.entityAccuracy(
                extractedEntities.get("COMPANY"), gt.getCompanies());

        double entityAccuracy =
                (dateAccuracy + amountAccuracy + companyAccuracy) / 3;

        // Relations
        int expectedRelations = gt.getDates().size()
                + gt.getAmounts().size()
                + gt.getCompanies().size();

        int extractedRelations =
                extractedEntities.get("DATE").size()
                        + extractedEntities.get("AMOUNT").size()
                        + extractedEntities.get("COMPANY").size();

        double relationCompleteness =
                metricsCalculator.relationCompleteness(
                        extractedRelations, expectedRelations);

        return new EvaluationResult(
                entityAccuracy,
                relationCompleteness
        );
    }
}
