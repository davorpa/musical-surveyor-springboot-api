package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.domain.colors.ColorDTO;
import io.davorpatech.apps.musicalsurveyor.domain.colors.CreateColorInput;
import io.davorpatech.apps.musicalsurveyor.domain.colors.FindColorsInput;
import io.davorpatech.apps.musicalsurveyor.domain.colors.UpdateColorInput;
import io.davorpatech.apps.musicalsurveyor.services.colors.ColorService;
import io.davorpatech.apps.musicalsurveyor.web.model.colors.CreateColorRequest;
import io.davorpatech.apps.musicalsurveyor.web.model.colors.UpdateColorRequest;
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
 * REST controller for managing {@code Color} resources.
 *
 * <p>As a controller, it is a stateless class that provides operations
 * to manage {@code Color} data domain. It exposes endpoints that
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
    name = "color",
    description = """
        The Colors API.
        
        Provides REST operations to manage Color resources."""
)
@RestController
@RequestMapping("/api/colors")
public class ColorController // NOSONAR
{
    private final ColorService colorService;

    /**
     * Constructs a new {@link ColorController} with the given arguments.
     *
     * @param colorService the color service, never {@code null}
     */
    ColorController(ColorService colorService) {
        Assert.notNull(colorService, "ColorService must not be null!");
        this.colorService = colorService;
    }

    /**
     * Finds all {@code Color} resources.
     *
     * @param pageable     the page and sorting parameters to be applied
     * @param forceUnpaged whether to force an unpaged result
     * @return all {@code Color} resources
     */
    @Operation(
        summary = "Finds all colors",
        description = """
            Finds all colors.
            
            The result can be paginated and sorted.
            
            The default sort order is ascending by code.""",
        tags = { "color" }
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
    PagedResult<ColorDTO> findAll(
        @ParameterObject
        @SortDefault.SortDefaults(
            @SortDefault(sort = "code", direction = Sort.Direction.ASC)
        ) Pageable pageable,
        @RequestParam(value = "unpaged", defaultValue = "false") boolean forceUnpaged)
    {
        FindColorsInput query = new FindColorsInput(
            forceUnpaged || pageable.isUnpaged() ?  0 : pageable.getPageNumber(),
            forceUnpaged || pageable.isUnpaged() ? -1 : pageable.getPageSize(),
            pageable.getSort()
        );
        return colorService.findAll(query);
    }

    /**
     * Retrieves the {@code Color} resource detail given its ID.
     *
     * @param id the ID of the resource to be retrieved
     * @return the {@code Color} resource matching the given ID
     */
    @Operation(
        summary = "Retrieves a color detail by ID",
        description = """
            Retrieves a color detail by ID.
            
            The identifier is a numeric value.""",
        tags = { "color" }
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
        description = "Color not found",
        content = @Content)
    @GetMapping("/{id}")
    ResponseEntity<ColorDTO> retrieveById(
        @Parameter(description = "The identifier of the color to be retrieved", example = "1")
        @RequestParam("id") Long id)
    {
        ColorDTO dto = colorService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Creates a new {@code Color} resource using the given data.
     *
     * @param request the request body containing the data
     * @return the created {@code Color} resource
     */
    @Operation(
        summary = "Creates a new color",
        description = """
            Creates a new color.
            
            The request body must contain the data.
            
            The color code must follow the hexadecimal format, e.g. #FF0000 for red; or also color names, e.g. red.""",
        tags = { "color" }
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
        description = "Color code already exists",
        content = @Content)
    @PostMapping
    ResponseEntity<ColorDTO> create(
        @Parameter(description = "The request body containing the data")
        @RequestBody @Validated CreateColorRequest request)
    {
        CreateColorInput input = new CreateColorInput(
            request.getCode());
        ColorDTO dto = colorService.create(input);

        // Compose URI Location of the retrieve endpoint for this created resource
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
        // build response entity providing URI and body of the created resource
        return ResponseEntity.created(location).body(dto);
    }

    /**
     * Updates a {@code Color} resource using the given request body.
     *
     * @param id      the ID of the color resource to be updated
     * @param request the request body containing the updated data
     * @return the updated {@code Color} resource
     */
    @Operation(
        summary = "Updates a color detail by ID",
        description = """
            Updates a color detail by its identifier.
            
            The identifier is a numeric value.
            
            The request body must contain the updated data.
            
            The color code must follow the hexadecimal format, e.g. #FF0000 for red; or also color names, e.g. red.""",
        tags = { "color" }
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
        description = "Color not found",
        content = @Content)
    @ApiResponse(
        responseCode = "409",
        description = "Color code already exists",
        content = @Content)
    @PutMapping("/{id}")
    ResponseEntity<ColorDTO> update(
        @Parameter(description = "The identifier of the color to be updated", example = "1")
        @PathVariable("id") Long id,
        @Parameter(description = "The request body containing the updated data")
        @RequestBody @Validated UpdateColorRequest request)
    {
        if (request.hasId() && !Objects.equals(id, request.getId())) {
            throw new NoMatchingRelatedFieldsException(
                "update.id", id, "update.request.id", request.getId());
        }
        UpdateColorInput input = new UpdateColorInput(id,
            request.getCode());
        ColorDTO dto = colorService.update(input);
        return ResponseEntity.ok(dto);
    }

    /**
     * Deletes the {@code Color} resource with the given ID.
     *
     * @param id the ID of the color resource to be removed
     * @return the removed {@code Color} resource
     */
    @Operation(
        summary = "Deletes a color detail by ID",
        description = """
            Deletes a color by its identifier.
            
            The identifier is a numeric value.""",
        tags = { "color" }
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
        description = "Color not found",
        content = @Content)
    @ApiResponse(
        responseCode = "409",
        description = "Color is used by other resources",
        content = @Content)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
        @Parameter(description = "The identifier of the color to be removed", example = "1")
        @PathVariable("id") Long id)
    {
        colorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
