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
    @GetMapping
    PagedResult<ArtistDTO> findAll(
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
     * @param id the ID of the artist to be retrieved
     * @return the {@code Artist} resource matching the given ID
     */
    @GetMapping("/{id}")
    ResponseEntity<ArtistDTO> retrieveById(
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
    @PutMapping("/{id}")
    ResponseEntity<ArtistDTO> update(
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
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
        @PathVariable("id") Long id)
    {
        artistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
