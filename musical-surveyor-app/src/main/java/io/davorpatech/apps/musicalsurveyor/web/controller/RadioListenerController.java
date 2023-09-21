package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.domain.listeners.CreateRadioListenerInput;
import io.davorpatech.apps.musicalsurveyor.domain.listeners.FindRadioListenersInput;
import io.davorpatech.apps.musicalsurveyor.domain.listeners.RadioListenerDTO;
import io.davorpatech.apps.musicalsurveyor.domain.listeners.UpdateRadioListenerInput;
import io.davorpatech.apps.musicalsurveyor.services.listeners.RadioListenerService;
import io.davorpatech.apps.musicalsurveyor.web.model.listeners.CreateRadioListenerRequest;
import io.davorpatech.apps.musicalsurveyor.web.model.listeners.UpdateRadioListenerRequest;
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
 * REST controller for managing {@code RadioListener} resources.
 *
 * <p>As a controller, it is a stateless class that provides operations
 * to manage {@code RadioListener} data domain. It exposes endpoints that
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
    name = "radio-listener",
    description = """
        The Radio Listeners API.
                
        Provides REST operations to manage Radio Listener resources."""
)
@RestController
@RequestMapping("/api/radio-listeners")
public class RadioListenerController // NOSONAR
{
    private final RadioListenerService radioListenerService;

    /**
     * Constructs a new {@code RadioListenerController} with the given arguments.
     *
     * @param radioListenerService the {@code RadioListenerService} instance
     *                             to be injected, never {@code null}
     */
    RadioListenerController(RadioListenerService radioListenerService) {
        Assert.notNull(radioListenerService, "RadioListenerService must not be null!");
        this.radioListenerService = radioListenerService;
    }

    /**
     * Finds all {@code RadioListener} resources.
     *
     * @return the {@code ResponseEntity} with the list of {@code RadioListener}
     * resources in body
     */
    @Operation(
        summary = "Finds all radio listeners",
        description = """
            Finds all radio listener.
                        
            The results can be paginated and sorted.""",
        tags = {"radio-listener"}
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful operation",
        useReturnTypeSchema = true)
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @GetMapping
    PagedResult<RadioListenerDTO> findAll(
        @ParameterObject
        @SortDefault.SortDefaults(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
        ) Pageable pageable,
        @RequestParam(value = "unpaged", defaultValue = "false") boolean forceUnpaged)
    {
        FindRadioListenersInput query = new FindRadioListenersInput(
            forceUnpaged || pageable.isUnpaged() ? 0 : pageable.getPageNumber(),
            forceUnpaged || pageable.isUnpaged() ? -1 : pageable.getPageSize(),
            pageable.getSort()
        );
        return radioListenerService.findAll(query);
    }

    /**
     * Retrieves the {@code RadioListener} resource detail given its ID.
     *
     * @param id the ID of the resource to be retrieved
     * @return the {@code RadioListener} resource matching the given ID
     */
    @Operation(
        summary = "Retrieves a radio listener detail by ID",
        description = """
            Retrieves a radio listener detail by its identifier.
                            
            The identifier is a numeric value.""",
        tags = {"radio-listener"}
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
        description = "Radio listener not found",
        content = @Content)
    @GetMapping("/{id}")
    ResponseEntity<RadioListenerDTO> retrieveById(
        @Parameter(description = "The identifier of the radio listener to be retrieved", example = "1")
        @PathVariable("id") Long id)
    {
        RadioListenerDTO dto = radioListenerService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Creates a new {@code RadioListener} resource using the given arguments.
     *
     * @param request the request body containing the parameters
     * @return the created {@code RadioListener} resource
     */
    @Operation(
        summary = "Creates a new radio listener",
        description = """
            Creates a new radio listener resource.
                            
            The request body must contain the parameters.""",
        tags = {"radio-listener"}
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
        responseCode = "409",
        description = "Radio listener already exists",
        content = @Content)
    @PostMapping
    ResponseEntity<RadioListenerDTO> create(
        @RequestBody @Validated CreateRadioListenerRequest request)
    {
        CreateRadioListenerInput input = new CreateRadioListenerInput(
            request.getName(), request.getPhone(),
            request.getAddress(), request.getEmail());
        RadioListenerDTO dto = radioListenerService.create(input);

        // Compose URI Location of the retrieve endpoint for this created resource
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
        // build response entity providing URI and body of the created resource
        return ResponseEntity.created(location).body(dto);
    }

    /**
     * Updates an {@code RadioListener} resource using the given request body.
     *
     * @param id      the ID of the radio listener resource to be updated
     * @param request the request body containing the updated parameters
     * @return the updated {@code RadioListener} resource
     */
    @Operation(
        summary = "Updates a radio listener detail by ID",
        description = """
            Updates a radio listener detail by its identifier.
                            
            The identifier is a numeric value.
                            
            The request body must contain the updated parameters.""",
        tags = {"radio-listener"}
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
        description = "Radio listener not found",
        content = @Content)
    @ApiResponse(
        responseCode = "409",
        description = "Radio listener already exists",
        content = @Content)
    @PutMapping("/{id}")
    ResponseEntity<RadioListenerDTO> update(
        @Parameter(description = "The identifier of the radio listener to be updated", example = "1")
        @PathVariable("id") Long id,
        @RequestBody @Validated UpdateRadioListenerRequest request)
    {
        if (request.hasId() && !Objects.equals(id, request.getId())) {
            throw new NoMatchingRelatedFieldsException(
                "update.id", id, "update.request.id", request.getId());
        }
        UpdateRadioListenerInput input = new UpdateRadioListenerInput(id,
            request.getName(), request.getPhone(),
            request.getAddress(), request.getEmail());
        RadioListenerDTO dto = radioListenerService.update(input);
        return ResponseEntity.ok(dto);
    }

    /**
     * Deletes the {@code RadioListener} resource with the given ID.
     *
     * @param id the ID of the radio listener resource to be removed
     * @return the removed {@code RadioListener} resource
     */
    @Operation(
        summary = "Deletes a radio listener detail by ID",
        description = """
            Deletes a radio listener by its identifier.
                            
            The identifier is a numeric value.""",
        tags = {"radio-listener"}
    )
    @ApiResponse(
        responseCode = "204",
        description = "Successful operation")
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @ApiResponse(
        responseCode = "404",
        description = "Radio listener not found",
        content = @Content)
    @ApiResponse(
        responseCode = "409",
        description = "Radio listener has related resources",
        content = @Content)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
        @Parameter(description = "The identifier of the radio listener to be deleted", example = "1")
        @PathVariable("id") Long id)
    {
        radioListenerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
