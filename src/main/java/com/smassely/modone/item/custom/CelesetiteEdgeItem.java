package com.smassely.modone.item.custom;

import com.smassely.modone.entity.ModEntities;
import com.smassely.modone.entity.custom.RadianceOrb;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CelesetiteEdgeItem extends SwordItem {
    public CelesetiteEdgeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        RadianceOrb radiance = new RadianceOrb(ModEntities.RADIANCEORB, world);

        if (user.getItemCooldownManager().isCoolingDown(this)) {
            return TypedActionResult.fail(itemStack);
        }

        if (!world.isClient) {

            radiance.setPosition(user.getX(), user.getEyeY(), user.getZ());
            radiance.setVelocity(user, user.getPitch(),user.getYaw(), 0f,3f,0f);

            world.spawnEntity(radiance);


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
                            if (!radiance.isRemoved()) {
                                radiance.discard();
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
