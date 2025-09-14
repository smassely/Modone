package com.smassely.modone.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.random.RandomGenerator;

import com.smassely.modone.ModOne;




public class FunnyWand extends Item{

    public FunnyWand(Settings settings) {
        
        super(settings);
        
    }

    private static void FaridFight(PlayerEntity fighter){
        
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        
        Vec3d start = user.getCameraPosVec(1.0F);
        Vec3d dir = user.getRotationVec(1.0F);
        Vec3d end = start.add(dir.multiply(15.0F));
        ItemStack stack = user.getStackInHand(hand);
        int pool = 100;
        java.util.Random RNG = new java.util.Random();

        Box box = new Box(start, end);

        if (!world.isClient) {
            EntityHitResult cast = ProjectileUtil.getEntityCollision(world, user, start, end, box,  entity -> !entity.isSpectator() && entity.isAlive() && entity.canHit());

            
        
            if(cast != null && cast.getEntity().isAlive()){
                int Randint = RNG.nextInt(1, pool);
                // int Randint = 12;
                ModOne.LOGGER.info(Integer.toString(Randint));
                if (Randint<=10) {
                    cast.getEntity().kill();
                }
                else if (Randint == 11) {
                    if (user.isAlive()) {
                        user.kill();
                        user.sendMessage(Text.literal("Playing with people is to be punished").formatted(Formatting.RED).formatted(Formatting.BOLD));
                    }
                }
                else if (Randint == 12){
                    if (user.getStackInHand(hand).getDamage() < user.getStackInHand(hand).getMaxDamage()) {
                        stack.damage(stack.getMaxDamage(), user, (player) -> player.sendToolBreakStatus(hand));
                    }
                }
                else if (Randint == 13) {
                    
                }

            }

        }        


        return super.use(world, user, hand);
    }

    
    
}