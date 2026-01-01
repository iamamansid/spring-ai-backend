package com.iamamansid.spring_ai_backend.Service;

import java.util.List;
import java.util.Map;

public interface EntityExtractionService {

    public Map<String, List<String>> extractEntities(String text);
}
