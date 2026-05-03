package net.haladopa.this_mod.network;

import net.haladopa.this_mod.entity.LaserBoltEntity;
import net.haladopa.this_mod.entity.ModEntities;
import net.haladopa.this_mod.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShootLaserPacket {
    public static void encode(ShootLaserPacket msg, FriendlyByteBuf buf) {}
    public static ShootLaserPacket decode(FriendlyByteBuf buf) { return new ShootLaserPacket(); }

    public static void handle(ShootLaserPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            if (!isWearingFullFinnSet(player)) return;

            ServerLevel level = (ServerLevel) player.level();
            Vec3 dir = player.getLookAngle();
            LaserBoltEntity bolt = new LaserBoltEntity(ModEntities.LASER_BOLT.get(), level);
            bolt.setOwner(player);
            bolt.setPos(
                    player.getX() + dir.x * 0.8,
                    player.getEyeY() - 0.1,
                    player.getZ() + dir.z * 0.8
            );
            bolt.setDeltaMovement(dir.scale(3.0));
            level.addFreshEntity(bolt);
        });
        ctx.get().setPacketHandled(true);
    }

    private static boolean isWearingFullFinnSet(ServerPlayer player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.finn_helmet.get()) &&
               player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.finn_chestplate.get()) &&
               player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.finn_leggings.get()) &&
               player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.finn_boots.get());
    }
}
