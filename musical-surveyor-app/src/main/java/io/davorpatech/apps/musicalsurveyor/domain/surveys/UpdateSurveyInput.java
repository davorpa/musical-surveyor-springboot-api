package io.davorpatech.apps.musicalsurveyor.domain.surveys;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseUpdateInputCmd;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Input object for updating an existing {@code Survey}.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the presentation layer and the services layer. It is
 * also used to validate the data sent to the services layer. It is a
 * good practice to use different DTOs for each one of service operations.
 * This way, the DTOs can be extended in the future without breaking the
 * service contract.
 *
 * <p>This is why the {@link CreateSurveyInput} and the {@link UpdateSurveyInput}
 * are different classes. They both represent the same data, but the
 * {@link UpdateSurveyInput} has an additional {@code id} field. This is
 * because the {@code id} is required to update an existing {@code Survey}.
 * The {@code id} is not required to create a new {@code Survey} because
 * the server will generate a new {@code id} for the new {@code Survey}.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class UpdateSurveyInput extends BaseUpdateInputCmd<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 7898289168606491592L;

    @NotBlank
    @Size(max = SurveyConstants.TITLE_MAXLEN)
    private final String title;

    @Size(max = SurveyConstants.DESCRIPTION_MAXLEN)
    private final String description;

    @NotNull
    private final LocalDateTime startDate;

    @NotNull
    private final LocalDateTime endDate;

    @Valid
    private final SurveyConfigDTO config;

    /**
     * Constructs a new {@link UpdateSurveyInput} with the given arguments.
     *
     * @param id          the survey ID
     * @param title       the survey title
     * @param description the survey description (optional)
     * @param startDate   the survey start date
     * @param endDate     the survey end date
     * @param config      the survey participation configuration (optional)
     */
    public UpdateSurveyInput(Long id,
                             String title, String description,
                             LocalDateTime startDate, LocalDateTime endDate,
                             SurveyConfigDTO config) {
        super(id);
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
        if (!super.equals(o)) return false;
        UpdateSurveyInput other = (UpdateSurveyInput) o;
        return Objects.equals(title, other.title) &&
            Objects.equals(description, other.description) &&
            Objects.equals(startDate, other.startDate) &&
            Objects.equals(endDate, other.endDate) &&
            Objects.equals(config, other.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
            title, description, startDate, endDate, config);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, title='%s', startDate='%s', endDate='%s', config=%s, description=%s",
            super.defineObjAttrs(), title, startDate, endDate,
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
        return config;
    }
}
