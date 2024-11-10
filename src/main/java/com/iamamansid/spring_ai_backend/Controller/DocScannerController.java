package com.iamamansid.spring_ai_backend.Controller;



import com.iamamansid.spring_ai_backend.Service.DocumentService;
import com.iamamansid.spring_ai_backend.models.response.ApiResponse;
import com.iamamansid.spring_ai_backend.models.response.DocOcrResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin()
@RequestMapping("/api/webapp/v0")
public class DocScannerController {

    @Autowired
    DocumentService documentService;

    @PostMapping(value = "/getDocScanned")
    public ResponseEntity<ApiResponse> scanDocData(@RequestParam(value = "document", required = true)MultipartFile document) {
        DocOcrResponse responseString = documentService.scanDocOcr(document);
        ApiResponse response = new ApiResponse();
        if (responseString!=null) {
            response.setResponse(responseString);
            response.setCode(200);
            response.setResult(true);
        } else {
            response.setCode(500);
            response.setResult(false);
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
