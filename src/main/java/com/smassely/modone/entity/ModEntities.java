package com.smassely.modone.entity;

import com.smassely.modone.ModOne;
import com.smassely.modone.entity.custom.RadianceOrb;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<RadianceOrb> RADIANCEORB = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModOne.MOD_ID, "radiance_orb"),
            FabricEntityTypeBuilder.<RadianceOrb>create(SpawnGroup.MISC, RadianceOrb::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)) // size of the projectile
//                    .trackRangeBlocks(64)
//                    .trackedUpdateRate(10)
                    .build()
    );


    public static void register() {
        // Ensure this class is loaded
    }
}
