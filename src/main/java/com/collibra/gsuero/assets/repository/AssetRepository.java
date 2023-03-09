package com.collibra.gsuero.assets.repository;

import com.collibra.gsuero.assets.model.Asset;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "assets", path = "assets")
public interface AssetRepository extends PagingAndSortingRepository<Asset, Long> {
    List<Asset> findByName(@Param("name") String name);
}