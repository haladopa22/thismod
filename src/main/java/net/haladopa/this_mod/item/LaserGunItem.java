package net.haladopa.this_mod.item;

import net.haladopa.this_mod.entity.LaserBoltEntity;
import net.haladopa.this_mod.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LaserGunItem extends Item {

    public LaserGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && isWearingFullFinnSet(player)) {
            ServerLevel serverLevel = (ServerLevel) level;
            Vec3 dir = player.getLookAngle();
            LaserBoltEntity bolt = new LaserBoltEntity(ModEntities.LASER_BOLT.get(), serverLevel);
            bolt.setOwner(player);
            bolt.setPos(
                    player.getX() + dir.x * 0.8,
                    player.getEyeY() - 0.1,
                    player.getZ() + dir.z * 0.8
            );
            bolt.setDeltaMovement(dir.scale(3.0));
            serverLevel.addFreshEntity(bolt);
        }

        player.getCooldowns().addCooldown(this, 15);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private boolean isWearingFullFinnSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.finn_helmet.get()) &&
               player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.finn_chestplate.get()) &&
               player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.finn_leggings.get()) &&
               player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.finn_boots.get());
    }
}
