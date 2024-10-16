package net.pixit.oooze.dye;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pixit.oooze.Oooze;

public class DyeBall extends ThrownItemEntity {

    public DyeBall(EntityType<? extends ThrownItemEntity> entityEntityType, World world) { super(entityEntityType, world); }

    public DyeBall(World world, LivingEntity owner) { super(Oooze.DYE_BALL_ENTITY_TYPE, owner, world); }

    public DyeBall(World world, double x, double y, double z) { super(Oooze.DYE_BALL_ENTITY_TYPE, x, y, z, world); }

    protected Item getDefaultItem() { return Items.AIR; }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        // change color of sheep if hit
        super.onEntityHit(entityHitResult);
        Entity sheep = entityHitResult.getEntity();
        if (sheep instanceof SheepEntity sheepEntity) {
            if (sheepEntity.isAlive() && !sheepEntity.isSheared() && sheepEntity.getColor() != this.getColor()) {
                sheepEntity.getWorld().playSoundFromEntity((PlayerEntity) this.getOwner(), sheepEntity, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                if (!getWorld().isClient) {
                    sheepEntity.setColor(this.getColor());
                }
            }
        } else {
            this.dropItem(this.getItem().getItem());
        }
    }

    /*protected void onBlockHit(BlockHitResult blockHitResult) {
        // change color of block if valid block to change color
        super.onBlockHit(blockHitResult);
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState bs = this.getWorld().getBlockState(pos);

        
    }*/

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    public DyeColor getColor() {
        return ((DyeItem) this.getItem().getItem()).getColor();
    }
}
