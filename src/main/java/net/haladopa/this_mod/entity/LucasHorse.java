package net.haladopa.this_mod.entity;

import net.haladopa.this_mod.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayDeque;

public class LucasHorse extends SkeletonHorse {
    private static final int MAX_ARROWS = 20;
    private long lastShotTime = 0;
    private final ArrayDeque<Arrow> firedArrows = new ArrayDeque<>();

    public LucasHorse(EntityType<? extends LucasHorse> type, Level level) {
        super(type, level);
        this.setTamed(true);
    }

    @Override
    public boolean isTamed() {
        return true;
    }

    @Override
    public boolean isSaddled() {
        return true;
    }

    @Override
    public boolean isTrap() {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getVehicle() == this) {
            return InteractionResult.PASS;
        }
        if (!isWearingFullLucasSet(player)) {
            return InteractionResult.FAIL;
        }
        this.doPlayerRide(player);
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    public void shootArrow(Player player) {
        if (level().getGameTime() - lastShotTime < 3) return;
        lastShotTime = level().getGameTime();

        firedArrows.removeIf(a -> !a.isAlive());
        while (firedArrows.size() >= MAX_ARROWS) {
            Arrow oldest = firedArrows.poll();
            if (oldest != null) oldest.discard();
        }

        Arrow arrow = new Arrow(level(), this.getX(), this.getY() + this.getBbHeight() * 0.8, this.getZ());
        arrow.setOwner(player);
        Vec3 look = player.getLookAngle();
        arrow.shoot(look.x, look.y, look.z, 3.0f, 0.5f);
        level().addFreshEntity(arrow);
        firedArrows.add(arrow);
    }

    private boolean isWearingFullLucasSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.lucas_helmet.get()) &&
               player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.lucas_chestplate.get()) &&
               player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.lucas_leggings.get()) &&
               player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.lucas_boots.get());
    }
}
