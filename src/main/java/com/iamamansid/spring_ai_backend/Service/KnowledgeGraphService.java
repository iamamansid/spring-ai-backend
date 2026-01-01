package com.iamamansid.spring_ai_backend.Service;

import java.util.List;
import java.util.Map;

public interface KnowledgeGraphService {

    public void createInvoiceGraph(Map<String, List<String>> entities);
}
