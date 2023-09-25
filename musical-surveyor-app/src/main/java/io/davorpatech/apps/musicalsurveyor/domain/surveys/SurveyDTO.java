package io.davorpatech.apps.musicalsurveyor.domain.surveys;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Survey DTO class.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior.
 *
 * <p>It is used to transfer projected data between the persistence layer
 * and the service layer. Also, it transfers this aggregated data from the
 * service layer to the presentation layer.
 */
@Schema(
    name = "Survey",
    description = """
        In the context of a radio station, a survey is a set of questions that
        are asked to the listeners in order to get their opinion about their
        musical preferences. Each response is a song that the listener likes.
        """
)
public class SurveyDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 7742176671436306997L;

    @Schema(
        description = "The survey ID",
        example = "1")
    private final Long id;

    @Schema(
        description = "The survey title",
        example = "October Promotion")
    private final String title;

    @Schema(
        description = "The survey description",
        example = "This is the October promotion survey.")
    private final String description;

    @Schema(
        description = "The survey status",
        example = "PENDING")
    private final SurveyStatus status;

    @Schema(
        description = "The survey start date",
        example = "2023-10-01T00:00:00Z")
    private final LocalDateTime startDate;

    @Schema(
        description = "The survey end date",
        example = "2023-10-31T23:59:59Z")
    private final LocalDateTime endDate;

    @Schema(
        description = "The survey participation configuration",
        example = """
            {
                "numMaxParticipants": 50,
                "numNeededResponses": 3
            }""")
    private final SurveyConfigDTO config;

    /**
     * Constructs a new {@link SurveyDTO} with the given arguments.
     *
     * @param id the survey ID
     * @param title the survey title
     * @param description the survey description (optional)
     * @param status the survey status
     * @param startDate the survey start date
     * @param endDate the survey end date
     * @param config the survey participation configuration
     */
    public SurveyDTO(Long id,
                     String title, String description, SurveyStatus status,
                     LocalDateTime startDate, LocalDateTime endDate,
                     SurveyConfigDTO config) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.config = config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyDTO other = (SurveyDTO) o;
        return Objects.equals(id, other.id) &&
            Objects.equals(title, other.title) &&
            Objects.equals(description, other.description) &&
            Objects.equals(status, other.status) &&
            Objects.equals(startDate, other.startDate) &&
            Objects.equals(endDate, other.endDate) &&
            Objects.equals(config, other.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id, title, description, status,
            startDate, endDate, config);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format(
            "id=%s, title='%s', status=%s, startDate='%s', endDate='%s', configuration=%s, description=%s",
            id, title, status, startDate, endDate,
            config == null ? null : '{' + config.defineObjAttrs() + '}',
            description == null ? null : '\'' + description + '\'');
    }

    /**
     * Returns the survey ID.
     *
     * @return the survey ID
     */
    @Override
    public Long getId() {
        return id;
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
     * Returns the survey status.
     *
     * @return the survey status
     */
    public SurveyStatus getStatus() {
        return status;
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
        return config;
    }
}
