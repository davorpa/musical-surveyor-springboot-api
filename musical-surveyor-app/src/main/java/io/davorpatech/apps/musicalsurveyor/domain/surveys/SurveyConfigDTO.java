package io.davorpatech.apps.musicalsurveyor.domain.surveys;

import io.davorpatech.fwk.model.BaseValueObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

import java.io.Serial;
import java.util.Objects;

/**
 * The SurveyConfig DTO class.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior.
 *
 * <p>It is used to transfer projected data between the persistence layer
 * and the service layer. Also, it transfers this aggregated data from the
 * service layer to the presentation layer.
 */
@Schema(
    name = "SurveyConfig",
    description = """
        It holds the configuration parameters of a survey, such as the 
        maximum number of participants allowed or the number of responses
        that the survey will have.
        """
)
public class SurveyConfigDTO extends BaseValueObject // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -1307246035163005928L;

    @Schema(
        description = "The maximum number of participants allowed in the survey",
        example = "" + SurveyConstants.CFG_NUM_MAX_PARTICIPANTS_DEFAULT)
    @Min(SurveyConstants.CFG_NUM_MAX_PARTICIPANTS_MIN)
    private final Integer numMaxParticipants;

    @Schema(
        description = "The number of responses that the survey will have",
        example = "" + SurveyConstants.CFG_NUM_RESPONSES_DEFAULT)
    @Min(SurveyConstants.CFG_NUM_RESPONSES_MIN)
    private final Integer numNeededResponses;

    /**
     * Constructs a new {@link SurveyConfigDTO} with the given arguments.
     *
     * @param numMaxParticipants the maximum number of participants allowed in the survey
     * @param numNeededResponses the number of responses that the survey will have
     */
    public SurveyConfigDTO(Integer numMaxParticipants, Integer numNeededResponses) {
        super();
        this.numMaxParticipants = numMaxParticipants;
        this.numNeededResponses = numNeededResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyConfigDTO other = (SurveyConfigDTO) o;
        return Objects.equals(numMaxParticipants, other.numMaxParticipants) &&
            Objects.equals(numNeededResponses, other.numNeededResponses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numMaxParticipants, numNeededResponses);
    }

    @Override
    public String defineObjAttrs() {
        return String.format("numMaxParticipants=%s, numNeededResponses=%s",
            numMaxParticipants, numNeededResponses);
    }

    /**
     * Gets the maximum number of participants allowed in the survey.
     *
     * <p>The default value is {@code 50}. It can be changed by the administrator
     * of the radio station when creating a new survey.
     *
     * @return the maximum number of participants allowed in the survey,
     *         never {@code null}
     */
    public Integer getNumMaxParticipants() {
        return numMaxParticipants;
    }

    /**
     * Gets the number of responses that the survey will have.
     *
     * <p>The default value is {@code 3}. It can be changed by the administrator
     * of the radio station when creating a new survey.
     *
     * @return the number of responses that the survey will have,
     *         never {@code null}
     */
    public Integer getNumNeededResponses() {
        return numNeededResponses;
    }
}
