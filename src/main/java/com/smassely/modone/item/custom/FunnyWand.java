package com.smassely.modone.item.custom;

import com.smassely.modone.ModOne;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;


public class FunnyWand extends Item{

    public FunnyWand(Settings settings) {
        
        super(settings);
        
    }

    public static void makeHollowCube(World world, BlockPos origin, int size, BlockState state, boolean clear) {
        if (world.isClient) return;
        int x0 = origin.getX();
        int y0 = origin.getY();
        int z0 = origin.getZ();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int x = 1; x < size - 1; x++) {
            for (int y = 1; y < size - 1; y++) {
                for (int z = 1; z < size - 1; z++) {
                    pos.set(x0 + x, y0 + y, z0 + z);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                }
            }
        }
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    int bx = origin.getX() + x;
                    int by = origin.getY() + y;
                    int bz = origin.getZ() + z;

                    pos.set(bx, by, bz);

                    if (x == 0 || x == size - 1 ||
                        y == 0 || y == size - 1 ||
                        z == 0 || z == size - 1) {
                    
                        world.setBlockState(pos, state, 3);
                    } 
                }
            }
        }
    }


    private static void FaridFight(Entity fighter, World world){
        if (world.isClient) return;
        fighter.teleport(fighter.getPos().x, fighter.getPos().y+4, fighter.getPos().z);
        int boxlength = 9;

        BlockState pristate = Blocks.OBSIDIAN.getDefaultState();
        BlockState state = Blocks.BLACK_CONCRETE.getDefaultState();
        int pposx = (int) Math.floor(fighter.getPos().x-(boxlength/2));
        int pposy = (int) fighter.getPos().y-4;
        int pposz = (int) fighter.getPos().z-(boxlength-7);

        BlockPos pos = new BlockPos(pposx,pposy,pposz);
        
        makeHollowCube(world, pos, boxlength, pristate, true);
        if (fighter.isPlayer()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) fighter;
            serverPlayer.networkHandler.requestTeleport(
            fighter.getX(),
            fighter.getY(),
            fighter.getZ(),
        0f,
        0f   
            );~
        } 
        else{
            fighter.setPitch(0f);
            fighter.setYaw(0f);
        }
        ((LivingEntity) fighter).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,
            40,
            2,
            false,
            false,
            false));
        
        WardenEntity farid = new WardenEntity(EntityType.WARDEN, world);
        farid.initialize(
            (ServerWorldAccess) world,
            world.getLocalDifficulty(farid.getBlockPos()),
            SpawnReason.TRIGGERED, 
            null, 
            null
        );   
        farid.setCustomName(Text.literal("Farid").formatted(Formatting.RED, Formatting.BOLD));
        farid.setCustomNameVisible(true);
        farid.setPersistent();
        farid.setPose(EntityPose.STANDING);
        
        if (farid != null) {
            farid.refreshPositionAndAngles(
                fighter.getX(),
                fighter.getY() + 1,
                fighter.getZ() + 4,
            0f, 0f
        );

        farid.addStatusEffect(new StatusEffectInstance(
            StatusEffects.GLOWING,
        200,  
            1,   
            false, 
            false, 
            false  
        ));

        world.spawnEntity(farid);
}

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
                // int Randint = RNG.nextInt(1, pool);
                int Randint = 14;
                ModOne.LOGGER.info(Integer.toString(Randint));
                if (Randint<=10) {
                    EntityType<?> type = cast.getEntity().getType();
                    String typeName = EntityType.getId(type).toString(); 
                    user.sendMessage(Text.literal("Farid joined the game").formatted(Formatting.YELLOW), false);
                    cast.getEntity().kill();
                    user.sendMessage(Text.literal(type.getName().getString() +  " was raped by Farid"), false);
                    user.sendMessage(Text.literal("Farid left the game").formatted(Formatting.YELLOW), false);
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
                else if (Randint >= 13 && Randint <= 15) {
                    FaridFight(user, world);   
                }
            }
        }
        return super.use(world, user, hand);
    }
}