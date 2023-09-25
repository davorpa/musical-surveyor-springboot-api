package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.domain.surveys.*;
import io.davorpatech.apps.musicalsurveyor.services.surveys.SurveyService;
import io.davorpatech.apps.musicalsurveyor.web.model.surveys.CreateSurveyRequest;
import io.davorpatech.apps.musicalsurveyor.web.model.surveys.UpdateSurveyRequest;
import io.davorpatech.fwk.exception.NoMatchingRelatedFieldsException;
import io.davorpatech.fwk.model.PagedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

/**
 * REST controller for managing {@code Survey} resources.
 *
 * <p>As a controller, it is a stateless class that provides operations
 * to manage {@code Survey} data domain. It exposes endpoints that
 * handle HTTP requests such as GET, POST, PUT, and DELETE. It also
 * provides a way to map request parameters to the method arguments
 * using the {@link RequestParam @RequestParam} annotation. It also
 * provides a way to map request body to the method arguments using the
 * {@link RequestBody @RequestBody} annotation. It also provides a way
 * to map request path variables to the method arguments using the
 * {@link PathVariable @PathVariable} annotation.
 *
 * <p>Controllers are the entry point to the presentation layer. They are
 * responsible for handling the HTTP requests, and they delegate the
 * business logic to the services.
 */
@Tag(
    name = "survey",
    description = """
        The Surveys API.
        
        Provides REST operations to manage Survey resources."""
)
@RestController
@RequestMapping("/api/surveys")
public class SurveyController // NOSONAR
{
    private final SurveyService surveyService;

    /**
     * Constructs a new {@link SurveyController} with the given arguments.
     *
     * @param surveyService the survey service, never {@code null}
     */
    SurveyController(SurveyService surveyService) {
        Assert.notNull(surveyService, "SurveyService must not be null!");
        this.surveyService = surveyService;
    }

    /**
     * Finds all {@code Survey} resources.
     *
     * @param pageable     the page request, never {@code null}
     * @param forceUnpaged whether to force an unpaged result
     * @param status       the status of the survey used as a filter (optional)
     * @return all {@code Survey} resources
     */
    @Operation(
        summary = "Finds all surveys",
        description = """
            Finds all surveys.
            
            The results can be paginated and sorted
            (sorted by default by start date, and end date)""",
        tags = { "survey" }
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful operation",
        useReturnTypeSchema = true
    )
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @GetMapping
    PagedResult<SurveyDTO> findAll(
        @ParameterObject
        @SortDefault(sort = "startDate", direction = Sort.Direction.DESC)
        @SortDefault(sort = "endDate", direction = Sort.Direction.ASC)
        Pageable pageable,
        @RequestParam(value = "unpaged", defaultValue = "false") boolean forceUnpaged,
        @RequestParam(required = false) SurveyStatus status)
    {
        FindSurveysInput input = new FindSurveysInput(
            forceUnpaged || pageable.isUnpaged() ?  0 : pageable.getPageNumber(),
            forceUnpaged || pageable.isUnpaged() ? -1 : pageable.getPageSize(),
            pageable.getSort(),
            status
        );
        return surveyService.findAll(input);
    }

    @Operation(
        summary = "Retrieves a survey detail by ID",
        description = """
            Retrieves the survey detail given its identifier.
            
            The identifier is a numeric value.""",
        tags = { "survey" }
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful operation",
        useReturnTypeSchema = true
    )
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content
    )
    @ApiResponse(
        responseCode = "404",
        description = "Survey not found",
        content = @Content
    )
    @GetMapping("/{id}")
    ResponseEntity<SurveyDTO> retrieveById(
        @Parameter(description = "The identifier of the survey to be retrieved", example = "1")
        @PathVariable("id") Long id)
    {
        SurveyDTO dto = surveyService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Creates a new {@code Survey} resource using the given request body.
     *
     * @param request the request body, containing the parameters
     * @return the created {@code Survey} resource
     */
    @Operation(
        summary = "Creates a new survey",
        description = """
            Creates a new survey.
            
            The request body must contain the survey parameters.""",
        tags = { "survey" }
    )
    @ApiResponse(
        responseCode = "201",
        description = "Successful operation",
        useReturnTypeSchema = true)
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @ApiResponse(
        responseCode = "412",
        description = "Survey cannot be created due to business rules",
        content = @Content)
    @PostMapping
    ResponseEntity<SurveyDTO> create(
        @RequestBody @Validated CreateSurveyRequest request)
    {
        CreateSurveyInput input = new CreateSurveyInput(
            request.getTitle(), request.getDescription(),
            request.getStartDate(), request.getEndDate(),
            request.getConfig());
        SurveyDTO dto = surveyService.create(input);

        // Compose URI Location of the retrieve endpoint for this created resource
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
        // build response entity providing URI and body of the created resource
        return ResponseEntity.created(location).body(dto);
    }

    /**
     * Updates an existing {@code Survey} resource using the given request body.
     *
     * @param id      the identifier of the survey to be updated
     * @param request the request body, containing the parameters
     * @return the updated {@code Survey} resource
     */
    @Operation(
        summary = "Updates a survey detail by ID",
        description = """
            Updates a survey detail by its identifier.
            
            The identifier is a numeric value.
            
            The request body must contain the survey parameters.""",
        tags = { "survey" }
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful operation",
        useReturnTypeSchema = true)
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @ApiResponse(
        responseCode = "404",
        description = "Survey not found",
        content = @Content)
    @ApiResponse(
        responseCode = "412",
        description = "Survey cannot be updated because it is used by other resources",
        content = @Content)
    @PutMapping("/{id}")
    ResponseEntity<SurveyDTO> update(
        @Parameter(description = "The identifier of the survey to be updated", example = "1")
        @PathVariable("id") Long id,
        @RequestBody @Validated UpdateSurveyRequest request)
    {
        if (request.hasId() && !Objects.equals(id, request.getId())) {
            throw new NoMatchingRelatedFieldsException(
                "update.id", id, "update.request.id", request.getId());
        }
        UpdateSurveyInput input = new UpdateSurveyInput(id,
            request.getTitle(), request.getDescription(),
            request.getStartDate(), request.getEndDate(),
            request.getConfig());
        SurveyDTO dto = surveyService.update(input);
        return ResponseEntity.ok(dto);
    }

    /**
     * Deletes the {@code Survey} resource with the given ID.
     *
     * @param id the ID of the survey resource to be removed
     * @return the removed {@code Survey} resource
     */
    @Operation(
        summary = "Deletes a survey detail by ID",
        description = """
            Deletes a survey by its identifier.
            
            The identifier is a numeric value.""",
        tags = { "survey" }
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful operation")
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @ApiResponse(
        responseCode = "404",
        description = "Survey not found",
        content = @Content)
    @ApiResponse(
        responseCode = "409",
        description = "Survey is used by other resources",
        content = @Content)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
        @Parameter(description = "The identifier of the survey to be removed", example = "1")
        @PathVariable("id") Long id)
    {
        surveyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Close the {@code Survey} resource with the given ID.
     *
     * @param id the identifier of the survey to be manually closed
     */
    @Operation(
        summary = "Closes a survey by ID",
        description = """
            Closes a survey by its identifier.
            
            The identifier is a numeric value.

            Closed surveys are frozen resources that cannot be updated or deleted.
            
            Surveys can be manually closed only if its end date has been reached and there are
            some participant who have completed the survey sending their favourite songs.
            Closing surveys already closed has no effect.
            
            Only surveys in this final state are eligible for raffles or prize draws.""",
        tags = { "survey" }
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful operation")
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @ApiResponse(
        responseCode = "404",
        description = "Survey not found",
        content = @Content)
    @ApiResponse(
        responseCode = "412",
        description = "Survey cannot be closed due to business rules",
        content = @Content)
    @PostMapping("/{id}/close")
    ResponseEntity<SurveyDTO> close(
        @Parameter(description = "The identifier of the survey to be closed", example = "1")
        @PathVariable("id") Long id)
    {
        SurveyDTO dto = surveyService.close(id);
        return ResponseEntity.ok(dto);
    }
}
