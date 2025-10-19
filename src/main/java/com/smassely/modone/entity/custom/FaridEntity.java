package com.smassely.modone.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class FaridEntity extends WardenEntity{

    public FaridEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(1000.0D);
        this.setHealth(this.getMaxHealth());
    }
    
    private final ServerBossBar FaridBar = new ServerBossBar(
            Text.literal("Farid").formatted(Formatting.RED, Formatting.BOLD),
            BossBar.Color.RED,
            BossBar.Style.PROGRESS);

    
    @Override
    public void tick() {
        super.tick();
        this.FaridBar.setPercent(this.getHealth() / this.getMaxHealth());
        String title = String.format("§c§lFARID §r(%.0f / %.0f)", this.getHealth(), this.getMaxHealth());
        this.FaridBar.setName(Text.literal(title));
    
    }
    
    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.FaridBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.FaridBar.removePlayer(player);
    }

}