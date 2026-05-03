package net.haladopa.this_mod.network;

import net.haladopa.this_mod.entity.LucasHorse;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShootArrowPacket {
    public static void encode(ShootArrowPacket msg, FriendlyByteBuf buf) {}
    public static ShootArrowPacket decode(FriendlyByteBuf buf) { return new ShootArrowPacket(); }

    public static void handle(ShootArrowPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player.getVehicle() instanceof LucasHorse horse) {
                horse.shootArrow(player);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
