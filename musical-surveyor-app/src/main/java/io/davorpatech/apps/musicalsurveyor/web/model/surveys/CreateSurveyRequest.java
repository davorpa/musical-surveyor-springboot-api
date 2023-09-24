package io.davorpatech.apps.musicalsurveyor.web.model.surveys;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.davorpatech.apps.musicalsurveyor.domain.surveys.SurveyConfigDTO;
import io.davorpatech.apps.musicalsurveyor.domain.surveys.SurveyConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the HTTP request body to create a new {@code Survey}.
 *
 * <p>Request DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the client and the server. It is also used to validate
 * the data sent to the server. It is a good practice to use different
 * DTOs for the request and the response. This way, the response DTO
 * can be extended in the future without breaking the client. This
 * is not the case for the request DTO, which should be kept as simple
 * as possible.
 *
 * <p>This is why the {@link CreateSurveyRequest} and the
 * {@link CreateSurveyRequest} are different classes. They both
 * represent the same data, but the {@link CreateSurveyRequest} has
 * an additional {@code id} field. This is because the {@code id} is
 * required to update an existing {@code Survey}. The {@code id} is
 * not required to create a new {@code Survey} because the server
 * will generate a new {@code id} for the new {@code Survey}.
 */
@Schema(
    name = "CreateSurveyRequest",
    description = "Represents the HTTP request body to create a new Survey."
)
public class CreateSurveyRequest extends BaseValueObject // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 2284771029287974311L;

    @Schema(
        description = "The survey title",
        example = "Survey about top love songs")
    @NotBlank
    @Size(max = SurveyConstants.TITLE_MAXLEN)
    private final String title;

    @Schema(
        description = "The survey description",
        example = "This survey is about the top love songs of all time")
    @Size(max = SurveyConstants.DESCRIPTION_MAXLEN)
    private final String description;

    @Schema(
        description = "The survey start date",
        example = "2023-09-25T00:00:00Z")
    @NotNull
    private final LocalDateTime startDate;

    @Schema(
        description = "The survey end date",
        example = "2023-09-30T23:59:59Z")
    @NotNull
    private final LocalDateTime endDate;

    @Schema(
        description = "The survey participation configuration",
        example = """
            {
                "numMaxParticipants": 50,
                "numNeededResponses": 3
            }""")
    @Valid
    private final SurveyConfigDTO config;

    /**
     * Constructs a new {@link CreateSurveyRequest} with the given arguments.
     *
     * @param title       the survey title
     * @param description the survey description (optional)
     * @param startDate   the survey start date
     * @param endDate     the survey end date
     * @param config      the survey participation configuration (optional)
     */
    @JsonCreator
    public CreateSurveyRequest(String title, String description,
                               LocalDateTime startDate, LocalDateTime endDate,
                               SurveyConfigDTO config) {
        super();
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.config = config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateSurveyRequest other = (CreateSurveyRequest) o;
        return Objects.equals(title, other.title) &&
            Objects.equals(description, other.description) &&
            Objects.equals(startDate, other.startDate) &&
            Objects.equals(endDate, other.endDate) &&
            Objects.equals(config, other.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, startDate, endDate, config);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("title='%s', startDate='%s', endDate='%s', config=%s, description=%s",
            title, startDate, endDate,
            config == null ? null : '{' + config.defineObjAttrs() + '}',
            description == null ? null : '\'' + description + '\'');
    }

    /**
     * Returns the survey title.
     *
     * @return the survey title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the survey description.
     *
     * @return the survey description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the survey start date.
     *
     * @return the survey start date
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Returns the survey end date.
     *
     * @return the survey end date
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Returns the survey participation configuration.
     *
     * @return the survey participation configuration
     */
    public SurveyConfigDTO getConfig() {
        // return an immutable object copying the original config
        return Optional.ofNullable(config)
            .map(value -> new SurveyConfigDTO(
                value.getNumMaxParticipants(),
                value.getNumNeededResponses()
            ))
            .orElse(null);
    }
}
