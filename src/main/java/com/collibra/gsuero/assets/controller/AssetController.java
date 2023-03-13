package com.collibra.gsuero.assets.controller;

import com.collibra.gsuero.assets.model.Asset;
import com.collibra.gsuero.assets.service.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asset")
public class AssetController {
    private AssetService assetService;

    @Operation(summary = "Get a Asset by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Asset",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Asset.class)) }),
            @ApiResponse(responseCode = "404", description = "Asset not found",
                    content = @Content) })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Asset getAsset(@PathVariable @NotNull long id) {
        return assetService.getById(id);
    }

    @Operation(summary = "Create an Asset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created an Asset",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Asset.class)) })
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Asset create(@RequestBody final Asset asset) {
        return assetService.create(asset);
    }

    @Operation(summary = "Updates an Asset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated an Asset",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Asset.class)) }),
            @ApiResponse(responseCode = "404", description = "Asset not found",
                    content = @Content) })
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Asset update(@PathVariable @NotNull long id, @RequestBody final Asset asset) {
        return assetService.update(id, asset);
    }

    @Operation(summary = "Deletes an Asset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted an Asset",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Asset.class)) }),
            @ApiResponse(responseCode = "404", description = "Asset not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @NotNull Long id) {
        assetService.delete(id);
    }

    @Operation(summary = "Promotes an Asset, and every other asset related to provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promoted the Asset",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Asset.class)) }),
            @ApiResponse(responseCode = "404", description = "Asset not found",
                    content = @Content) })
    @PutMapping("/{id}/promote")
    @ResponseStatus(HttpStatus.OK)
    public void promote(@PathVariable Long id) {
        assetService.promote(id);
    }

    @Autowired
    public void setAssetService(AssetService assetService) {
        this.assetService = assetService;
    }
}
