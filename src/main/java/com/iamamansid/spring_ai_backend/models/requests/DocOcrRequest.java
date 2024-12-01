package com.iamamansid.spring_ai_backend.models.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
public class DocOcrRequest {
    MultipartFile document;
    String VerificationData;
    JSONArray array;
}
