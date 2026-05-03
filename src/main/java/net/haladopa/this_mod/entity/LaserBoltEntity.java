package net.haladopa.this_mod.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import net.minecraft.core.particles.ParticleTypes;

public class LaserBoltEntity extends ThrowableProjectile {

    private static final int MAX_LIFETIME = 30;

    public LaserBoltEntity(EntityType<? extends LaserBoltEntity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        Vec3 vel = this.getDeltaMovement();
        super.tick();
        // Restore velocity after super to cancel drag (laser travels at constant speed)
        this.setDeltaMovement(vel);

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIT,
                    this.getX(), this.getY(), this.getZ(), 3, 0.05, 0.05, 0.05, 0.0);
        }

        if (this.tickCount > MAX_LIFETIME) this.discard();
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        if (entity == this.getOwner()) return false;
        return super.canHitEntity(entity);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide) {
            Entity hit = result.getEntity();
            if (hit instanceof LivingEntity living) {
                living.hurt(this.damageSources().thrown(this, this.getOwner()), 8.0f);
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {}

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {}
}
