package com.collibra.gsuero.assets.controller;

import com.collibra.gsuero.assets.model.Asset;
import com.collibra.gsuero.assets.service.AssetService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssetControllerTest {

    private AssetService assetService;
    private AssetController controller;

    @BeforeAll
    void setup(@Mock AssetService assetService) {
        this.controller = new AssetController();
        this.controller.setAssetService(assetService);
        this.assetService = assetService;

        doReturn(buildAsset(1L, "an-asset"))
                .when(this.assetService).getById(eq(1L));

        doReturn(buildAsset(2L, "new-asset"))
                .when(this.assetService).create(any(Asset.class));

        doReturn(buildAsset(2L, "updated-asset"))
                .when(this.assetService).update(eq(2L), any(Asset.class));

        doNothing().when(this.assetService).delete(eq(2L));

        doNothing().when(this.assetService).promote(eq(2L));
    }

    @BeforeEach
    void init() {
        clearInvocations(this.assetService);
    }

    // TODO: Validate annotations are present at class level

    @Test
    void testGetAsset() {
        Asset asset = controller.getAsset(1L);
        assertEquals("an-asset", asset.getName());
        assertEquals(1L, asset.getId());
        verify(assetService, times(1)).getById(eq(1L));
    }

    @Test
    void testCreateAsset() {
        Asset asset = controller.create(buildAsset(2L, "new-asset"));
        assertEquals("new-asset", asset.getName());
        assertEquals(2L, asset.getId());
        verify(assetService, times(1)).create(any());
    }

    @Test
    void testUpdateAsset() {
        Asset asset = controller.update(2L, buildAsset(2L, "updated-asset"));
        assertEquals("updated-asset", asset.getName());
        assertEquals(2L, asset.getId());
        verify(assetService, times(1)).update(eq(2L), any(Asset.class));
    }

    @Test
    void testDeleteAsset() {
        controller.delete(2L);
        verify(assetService, times(1)).delete(eq(2L));
    }

    @Test
    void testPromoteAsset() {
        controller.promote(2L);
        verify(assetService, times(1)).promote(eq(2L));
    }

    private Asset buildAsset(long id, String name) {
        Asset asset = new Asset();
        asset.setName(name);
        asset.setId(id);
        asset.setPromoted(false);
        return asset;
    }
}
