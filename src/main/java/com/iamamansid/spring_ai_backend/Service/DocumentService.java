package com.iamamansid.spring_ai_backend.Service;

import com.iamamansid.spring_ai_backend.models.requests.Message;
import com.iamamansid.spring_ai_backend.models.requests.Messages;
import com.iamamansid.spring_ai_backend.models.response.ApiResponse;
import com.iamamansid.spring_ai_backend.models.response.DocOcrResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    DocOcrResponse scanDocOcr(MultipartFile document) throws Exception;

    String callChatBot(Messages messages) throws Exception;
}
