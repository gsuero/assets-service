package com.collibra.gsuero.assets.service;

import com.collibra.gsuero.assets.error.NotFoundException;
import com.collibra.gsuero.assets.model.Asset;
import com.collibra.gsuero.assets.model.domain.AssetDao;
import com.collibra.gsuero.assets.repository.AssetRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssetServiceTest {

    private AssetService assetService;
    private AssetRepository repository;
    private EventService eventService;

    @BeforeAll
    void setup(@Mock AssetRepository repository, @Mock EventService eventService) {
        this.assetService = new AssetService();
        this.repository = repository;
        this.eventService = eventService;

        this.assetService.setEventService(eventService);
        this.assetService.setRepository(repository);

        doReturn(Optional.of(buildAssetDao(1L, "get-asset")))
                .when(repository).findById(eq(1L));

        doReturn(buildAssetDao(2L, "saved-asset"))
                .when(repository).save(any());

        AssetDao childrenDao = buildAssetDao(3L, "promote-asset");
        childrenDao.setParent(buildAssetDao(1L, "get-asset"));

        doReturn(Optional.of(childrenDao))
                .when(repository).findById(eq(3L));

        doReturn(List.of(buildAssetDao(2L, "saved-asset"))).when(repository).findByParentId(eq(1L));

    }

    @BeforeEach
    void init() {
        clearInvocations(this.repository, this.eventService);
    }

    @Test
    void testGetById() {
        Asset asset = assetService.getById(1L);
        assertEquals("get-asset", asset.getName());
        assertEquals(1L, asset.getId());
        verify(repository, times(1)).findById(eq(1L));
    }

    @Test
    void testUpdate() {
        Asset update = new Asset();
        update.setName("new-name");
        Asset asset = assetService.update(1L, update);

        verify(repository, times(1)).findById(eq(1L));

        ArgumentCaptor<AssetDao> assetCaptor = ArgumentCaptor.forClass(AssetDao.class);

        verify(repository, times(1)).save(assetCaptor.capture());

        assertEquals("new-name", assetCaptor.getValue().getName());
    }


    @Test
    void testCreate() {
        Asset asset = assetService.create(buildAsset(2L, "saved-asset"));
        assertEquals("saved-asset", asset.getName());
        assertEquals(2L, asset.getId());

        ArgumentCaptor<AssetDao> assetDaoArgumentCaptor = ArgumentCaptor.forClass(AssetDao.class);

        verify(repository, times(1)).save(assetDaoArgumentCaptor.capture());

        assertTrue(assetDaoArgumentCaptor.getValue() instanceof AssetDao);
        assertEquals(assetDaoArgumentCaptor.getValue().getId(), 2L);
        assertEquals(assetDaoArgumentCaptor.getValue().getName(), "saved-asset");
    }

    @Test
    void testPromote() {
        assetService.promote(1L);

        verify(repository, times(1)).findById(eq(1L));
        verify(repository, times(1)).findByParentId(eq(1L));
        // verify(eventService, times(2)).sendEvent(any());

    }

    @Test
    void testPromoteNotFound() {
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> assetService.promote(4L));

        Assertions.assertNotNull(exception);

    }

    @Test
    void testPromoteAlreadyPromoted() {
        AssetDao dao = buildAssetDao(1L, "get-asset");
        dao.setPromoted(true);
        doReturn(Optional.of(dao))
                .when(repository).findById(eq(1L));

        assetService.promote(1L);

        verify(repository, times(1)).findById(eq(1L));
        verify(repository, times(0)).findByParentId(eq(1L));
        verify(eventService, times(0)).sendEvent(any());

    }

    @Test
    void testDelete() {
        assetService.delete(1L);
        verify(repository, times(1)).deleteById(eq(1L));
    }


    private AssetDao buildAssetDao(long id, String name) {
        AssetDao asset = new AssetDao();
        asset.setName(name);
        asset.setId(id);
        asset.setPromoted(false);
        return asset;
    }

    private Asset buildAsset(long id, String name) {
        Asset asset = new Asset();
        asset.setName(name);
        asset.setId(id);
        asset.setPromoted(false);
        return asset;
    }
}
