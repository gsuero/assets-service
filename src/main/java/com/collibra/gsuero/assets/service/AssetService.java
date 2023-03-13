package com.collibra.gsuero.assets.service;

import com.collibra.gsuero.assets.error.NotFoundException;
import com.collibra.gsuero.assets.model.Asset;
import com.collibra.gsuero.assets.model.AssetEvent;
import com.collibra.gsuero.assets.model.Operation;
import com.collibra.gsuero.assets.model.domain.AssetDao;
import com.collibra.gsuero.assets.repository.AssetRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.collibra.gsuero.assets.model.Operation.PROMOTE;

@Service
public class AssetService {
    private static final Logger LOG = LoggerFactory.getLogger(AssetService.class);
    private AssetRepository repository;
    private EventService eventService;
    @Autowired
    public void setRepository(AssetRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public Asset getById(long id) {
        Optional<AssetDao> assetOptional = repository.findById(id);

        if (assetOptional.isPresent()) {
            return convert(assetOptional.get());
        }
        LOG.warn("Asset with id {} was not present in data store", id);
        throw new NotFoundException();
    }

    public Asset create(Asset asset) {
        LOG.debug("About to create new Asset with name : {}", asset.getName());
        Asset createdAsset = convert(repository.save(convert(asset)));
        LOG.debug("Created asset for name {} with resulting id {}", asset.getName(), createdAsset.getId());
        return createdAsset;
    }

    public Asset update(long id, Asset asset) {
        Asset fetched = getById(id);

        fetched.setName(asset.getName());
        fetched.setPromoted(asset.isPromoted());

        LOG.debug("About to update Asset with id : {}", id);
        Asset updatedAsset = convert(repository.save(convert(fetched)));
        LOG.debug("Updated asset for name {} with id {}", asset.getName(), updatedAsset.getId());
        return updatedAsset;
    }
    public void delete(long id) {
        LOG.debug("About to delete Asset with id : {}", id);
        repository.deleteById(id);
    }

    public void promote(Long id) {
        Optional<AssetDao> optionalDao = repository.findById(id);

        if (optionalDao.isPresent()) {
            promote(optionalDao.get());
        } else {
            throw new NotFoundException();
        }
    }

    private void promote(AssetDao dao) {
        if (!dao.isPromoted()) {
            LOG.debug("About to promote asset [{}] and all its children...", dao.getId());
            notify(PROMOTE, convert(dao));
            List<AssetDao> daos = repository.findByParentId(dao.getId());
            for (AssetDao children : daos) {
                if (!children.isPromoted()) {
                    promote(children);
                }
            }

            // before we process parent, lets flag it...
            flagPromoted(dao);

            if (dao.getParent() != null) {
                promote(dao.getParent().getId());
            }


        }
    }
    private void flagPromoted(AssetDao dao) {
        dao.setPromoted(true);
        repository.save(dao);
    }

    private Asset convert(AssetDao dao) {
        if (dao == null) {
            return null;
        }
        Asset asset = new Asset();
        asset.setId(dao.getId());
        asset.setName(dao.getName());
        if (dao.getParent() != null) {
            asset.setParent(dao.getParent().getId());
        }
        return asset;
    }

    private AssetDao convert(Asset dto) {
        AssetDao asset = new AssetDao();
        asset.setId(dto.getId());
        asset.setName(dto.getName());
        if (dto.getParent() != null && dto.getParent() > 0) {
            asset.setParent(repository.getReferenceById(dto.getParent()));
        }
        return asset;
    }

    private void notify(Operation operation, Asset asset) {

        new Thread(() -> {
            if (!asset.isPromoted()) {
                LOG.debug("Promoting {} with event ecosystem....", asset.getId());
                eventService.sendEvent(new AssetEvent(operation, asset));
            } else {
                LOG.debug("Asset with ID [{}] was already promoted", asset.getId());
            }
        }).start();
    }
}
