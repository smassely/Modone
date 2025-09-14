package com.smassely.modone.item.custom;

import com.smassely.modone.ModOne;

import net.minecraft.entity.EntityType;
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




public class FunnyWand extends Item{

    public FunnyWand(Settings settings) {
        super(settings);
        
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        Vec3d start = user.getCameraPosVec(1.0F);
        Vec3d dir = user.getRotationVec(1.0F);
        Vec3d end = start.add(dir.multiply(15.0F));
        
        int pool = 100;
        java.util.Random RNG = new java.util.Random();

        Box box = new Box(start, end);

        if (!world.isClient) {
            EntityHitResult cast = ProjectileUtil.getEntityCollision(world, user, start, end, box,  entity -> !entity.isSpectator() && entity.isAlive() && entity.canHit());

            
        
            if(cast != null && cast.getEntity().isAlive()){
                int Randint = RNG.nextInt(1, pool);
                ModOne.LOGGER.info(Integer.toString(Randint));
                if (Randint<=10) {
                    EntityType<?> type = cast.getEntity().getType();
                    String typeName = EntityType.getId(type).toString(); 
                    user.sendMessage(Text.literal("Farid joined the game").formatted(Formatting.YELLOW), false);
                    cast.getEntity().kill();
                    user.sendMessage(Text.literal(type.getName().getString() +  " was raped by Farid"), false);
                    user.sendMessage(Text.literal("Farid left the game").formatted(Formatting.YELLOW), false);
                }

            }

        }        


        return super.use(world, user, hand);
    }

    
    
}