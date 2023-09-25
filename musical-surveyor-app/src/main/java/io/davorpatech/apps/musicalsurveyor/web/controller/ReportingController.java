package io.davorpatech.apps.musicalsurveyor.web.controller;

import io.davorpatech.apps.musicalsurveyor.domain.SongWithPopularityInfo;
import io.davorpatech.apps.musicalsurveyor.services.reports.ReportingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for reporting.
 */
@Tag(
    name = "reports",
    description = """
        The Reporting API.
        
        Provides REST operations to access aggregated report data."""
)
@RestController
@RequestMapping("/api/reports")
public class ReportingController // NOSONAR
{
    private final ReportingService reportingService;

    /**
     * Constructs a new {@link ReportingController} with the given arguments.
     *
     * @param reportingService the reporting service, never {@code null}
     */
    ReportingController(ReportingService reportingService) {
        Assert.notNull(reportingService, "ReportingService must not be null!");
        this.reportingService = reportingService;
    }

    /**
     * Returns a collection of songs ranked by popularity.
     *
     * <p>The rank is based on the number of times the song was selected as a favorite
     * in the successive surveys made by the radio station.
     *
     * @return the most popular songs
     */
    @Operation(
        summary = "List the songs ranking by its popularity",
        description = """
            The rank is based on the number of times the song was selected as a favorite
            in the successive surveys made by the radio station.""",
        tags = { "reports" }
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful operation",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = SongWithPopularityInfo.class
            )
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "Request parameters are invalid",
        content = @Content)
    @GetMapping(value = "/songs-popularity", produces = MediaType.APPLICATION_JSON_VALUE)
    List<SongWithPopularityInfo> songsPopularity() {
        return reportingService.findAllMostPopularSongs();
    }
}
