package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.domain.songs.CreateSongInput;
import io.davorpatech.apps.musicalsurveyor.domain.songs.FindSongsInput;
import io.davorpatech.apps.musicalsurveyor.domain.songs.SongWithArtistDTO;
import io.davorpatech.apps.musicalsurveyor.domain.songs.UpdateSongInput;
import io.davorpatech.apps.musicalsurveyor.services.songs.SongService;
import io.davorpatech.apps.musicalsurveyor.web.model.songs.CreateSongRequest;
import io.davorpatech.apps.musicalsurveyor.web.model.songs.UpdateSongRequest;
import io.davorpatech.fwk.exception.NoMatchingRelatedFieldsException;
import io.davorpatech.fwk.model.PagedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

/**
 * REST controller for managing {@code Song} resources related with a
 * given {@code Artist}.
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
@RestController
@RequestMapping("/api/artists/{artistId}/songs")
public class ArtistSongsController // NOSONAR
{
    private final SongService songService;

    /**
     * Constructs a new {@link SongController} with the given arguments.
     *
     * @param songService the song service, never {@code null}
     */
    ArtistSongsController(SongService songService) {
        Assert.notNull(songService, "SongService must not be null!");
        this.songService = songService;
    }

    /**
     * Finds all {@code Song} resources part of repertoire of the given
     * {@code Artist}.
     *
     * @param artistId     the identifier of the artist to find its song repertoire
     * @param pageable     the page and sorting parameters to be applied
     * @param forceUnpaged whether to force an unpaged result
     * @return all {@code Song} resources
     */
    @Operation(
        summary = "Finds the songs part of the artist repertoire",
        description = """
            Finds all songs part of the artist repertoire.
            
            The results can be paginated and sorted.""",
        tags = { "artist", "song" }
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
        @Parameter(example = "1")
        @PathVariable("artistId") Long artistId,
        @ParameterObject
        Pageable pageable,
        @RequestParam(value = "unpaged", defaultValue = "false") boolean forceUnpaged)
    {
        FindSongsInput query = FindSongsInput.ofArtist(
            artistId,
            forceUnpaged || pageable.isUnpaged() ?  0 : pageable.getPageNumber(),
            forceUnpaged || pageable.isUnpaged() ? -1 : pageable.getPageSize(),
            pageable.getSort()
        );
        return songService.findAll(query);
    }

    /**
     * Finds a {@code Song} resource inside the repertoire of a given
     * {@code Artist}.
     *
     * @param artistId the identifier of the artist wherein find the song
     * @param songId   the identifier of the song to find
     * @return the {@code Song} resource
     */
    @Operation(
        summary = "Finds a song part of the artist repertoire",
        description = """
            Finds a song part of the artist repertoire.
            
            The identifiers are numeric values.""",
        tags = { "artist", "song" }
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
        description = "Artist or song not found",
        content = @Content)
    @GetMapping("/{songId}")
    ResponseEntity<SongWithArtistDTO> retrieveById(
        @Parameter(example = "1")
        @PathVariable("artistId") Long artistId,
        @Parameter(example = "1")
        @PathVariable("songId") Long songId)
    {
        SongWithArtistDTO song = songService.findInArtistRepertoire(artistId, songId);
        return ResponseEntity.ok(song);
    }

    /**
     * Creates a new {@code Song} resource inside the repertoire of a given
     * {@code Artist} using the given arguments.
     *
     * @param artistId the identifier of the artist wherein create the song
     *                 resource
     * @param request  the request body containing the parameters
     * @return the created {@code Song} resource
     */
    @Operation(
        summary = "Creates a new song part of the artist repertoire",
        description = """
            Creates a new song part of the artist repertoire.
            
            The request body must contain the parameters.""",
        tags = { "artist", "song" }
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
        responseCode = "404",
        description = "Artist not found",
        content = @Content)
    @PostMapping
    ResponseEntity<SongWithArtistDTO> create(
        @Parameter(example = "1")
        @PathVariable("artistId") Long artistId,
        @RequestBody @Validated CreateSongRequest request)
    {
        if (request.hasArtistId() && !Objects.equals(artistId, request.getArtistId())) {
            throw new NoMatchingRelatedFieldsException(
                "create.artistId", artistId, "create.request.artistId", request.getArtistId());
        }
        CreateSongInput input = new CreateSongInput(
            artistId,
            request.getTitle(), request.getReleaseYear(),
            request.getDuration(), request.getGenre());
        SongWithArtistDTO dto = songService.create(input);

        // Compose URI Location of the retrieve endpoint for this created resource
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
        // build response entity providing URI and body of the created resource
        return ResponseEntity.created(location).body(dto);
    }

    /**
     * Updates a {@code Song} resource inside the repertoire of a given
     * {@code Artist} using the given arguments.
     *
     * @param artistId the identifier of the artist that owns the song to update
     * @param songId   the identifier of the song to update
     * @param request  the request body containing the parameters
     * @return the updated {@code Song} resource
     */
    @Operation(
        summary = "Updates a song part of the artist repertoire",
        description = """
            Updates a song part of the artist repertoire.
            
            The request body must contain the parameters.
            
            The identifiers are numeric values.""",
        tags = { "artist", "song" }
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
        description = "Artist or song not found",
        content = @Content)
    @ApiResponse(
        responseCode = "412",
        description = "Song cannot be transferred to another artist",
        content = @Content)
    @PutMapping("/{songId}")
    ResponseEntity<SongWithArtistDTO> update(
        @Parameter(example = "1")
        @PathVariable("artistId") Long artistId,
        @Parameter(example = "1")
        @PathVariable("songId") Long songId,
        @RequestBody @Validated UpdateSongRequest request)
    {
        if (request.hasArtistId() && !Objects.equals(artistId, request.getArtistId())) {
            throw new NoMatchingRelatedFieldsException(
                "update.artistId", artistId, "update.request.artistId", request.getArtistId());
        }
        if (request.hasId() && !Objects.equals(songId, request.getId())) {
            throw new NoMatchingRelatedFieldsException(
                "update.songId", songId, "update.request.id", request.getId());
        }
        UpdateSongInput input = new UpdateSongInput(songId, artistId,
            request.getTitle(), request.getReleaseYear(),
            request.getDuration(), request.getGenre());
        SongWithArtistDTO dto = songService.update(input);
        return ResponseEntity.ok(dto);
    }

    /**
     * Deletes a {@code Song} resource inside the repertoire of a given
     * {@code Artist}.
     *
     * @param artistId the identifier of the artist wherein delete the song
     * @param songId   the identifier of the song to delete
     * @return the {@code Song} resource deleted
     */
    @Operation(
        summary = "Deletes a song part of the artist repertoire",
        description = """
            Deletes a song part of the artist repertoire.
            
            The identifiers are numeric values.""",
        tags = { "artist", "song" }
    )
    @ApiResponse(
        responseCode = "204",
        description = "Successful operation",
        useReturnTypeSchema = true)
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @ApiResponse(
        responseCode = "404",
        description = "Artist or song not found",
        content = @Content)
    @ApiResponse(
        responseCode = "409",
        description = "Song cannot be deleted",
        content = @Content)
    @DeleteMapping("/{songId}")
    ResponseEntity<Void> delete(
        @Parameter(example = "1")
        @PathVariable("artistId") Long artistId,
        @Parameter(example = "1")
        @PathVariable("songId") Long songId)
    {
        songService.deleteFromArtistRepertoire(artistId, songId);
        return ResponseEntity.noContent().build();
    }

}
