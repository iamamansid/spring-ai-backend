package com.iamamansid.spring_ai_backend.Service;

import com.iamamansid.spring_ai_backend.models.response.EvaluationResult;

import java.util.List;
import java.util.Map;

public interface EvaluationService {

    public EvaluationResult evaluateInvoice(
            String invoiceNo,
            Map<String, List<String>> extractedEntities);


}
