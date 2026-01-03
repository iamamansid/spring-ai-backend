package com.iamamansid.spring_ai_backend.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocOcrResponse {
    private Double confidence;

    public boolean isGraphCreated() {
        return isGraphCreated;
    }

    public void setGraphCreated(boolean graphCreated) {
        isGraphCreated = graphCreated;
    }

    private boolean isGraphCreated;

    public String getCaptionResult() {
        return captionResult;
    }

    public void setCaptionResult(String captionResult) {
        this.captionResult = captionResult;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getReadResult() {
        return readResult;
    }

    public void setReadResult(String readResult) {
        this.readResult = readResult;
    }

    private String captionResult;
    private String readResult;
}
