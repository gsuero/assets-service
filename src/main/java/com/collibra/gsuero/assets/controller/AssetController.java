package com.collibra.gsuero.assets.controller;

import com.collibra.gsuero.assets.error.NotFoundException;
import com.collibra.gsuero.assets.model.Asset;
import com.collibra.gsuero.assets.repository.AssetRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/asset")
public class AssetController {
    private static final Logger LOG = LoggerFactory.getLogger(AssetController.class);

    private AssetRepository repository;

    @GetMapping("/{id}")
    public Asset getAsset(@PathVariable long id) {
        Optional<Asset> assetOptional = repository.findById(id);

        if (assetOptional.isPresent()) {
            return assetOptional.get();
        }
        LOG.error("Asset with id {} was not present in data store", id);
        throw new NotFoundException();
    }

    @GetMapping
    public Page<Asset> getAssets(@NotNull final Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Autowired
    public void setRepository(AssetRepository repository) {
        this.repository = repository;
    }
}
