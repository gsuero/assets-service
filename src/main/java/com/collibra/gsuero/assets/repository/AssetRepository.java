package com.collibra.gsuero.assets.repository;

import com.collibra.gsuero.assets.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssetRepository extends PagingAndSortingRepository<Asset, Long>, JpaRepository<Asset, Long> {

}