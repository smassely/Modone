package com.smassely.modone;

import com.smassely.modone.entity.ModEntities;
import com.smassely.modone.renderer.FaridEntityRenderer;
import com.smassely.modone.renderer.RadianceOrbRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModOneClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.RADIANCEORB, RadianceOrbRenderer::new);
        EntityRendererRegistry.register(ModEntities.FARID, FaridEntityRenderer::new);
    }
}