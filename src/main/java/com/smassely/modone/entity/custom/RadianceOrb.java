package com.smassely.modone.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class RadianceOrb extends ExplosiveProjectileEntity {
    public RadianceOrb(EntityType<RadianceOrb> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void onCollision(HitResult hitResult) {
        World world = this.getWorld();
        if(!world.isClient){
            world.createExplosion(this, this.getX(),this.getY(), this.getZ(), 3f, World.ExplosionSourceType.TNT);
        }
        this.discard();
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();

        if (!this.getWorld().isClient){
            entity.damage(this.getWorld().getDamageSources().generic(),5f);
            this.getWorld().createExplosion(this, this.getX(),this.getY(),this.getZ(),3f,World.ExplosionSourceType.TNT);
        }

    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient){
            for (int i = 0; i < 2; i++) {
                         this.getWorld().addParticle(ParticleTypes.END_ROD,
                             this.getX() + (this.random.nextDouble() - 0.5) * 0.3,
                             this.getY() + (this.random.nextDouble() - 0.5) * 0.3,
                             this.getZ() + (this.random.nextDouble() - 0.5) * 0.3,
                             0, 0, 0);
                        this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK,
                            this.getX() + (this.random.nextDouble() - 0.5) * 0.3,
                            this.getY() + (this.random.nextDouble() - 0.5) * 0.3,
                            this.getZ() + (this.random.nextDouble() - 0.5) * 0.3,
                            0, 0, 0);
                     }

        }

    }

    @Override
    protected void initDataTracker() {

    }
}
