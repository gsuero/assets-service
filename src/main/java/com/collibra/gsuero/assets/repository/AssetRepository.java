package com.collibra.gsuero.assets.repository;

import com.collibra.gsuero.assets.model.domain.AssetDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssetRepository extends JpaRepository<AssetDao, Long> {

    List<AssetDao> findByParentId(long parentId);
}