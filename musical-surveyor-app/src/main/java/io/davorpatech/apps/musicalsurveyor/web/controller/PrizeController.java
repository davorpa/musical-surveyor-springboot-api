package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.domain.prizes.CreatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.FindPrizesInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.PrizeDTO;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.UpdatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.services.prizes.PrizeService;
import io.davorpatech.apps.musicalsurveyor.web.model.prizes.CreatePrizeRequest;
import io.davorpatech.apps.musicalsurveyor.web.model.prizes.UpdatePrizeRequest;
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
 * REST controller to manage {@code Prize} resources.
 *
 * <p>As a controller, it is a stateless class that provides operations
 * to manage {@code Prize} data domain. It exposes endpoints that
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
    name = "prize",
    description = """
        The Prizes API.
        
        Provides REST operations to manage Prize resources."""
)

@RestController
@RequestMapping("/api/prizes")
public class PrizeController // NOSONAR
{
    private final PrizeService prizeService;

    /**
     * Constructs a new {@link PrizeController} with the given arguments.
     *
     * @param prizeService the prize service, never {@code null}
     */
    PrizeController(PrizeService prizeService) {
        Assert.notNull(prizeService, "PrizeService must not be null!");
        this.prizeService = prizeService;
    }

    /**
     * Finds all {@code Prize} resources.
     *
     * @param pageable     the page and sorting parameters to be applied
     * @param forceUnpaged whether to force an unpaged result
     * @return all {@code Prize} resources
     */
    @Operation(
        summary = "Finds all Prize",
        description = """
            Finds all prizes.
            
            The results can be paginated and sorted.""",
        tags = { "prize" }
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
    PagedResult<PrizeDTO> findAll(
        @ParameterObject
        @SortDefault(sort = "monetaryValue", direction = Sort.Direction.DESC)
        @SortDefault(sort = "title", direction = Sort.Direction.ASC)
        Pageable pageable,
        @RequestParam(value = "unpaged", defaultValue = "false") boolean forceUnpaged)
    {
        FindPrizesInput query = new FindPrizesInput(
            forceUnpaged || pageable.isUnpaged() ?  0 : pageable.getPageNumber(),
            forceUnpaged || pageable.isUnpaged() ? -1 : pageable.getPageSize(),
            pageable.getSort()
        );
        return prizeService.findAll(query);
    }

    /**
     * Retrieves the {@code Prize} resource detail given its ID.
     *
     * @param id the ID of the resource to be retrieved
     * @return the {@code Prize} resource matching the given ID
     */
    @Operation(
        summary = "Retrieves a prize detail by ID",
        description = """
            Retrieves a prize detail by its identifier.
            
            The identifier is a numeric value.""",
        tags = { "prize" }
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
        description = "Prize not found",
        content = @Content)
    @GetMapping("/{id}")
    ResponseEntity<PrizeDTO> retrieveById(
        @Parameter(description = "The identifier of the prize to be retrieved", example = "1")
        @PathVariable("id") Long id)
    {
        PrizeDTO dto = prizeService.findById(id);
        return ResponseEntity.ok(dto);
    }


    /**
     * Creates a new {@code Prize] resource using the given request arguments.
     *
     * @param request the request body containing the parameters
     * @return the created {@code Prize} resource
     */
    @Operation(
        summary = "Creates a new prize",
        description = """
            Creates a new prize.
            
            The request body must contain the parameters.""",
        tags = { "prize" }
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
        description = "Prize already exists",
        content = @Content)
    @PostMapping
    ResponseEntity<PrizeDTO> create(
        @RequestBody @Validated CreatePrizeRequest request)
    {
        CreatePrizeInput input = new CreatePrizeInput(
            request.getTitle(), request.getDescription(), request.getMonetaryValue());
        PrizeDTO dto = prizeService.create(input);

        // Compose URI Location of the retrieve endpoint for this created resource
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
        // build response entity providing URI and body of the created resource
        return ResponseEntity.created(location).body(dto);
    }

    /**
     * Updates a {@code Prize} resource using the given request body.
     *
     * @param id      the ID of the prize resource to be updated
     * @param request the request body containing the parameters
     * @return the updated {@code Prize} resource
     */
    @Operation(
        summary = "Updates an prize detail by ID",
        description = """
            Updates an prize detail by its identifier.
            
            The identifier is a numeric value.
            
            The request body must contain the updated parameters.""",
        tags = { "prize" }
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
        description = "Prize not found",
        content = @Content)
    @ApiResponse(
        responseCode = "409",
        description = "Prize already exists",
        content = @Content)
    @ApiResponse(
        responseCode = "412",
        description = "Prize cannot be updated because it is used by other resources",
        content = @Content)
    @PutMapping("/{id}")
    ResponseEntity<PrizeDTO> update(
        @Parameter(description = "The identifier of the prize to be updated", example = "1")
        @PathVariable("id") Long id,
        @RequestBody @Validated UpdatePrizeRequest request)
    {
        if (request.hasId() && !Objects.equals(id, request.getId())) {
            throw new NoMatchingRelatedFieldsException(
                "update.id", id, "update.request.id", request.getId());
        }
        UpdatePrizeInput input = new UpdatePrizeInput(id,
            request.getTitle(), request.getDescription(), request.getMonetaryValue());
        PrizeDTO dto = prizeService.update(input);
        return ResponseEntity.ok(dto);
    }

    /**
     * Deletes the {@code Prize} resource with the given ID.
     *
     * @param id the ID of the prize resource to be removed
     * @return the removed {@code Prize} resource
     */
    @Operation(
        summary = "Deletes a prize detail by ID",
        description = """
            Deletes a prize by its identifier.
                        
            The identifier is a numeric value.""",
        tags = { "prize" }
    )
    @ApiResponse(responseCode = "204",
        description = "Successful operation")
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @ApiResponse(
        responseCode = "404",
        description = "Prize not found",
        content = @Content)
    @ApiResponse(
        responseCode = "409",
        description = "Prize is used by other resources",
        content = @Content)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(
        @Parameter(description = "The identifier of the prize to be removed", example = "1")
        @PathVariable("id") Long id)
    {
        prizeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
