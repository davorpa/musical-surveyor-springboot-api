package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.domain.artist.ArtistDTO;
import io.davorpatech.apps.musicalsurveyor.domain.artist.CreateArtistInput;
import io.davorpatech.apps.musicalsurveyor.domain.artist.FindArtistsInput;
import io.davorpatech.apps.musicalsurveyor.domain.artist.UpdateArtistInput;
import io.davorpatech.apps.musicalsurveyor.services.artist.ArtistService;
import io.davorpatech.apps.musicalsurveyor.web.model.artist.CreateArtistRequest;
import io.davorpatech.apps.musicalsurveyor.web.model.artist.UpdateArtistRequest;
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
 * REST controller for managing {@code Artist} resources.
 *
 * <p>As a controller, it is a stateless class that provides operations
 * to manage {@code Artist} data domain. It exposes endpoints that
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
    name = "artist",
    description = """
        The Artists API.
        
        Provides REST operations to manage Artist resources."""
)
@RestController
@RequestMapping("/api/artists")
public class ArtistController // NOSONAR
{
    private final ArtistService artistService;

    /**
     * Constructs a new {@link ArtistController} with the given arguments.
     *
     * @param artistService the artist service, never {@code null}
     */
    ArtistController(ArtistService artistService) {
        Assert.notNull(artistService, "ArtistService must not be null!");
        this.artistService = artistService;
    }

    /**
     * Finds all {@code Artist} resources.
     *
     * @param pageable     the page and sorting parameters to be applied
     * @param forceUnpaged whether to force an unpaged result
     * @return all {@code Artist} resources
     */
    @Operation(
        summary = "Finds all artists",
        description = """
            Finds all artists.
            
            The results can be paginated and sorted.""",
        tags = { "artist" }
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
    PagedResult<ArtistDTO> findAll(
        @ParameterObject
        @SortDefault.SortDefaults(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
        ) Pageable pageable,
        @RequestParam(value = "unpaged", defaultValue = "false") boolean forceUnpaged)
    {
        FindArtistsInput query = new FindArtistsInput(
            forceUnpaged || pageable.isUnpaged() ?  0 : pageable.getPageNumber(),
            forceUnpaged || pageable.isUnpaged() ? -1 : pageable.getPageSize(),
            pageable.getSort()
        );
        return artistService.findAll(query);
    }

    /**
     * Retrieves the {@code Artist} resource detail given its ID.
     *
     * @param id the ID of the resource to be retrieved
     * @return the {@code Artist} resource matching the given ID
     */
    @Operation(
        summary = "Retrieves an artist detail by ID",
        description = """
            Retrieves an artist detail by its identifier.
    
            The identifier is a numeric value.""",
        tags = { "artist" }
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
        description = "Artist not found",
        content = @Content)
    @GetMapping("/{id}")
    ResponseEntity<ArtistDTO> retrieveById(
        @Parameter(description = "The identifier of the artist to be retrieved", example = "1")
        @PathVariable("id") Long id)
    {
        ArtistDTO dto = artistService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Creates a new {@code Artist} resource using the given arguments.
     *
     * @param request the request body containing the parameters
     * @return the created {@code Artist} resource
     */
    @Operation(
        summary = "Creates a new artist",
        description = """
            Creates a new artist.
            
            The request body must contain the parameters.""",
        tags = { "artist" }
    )
    @ApiResponse(
        responseCode = "201",
        description = "Successful operation",
        useReturnTypeSchema = true)
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @PostMapping
    ResponseEntity<ArtistDTO> create(
        @RequestBody @Validated CreateArtistRequest request)
    {
        CreateArtistInput input = new CreateArtistInput(
            request.getName(), request.getBiography());
        ArtistDTO dto = artistService.create(input);

        // Compose URI Location of the retrieve endpoint for this created resource
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
        // build response entity providing URI and body of the created resource
        return ResponseEntity.created(location).body(dto);
    }

    /**
     * Updates an {@code Artist} resource using the given request body.
     *
     * @param id      the ID of the artist resource to be updated
     * @param request the request body containing the updated parameters
     * @return the updated {@code Artist} resource
     */
    @Operation(
        summary = "Updates an artist detail by ID",
        description = """
            Updates an artist detail by its identifier.
            
            The identifier is a numeric value.
            
            The request body must contain the updated parameters.""",
        tags = { "artist" }
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
        description = "Artist not found",
        content = @Content)
    @PutMapping("/{id}")
    ResponseEntity<ArtistDTO> update(
        @Parameter(description = "The identifier of the artist to be updated", example = "1")
        @PathVariable("id") Long id,
        @RequestBody @Validated UpdateArtistRequest request)
    {
        if (request.hasId() && !Objects.equals(id, request.getId())) {
            throw new NoMatchingRelatedFieldsException(
                "update.id", id, "update.request.id", request.getId());
        }
        UpdateArtistInput input = new UpdateArtistInput(id,
            request.getName(), request.getBiography());
        ArtistDTO dto = artistService.update(input);
        return ResponseEntity.ok(dto);
    }

    /**
     * Deletes the {@code Artist} resource with the given ID.
     *
     * @param id the ID of the artist resource to be removed
     * @return the removed {@code Artist} resource
     */
    @Operation(
        summary = "Deletes an artist detail by ID",
        description = """
            Deletes an artist by its identifier.
            
            The identifier is a numeric value.""",
        tags = { "artist" }
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
        description = "Artist not found",
        content = @Content)
    @ApiResponse(
        responseCode = "409",
        description = "Artist is used by other resources",
        content = @Content)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
        @Parameter(description = "The identifier of the artist to be removed", example = "10")
        @PathVariable("id") Long id)
    {
        artistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
