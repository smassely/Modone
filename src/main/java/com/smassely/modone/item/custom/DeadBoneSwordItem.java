package com.smassely.modone.item.custom;

import net.minecraft.entity.EntityType;
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

        // Check if item is on cooldown
        if (user.getItemCooldownManager().isCoolingDown(this)) {
            return TypedActionResult.fail(itemStack);
        }

        if (!world.isClient) {
            WitherSkullEntity skull = new WitherSkullEntity(EntityType.WITHER_SKULL, world);
            skull.setPosition(user.getX(), user.getEyeY(), user.getZ());
            skull.setOwner(user);
            skull.setVelocity(user, user.getPitch(), user.getYaw(), 0f, 3f, 0f);
            world.spawnEntity(skull);


            if (!user.isCreative()) {
                if (user.getStackInHand(hand).getDamage() <= user.getStackInHand(hand).getMaxDamage() - 1) {
                    user.getStackInHand(hand).setDamage(user.getStackInHand(hand).getDamage() + 1);
                }
            }

            user.getItemCooldownManager().set(this, 15);
        }

        return super.use(world,user,hand);
    }
}
