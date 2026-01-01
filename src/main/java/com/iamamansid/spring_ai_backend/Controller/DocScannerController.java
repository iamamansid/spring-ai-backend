package com.iamamansid.spring_ai_backend.Controller;



import com.iamamansid.spring_ai_backend.Service.DocumentService;
import com.iamamansid.spring_ai_backend.models.response.ApiResponse;
import com.iamamansid.spring_ai_backend.models.response.DocOcrResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/webapp/v0")
public class DocScannerController {

    @Autowired
    DocumentService documentService;

    private static final Logger logger = LoggerFactory.getLogger(DocScannerController.class);

    @PostMapping(value = "/getDocScanned", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> scanDocData(@RequestParam(value = "document", required = true)MultipartFile document) {
        ApiResponse response = new ApiResponse();
        try {
            DocOcrResponse responseString = documentService.scanDocOcr(document);
            if (responseString != null) {
                response.setResponse(responseString);
                response.setCode(200);
                response.setResult(true);
            } else {
                response.setCode(500);
                response.setResult(false);
            }
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (Exception e) {
            logger.error("Error processing OCR request: {}", e.getMessage(), e);
            response.setCode(500);
            response.setResult(false);
            response.setResponse("An unexpected error occurred while processing the document.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
