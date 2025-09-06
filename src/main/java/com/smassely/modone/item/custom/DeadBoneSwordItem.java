package com.smassely.modone.item.custom;

import com.smassely.modone.ModOne;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.ibm.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class DeadBoneSwordItem extends SwordItem {
    public DeadBoneSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient){
            WitherSkullEntity skul = new WitherSkullEntity(EntityType.WITHER_SKULL, world);
            skul.setPosition(user.getX(), user.getEyeY(), user.getZ());
            skul.setOwner(user);
            skul.setVelocity(user, user.getPitch(), user.getYaw(),0f,3f,0f);

            world.spawnEntity(skul);
            ModOne.LOGGER.info("clicked");
            if (!user.isCreative()){


            }
        }

        return super.use(world, user, hand);
    }
}
