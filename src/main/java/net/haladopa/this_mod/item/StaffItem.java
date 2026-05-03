package net.haladopa.this_mod.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class StaffItem extends Item {
    public StaffItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide()) return InteractionResultHolder.pass(stack);
        if (!isWearingFullMerrySet(player)) return InteractionResultHolder.pass(stack);

        Vec3 playerPos = player.position();
        AABB searchBox = player.getBoundingBox().inflate(10.0);
        List<Mob> mobs = level.getEntitiesOfClass(Mob.class, searchBox);

        for (Mob mob : mobs) {
            Vec3 direction = mob.position().subtract(playerPos).normalize();
            mob.setDeltaMovement(direction.x * 2.0, 0.5, direction.z * 2.0);
            mob.hasImpulse = true;
        }

        player.getCooldowns().addCooldown(this, 40);
        return InteractionResultHolder.success(stack);
    }

    private boolean isWearingFullMerrySet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.merry_helmet.get()) &&
               player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.merry_chestplate.get()) &&
               player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.merry_leggings.get()) &&
               player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.merry_boots.get());
    }
}
