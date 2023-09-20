package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.services.prizes.PrizeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final PrizeService prizeService;

    public PrizeController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    /**
     *Creates a new {@code PrizeController} instance.
     *
     *@param prizeService the{@code PrizeService} instance to use.
     *@throws NullPointerException if {@code prizeService} is {@code null}.
     *
     * @see PrizeService
     * @see PrizeController

     */
    @RestController
    @RequestMapping ("/api/prizes")
    public class PrizeController // NO SONAR
    { private final PrizeService prizeService;

        /**
         * Creates a new {@code PrizeController} instance.
         * @param prizeService the {@code PrizeService} instance to use.
         * @throws NullPointerException if {@code prizeService} is {@code null}.
         * @see PrizeService
         * @see PrizeController
         */
    PrizeController(PrizeService prizeService) {
        Assert.notNull(prizeService, "PrizeService must not be null!");
        this.prizeService = prizeService;
    }



 /*
 * Finds all {@code Prize} resources.
 *
 * @param pageable the page and sorting parameters to be applied
 * @param forceUnpaged wheter to force an unpaged result
 * @return all {@code Prize} resources
 */

