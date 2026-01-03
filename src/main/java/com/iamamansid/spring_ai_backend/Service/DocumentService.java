package com.iamamansid.spring_ai_backend.Service;

import com.iamamansid.spring_ai_backend.models.response.ApiResponse;
import com.iamamansid.spring_ai_backend.models.response.DocOcrResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    DocOcrResponse scanDocOcr(MultipartFile document) throws Exception;
}
