package com.iamamansid.spring_ai_backend.Service;

import java.util.List;
import java.util.Map;

public interface KnowledgeGraphService {

    public void createInvoiceGraph(Map<String, List<String>> entities);

    public boolean executeTestQuery(String companyName);

    public long getTotalNodeCount(String invoiceNo);

    public boolean checkIdempotency(
            String invoiceNo,
            Runnable reIngestOperation);

    public boolean queryInvoiceByCompany(String companyName);

    public boolean queryInvoiceByAmount(String amount);

    public boolean queryInvoiceExists(String invoiceNo);
}
