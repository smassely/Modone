package com.smassely.modone.renderer;

import com.smassely.modone.ModOne;
import com.smassely.modone.entity.custom.RadianceOrb;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RadianceOrbRenderer extends EntityRenderer<com.smassely.modone.entity.custom.RadianceOrb> {
    private static final Identifier TEXTURE = new Identifier("minecraft", "entity/wither/wither_invulnerable");

    public RadianceOrbRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(RadianceOrb entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.scale(0.5f, 0.5f, 0.5f);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(getTexture(entity)));

        // Simple approach - render 6 quads to make a cube
        MatrixStack.Entry entry = matrices.peek();
        int brightness = 0xF000F0; // Full bright

        float s = 0.5f; // half size

        // Front (+Z)
        addQuad(entry, vertexConsumer, -s, -s, s, s, -s, s, s, s, s, -s, s, s, 0, 0, 1, brightness);
        // Back (-Z)
        addQuad(entry, vertexConsumer, s, -s, -s, -s, -s, -s, -s, s, -s, s, s, -s, 0, 0, -1, brightness);
        // Right (+X)
        addQuad(entry, vertexConsumer, s, -s, s, s, -s, -s, s, s, -s, s, s, s, 1, 0, 0, brightness);
        // Left (-X)
        addQuad(entry, vertexConsumer, -s, -s, -s, -s, -s, s, -s, s, s, -s, s, -s, -1, 0, 0, brightness);
        // Top (+Y)
        addQuad(entry, vertexConsumer, -s, s, s, s, s, s, s, s, -s, -s, s, -s, 0, 1, 0, brightness);
        // Bottom (-Y)
        addQuad(entry, vertexConsumer, -s, -s, -s, s, -s, -s, s, -s, s, -s, -s, s, 0, -1, 0, brightness);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void addQuad(MatrixStack.Entry entry, VertexConsumer vertices,
                         float x1, float y1, float z1, float x2, float y2, float z2,
                         float x3, float y3, float z3, float x4, float y4, float z4,
                         float nx, float ny, float nz, int light) {

        addVertex(entry, vertices, x1, y1, z1, 0, 0, nx, ny, nz, light);
        addVertex(entry, vertices, x2, y2, z2, 1, 0, nx, ny, nz, light);
        addVertex(entry, vertices, x3, y3, z3, 1, 1, nx, ny, nz, light);
        addVertex(entry, vertices, x4, y4, z4, 0, 1, nx, ny, nz, light);
    }

    private void addVertex(MatrixStack.Entry entry, VertexConsumer vertices,
                           float x, float y, float z, float u, float v,
                           float nx, float ny, float nz, int light) {
        vertices.vertex(entry.getPositionMatrix(), x, y, z)
                .color(255, 255, 255, 255)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(entry.getNormalMatrix(), nx, ny, nz)
                .next();
    }

    @Override
    public Identifier getTexture(RadianceOrb entity) {
        return TEXTURE;
    }
}