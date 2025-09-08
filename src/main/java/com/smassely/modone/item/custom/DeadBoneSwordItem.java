package com.smassely.modone.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DeadBoneSwordItem extends SwordItem {
    public DeadBoneSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        WitherSkullEntity skull = new WitherSkullEntity(EntityType.WITHER_SKULL, world);

        if (user.getItemCooldownManager().isCoolingDown(this)) {
            return TypedActionResult.fail(itemStack);
        }

        if (!world.isClient) {

            skull.setPosition(user.getX(), user.getEyeY(), user.getZ());
            skull.setVelocity(user, user.getPitch(), user.getYaw(), 0f, 3f, 0f);
            world.spawnEntity(skull);


            if (!user.isCreative()) {
                if (user.getStackInHand(hand).getDamage() <= user.getStackInHand(hand).getMaxDamage() - 10) {
                    user.getStackInHand(hand).setDamage(user.getStackInHand(hand).getDamage() + 10);
                }
            }
            world.getServer().execute(() -> {
                new java.util.Timer().schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        world.getServer().execute(() -> {
                            if (!skull.isRemoved()) {
                                skull.discard();
                            }
                        });
                    }
                }, 3000); // 5000ms = 5 seconds delay
            });
            user.getItemCooldownManager().set(this, 10);
        }


        return super.use(world,user,hand);
    }
}
