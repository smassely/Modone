package com.smassely.modone.entity.custom;

import java.util.List;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class FaridEntity extends WardenEntity{

    int shotCooldown = 200;

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

        if (shotCooldown >0){
            shotCooldown--;
        }
        if (shotCooldown == 1){

        }
    
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


    private void ranged(int r){
        ServerPlayerEntity target = selectTraget(r);
        
    }

    private ServerPlayerEntity selectTraget(int r){
        ServerPlayerEntity theChosenOne = null;
        List<ServerPlayerEntity> nearbyPlayers = this.getWorld().getEntitiesByClass(
            ServerPlayerEntity.class, 
            this.getBoundingBox().expand(r),
            player -> player.isAlive() && !player.isSpectator()
        );

        if (!nearbyPlayers.isEmpty()) {
            theChosenOne = nearbyPlayers.get(this.random.nextInt(nearbyPlayers.size()));
        }
        return theChosenOne;

    }
}