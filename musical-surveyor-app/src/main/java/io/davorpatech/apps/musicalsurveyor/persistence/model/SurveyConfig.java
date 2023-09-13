package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.fwk.model.BaseValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class SurveyConfig extends BaseValueObject {

    private static final long serialVersionUID = -6232890906214957251L;

    @Column(name = "num_max_participants", nullable = false)
    @NotNull
    @Min(0)
    private Integer numMaxParticipants;

    @Column(name = "num_max_responses", nullable = false)
    @NotNull
    @Min(0)
    private Integer numSurveyResponses;

    @Override
    protected String defineObjAttrs() {
        return String.format("numMaxParticipants=%s, numSurveyResponses=%s",
                numMaxParticipants, numSurveyResponses);
    }

    public Integer getNumMaxParticipants() {
        return numMaxParticipants;
    }

    public void setNumMaxParticipants(Integer numMaxParticipants) {
        this.numMaxParticipants = numMaxParticipants;
    }

    public Integer getNumSurveyResponses() {
        return numSurveyResponses;
    }

    public void setNumSurveyResponses(Integer numSurveyResponses) {
        this.numSurveyResponses = numSurveyResponses;
    }
}
