package com.iamamansid.spring_ai_backend.Service.Impl;

import ch.qos.logback.classic.Logger;
import com.iamamansid.spring_ai_backend.Service.DocumentService;
import com.iamamansid.spring_ai_backend.Service.EntityExtractionService;
import com.iamamansid.spring_ai_backend.Service.KnowledgeGraphService;
import com.iamamansid.spring_ai_backend.models.response.ApiResponse;
import com.iamamansid.spring_ai_backend.models.response.DocOcrResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class DocumentServiceImpl implements DocumentService {


    @Value("${AmanOpenAI.APIKey}")
    String apiKey;

    private final RestTemplate restTemplate;

    @Value("${AmanOpenAI.Endpoint}")
    String endpoint;

    @Autowired
    EntityExtractionService entityExtractionService;

    @Autowired
    KnowledgeGraphService knowledgeGraphService;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(DocumentServiceImpl.class);


    public DocumentServiceImpl( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    @Override
    @Cacheable(value = "ocrResponses", key = "#document.originalFilename")
    @Async
    public DocOcrResponse scanDocOcr(MultipartFile document) throws Exception {

        String url = endpoint + "/computervision/imageanalysis:analyze?features=caption,read&model-version=latest&language=en&api-version=2024-02-01";
        String ApiResponseString="";
        ResponseEntity<Map> response = null;
        ApiResponse apiResponse;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("Ocp-Apim-Subscription-Key", apiKey);
        Map<String, List<String>> res;

        try {
            HttpEntity<byte[]> entity = new HttpEntity<>(document.getBytes(), headers);

            response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        } catch (Exception ex) {
            logger.error("Error while calling Azure OCR API", ex);
            throw new RuntimeException("OCR Processing Failed: " + ex.getMessage());
        }
        DocOcrResponse response1 = null;
        if (response!=null){
           response1  = parseOcrResponse(response.getBody());
            try {
                res = entityExtractionService.extractEntities(response1.getReadResult());
                knowledgeGraphService.createInvoiceGraph(res);
            } catch (Exception ex) {
                logger.error("Error while retrieving entities or writing to Neo4j:", ex);
                throw new Exception("Error while retrieving entities or writing to Neo4j: "+ex.getMessage());
            }
        }
        return response1;
    }

    public DocOcrResponse parseOcrResponse(Map<String, Object> responseBody) {
        DocOcrResponse ocrResponse = new DocOcrResponse();

        // Extract caption result
        Map<String, Object> captionResult = (Map<String, Object>) responseBody.get("captionResult");
        if (captionResult != null) {
            ocrResponse.setCaptionResult((String) captionResult.get("text"));
            ocrResponse.setConfidence((Double) captionResult.get("confidence"));
        }

        // Extract read result
        StringBuilder extractedText = new StringBuilder();
        Map<String, Object> readResult = (Map<String, Object>) responseBody.get("readResult");
        if (readResult != null) {
            List<Map<String, Object>> blocks = (List<Map<String, Object>>) readResult.get("blocks");
            if (blocks != null) {
                for (Map<String, Object> block : blocks) {
                    List<Map<String, Object>> lines = (List<Map<String, Object>>) block.get("lines");
                    if (lines != null) {
                        for (Map<String, Object> line : lines) {
                            List<Map<String, Object>> words = (List<Map<String, Object>>) line.get("words");
                            if (words != null) {
                                for (Map<String, Object> word : words) {
                                    extractedText.append(word.get("text")).append(" ");
                                }
                            }
                            extractedText.append("\n");
                        }
                    }
                }
            }
        }
        ocrResponse.setReadResult(extractedText.toString());

        return ocrResponse;
    }
}
