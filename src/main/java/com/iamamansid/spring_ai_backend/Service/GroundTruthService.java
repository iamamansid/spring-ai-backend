package com.iamamansid.spring_ai_backend.Service;

import com.iamamansid.spring_ai_backend.models.response.GroundTruthInvoice;

public interface GroundTruthService {

    public GroundTruthInvoice loadGroundTruth(String invoiceNo);
}
