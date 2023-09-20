package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.domain.songs.FindSongsInput;
import io.davorpatech.apps.musicalsurveyor.domain.songs.SongWithArtistDTO;
import io.davorpatech.apps.musicalsurveyor.services.songs.SongService;
import io.davorpatech.fwk.model.PagedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@code Song} resources.
 *
 * <p>As a controller, it is a stateless class that provides operations
 * to manage {@code Artist} data domain. It exposes endpoints that
 * handle HTTP requests such as GET, POST, PUT, and DELETE. It also
 * provides a way to map request parameters to the method arguments
 * using the {@link RequestParam @RequestParam} annotation. It also
 * provides a way to map request body to the method arguments using the
 * {@link org.springframework.web.bind.annotation.RequestBody @RequestBody}
 * annotation. It also provides a way to map request path variables to the
 * method arguments using the {@link org.springframework.web.bind.annotation.PathVariable
 * @PathVariable} annotation.
 *
 * <p>Controllers are the entry point to the presentation layer. They are
 * responsible for handling the HTTP requests, and they delegate the
 * business logic to the services.
 */
@Tag(
    name = "song",
    description = """
        The Songs API.
        
        Provides REST operations to manage Songs resources."""
)
@RestController
@RequestMapping("/api/songs")
public class SongController // NOSONAR
{
    private final SongService songService;

    /**
     * Constructs a new {@link SongController} with the given arguments.
     * 
     * @param songService the song service, never {@code null}
     */
    SongController(SongService songService) {
        Assert.notNull(songService, "SongService must not be null!");
        this.songService = songService;
    }

    /**
     * Finds all {@code Song} resources.
     *
     * @param pageable     the page and sorting parameters to be applied
     * @param forceUnpaged whether to force an unpaged result
     * @return all {@code Song} resources
     */
    @Operation(
        summary = "Finds all songs",
        description = """
            Finds all songs.
            
            The results can be paginated and sorted.""",
        tags = { "song" }
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
    PagedResult<SongWithArtistDTO> findAll(
        @ParameterObject
        Pageable pageable,
        @RequestParam(value = "unpaged", defaultValue = "false") boolean forceUnpaged)
    {
        FindSongsInput query = FindSongsInput.of(
            forceUnpaged || pageable.isUnpaged() ?  0 : pageable.getPageNumber(),
            forceUnpaged || pageable.isUnpaged() ? -1 : pageable.getPageSize(),
            pageable.getSort()
        );
        return songService.findAll(query);
    }

    /**
     * Retrieves the detail of a {@code Song} resource given its ID.
     *
     * @param id the ID of the resource to be retrieved
     * @return the {@code Song} resource matching the given ID
     */
    @Operation(
        summary = "Retrieves a song detail by its ID",
        description = """
            Retrieves the song detail by its identifier.
            
            The identifier is a numeric value.""",
        tags = { "song" }
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
        description = "The song is not found",
        content = @Content)
    @GetMapping("/{id}")
    ResponseEntity<SongWithArtistDTO> retrieveById(
        @Parameter(description = "The identifier of the song to be retrieved", example = "1")
        @PathVariable("id") Long id)
    {
        SongWithArtistDTO dto = songService.findById(id);
        return ResponseEntity.ok(dto);
    }
}
