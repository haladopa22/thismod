package net.haladopa.this_mod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

public class DanielDrone extends PathfinderMob {

    @Nullable private UUID ownerUUID;
    private int orbitIndex;

    public DanielDrone(EntityType<? extends DanielDrone> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setNoGravity(true);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FLYING_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 10.0);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        return nav;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new DroneOrbitGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, false, false,
                target -> this.ownerUUID == null || !target.getUUID().equals(this.ownerUUID)));
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.ownerUUID = uuid;
    }

    @Nullable
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOrbitIndex(int index) {
        this.orbitIndex = index;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (ownerUUID != null) tag.putUUID("DroneOwner", ownerUUID);
        tag.putInt("OrbitIndex", orbitIndex);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("DroneOwner")) ownerUUID = tag.getUUID("DroneOwner");
        orbitIndex = tag.getInt("OrbitIndex");
    }

    private static class DroneOrbitGoal extends Goal {
        private final DanielDrone drone;

        DroneOrbitGoal(DanielDrone drone) {
            this.drone = drone;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return drone.ownerUUID != null && drone.getTarget() == null;
        }

        @Override
        public void tick() {
            if (!(drone.level() instanceof ServerLevel level)) return;
            Entity owner = level.getEntity(drone.ownerUUID);
            if (owner == null) return;
            long gameTime = level.getGameTime();
            double angle = (2 * Math.PI * drone.orbitIndex / 5.0) + (gameTime * 0.04);
            double radius = 2.5;
            double x = owner.getX() + radius * Math.cos(angle);
            double z = owner.getZ() + radius * Math.sin(angle);
            double y = owner.getY() + 1.5 + Math.sin((gameTime * 0.02) + drone.orbitIndex) * 0.5;
            drone.getMoveControl().setWantedPosition(x, y, z, 1.0);
        }
    }
}
