package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.domain.artist.PrizeDTO;
import io.davorpatech.apps.musicalsurveyor.domain.artist.CreatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.CreatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.FindPrizesInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.PrizeDTO;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.UpdatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.web.model.artist.UpdatePrizeRequest;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * REST Ccntroller for managing {@code Prize} resorces.
 *
 * <p> As a controller, it is a stateless class that provides operations
 * to manage {@code Prize} data domain. It exposes endpoints that
 * handle HTTP requests such as GET,POST,PUT, and DELETE. It also
 * provides a way to map request parameters to the method arguments
 * using the {@Link RequestParam @RequestParam} annotation. It also
 * provides a way to map request body to the method argumentes using the
 * {@Link RequestBody @RequestBody} annotation. It also provides a way
 * to map request path variables to the method arguments using the
 * {@Link PathVariable @PathVariable} annotation.
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
    private final PrizeController prizeController;

    /**
     *Creates a new {@code PrizeController} instance.
     *
     * @param prizeController the {@code PrizeController} instance to be injected.
     *  Must not be {@code null}.
     */
    PrizeController(PrizeController prizeController) {
        Objects.requireNonNull(prizeController, "PrizeController must not be null!");
        this.prizeController = prizeController;
    }
    /**
    /*
      Finds all {@code Prize} resources.

      <p> The results can be paginated and sorted.

      @param pageable the {@code Pageable} instance to be injected.
     *                 Must not be {@code null}.
     *                 Default value is {@code Pageable.unpaged()}.
     */
    @Operation(
        summary = "Finds all Prize resources",
        description = """
            Finds all Prize resources.
            
            The results can be paginated and sorted.""",
        tags = { "prize" }
    )
    @ApiResponse(
        responseCode = "200",
        description = "Succesfully retrieved Prize resources",
        useReturnTypeSchema = true)

    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        useReturnTypeSchema = true)

    @GetMapping
    PagedResult<PrizeDTO> findAll(
        @ParameterObject
        @SortDefault.SortDefaults(
            @SortDefault( sort= "id", direction = Sort.Direction.ASC)
        )Pageable pageable,
        @RequestParam(value = "unpager", defaultValue = "false") boolean forceUnpaged)
    {
        FindPrizesInput query = new FindPrizesInput(
            forceUnpaged || pageable.isUnpaged() ?  0 : pageable.getPageNumber(),
            forceUnpaged || pageable.isUnpaged() ? -1 : pageable.getPageSize(),
            pageable.getSort()
        );
        return prizeController.findAll(query);
    }

    /**
     * Retrieves the {@code Prize} resource detal given its ID.
     *
     * @param id the ID of the resource to be retrieved
     *           Must not be {@code null}.
     *           Must be a positive number.
     *           Must be a valid {@code Prize} identifier.
     */
    @Operation(
        summary = "Retrieves a Prize detail by ID",
        description = """
            Retrieves a Prize detail by its identifier.""",

        tags = { "prize"}
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
        @Parameter(description = "The Prize identifier", example= "1")
        @PathVariable("id") Long id)
    {
        PrizeDTO dto = prizeService.findById(id);
        return ResponseEntity.ok(dto);
    }


    /**
     * Creates a new {@code Prize] resource. instance.
     * <p>  The request body must contain the parameters.
     * @param dto the {@code PrizeDTO} instance to be created.
     *            Must not be {@code null}
     *            Must have a {@code null identifier}
     *            Must have a {@code null version}
     *            Must have a {@code null createdAt}
     *            Must have a {@code null updatedAt}
     *            Must have a {@code null deletedAt}
     *            Must have a {@code null deleted}
     *            Must have a {@code null name}
     *            Must have a {@code null description}
     *            Must have a {@code null year}
     *            Must have a {@code null category}
     *            Must have a {@code null winner}
     *            Must have a {@code null entity}
    */
    @Operation(
        summary = "Creates a new Prize",
        description = """
            Creates a new Prize.
            
           The request body must contain the parameters.""",
        tags = { "Prize" }
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
    ResponseEntity<PrizeDTO> create(
        @Parameter(description = "The Prize to be created")
        @RequestBody @Validated CreatePrizeInput request)
    {
        CreatePrizeInput input = new CreatePrizeInput(
            request.getId(), request.getdescription(),
        PrizeDTO ,dto = prizeService.create(input));

        // Compose URI Location of the retrieve endpoint for this created resource
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
        // build response entity providing URI and body of the created resource
        return ResponseEntity.created(location).body(dto);
    }
    /**
     * Updates an {@code Prize} resource using the given request body.
     *
     * @param id      the ID of the prize resource to be updated
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

     @PutMapping("/{id}")
     ResponseEntity<PrizeDTO> update(
         @Parameter(description = "The identifier of the prize to be updated", example = "1")
         @PathVariable("id") Long id,
         @Parameter(description = "The Prize to be updated")
         @RequestBody @Validated UpdatePrizeRequest request)     {
         if (id == null) {
             throw new IllegalArgumentException("Prize identifier must not be null!");
         }
         return null;
     }
}
     /**
      * Deletes am {@code Prize} resource given its ID.
      *
      * @param id the ID of the prize resource to be deleted
      *           Must not be {@code null}
      */

     @Operation(
       summary = "Deletes an prize detail by ID",
       description = """
            Deletes an prize by its identifier.
            
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
         @Parameter(description = "The identifier of the prize to be removed", example = "10")
         @PathVariable("id") Long id)
{
    prizeService.deleteById(id);
    return ResponseEntity.noContent().build();
}
}

