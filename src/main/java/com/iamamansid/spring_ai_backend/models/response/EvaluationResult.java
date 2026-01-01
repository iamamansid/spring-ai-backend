package com.iamamansid.spring_ai_backend.models.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EvaluationResult {

    public EvaluationResult(double entityAccuracy, double relationCompleteness, boolean graphIdempotent, double queryAnswerability) {
        this.entityAccuracy = entityAccuracy;
        this.relationCompleteness = relationCompleteness;
        this.graphIdempotent = graphIdempotent;
        this.queryAnswerability = queryAnswerability;
    }

    public EvaluationResult(double entityAccuracy, double relationCompleteness) {
        this.entityAccuracy = entityAccuracy;
        this.relationCompleteness = relationCompleteness;
    }

    private double entityAccuracy;

    public double getEntityAccuracy() {
        return entityAccuracy;
    }

    public void setEntityAccuracy(double entityAccuracy) {
        this.entityAccuracy = entityAccuracy;
    }

    public double getRelationCompleteness() {
        return relationCompleteness;
    }

    public void setRelationCompleteness(double relationCompleteness) {
        this.relationCompleteness = relationCompleteness;
    }

    public boolean isGraphIdempotent() {
        return graphIdempotent;
    }

    public void setGraphIdempotent(boolean graphIdempotent) {
        this.graphIdempotent = graphIdempotent;
    }

    public double getQueryAnswerability() {
        return queryAnswerability;
    }

    public void setQueryAnswerability(double queryAnswerability) {
        this.queryAnswerability = queryAnswerability;
    }

    private double relationCompleteness;
    private boolean graphIdempotent;
    private double queryAnswerability;

}
