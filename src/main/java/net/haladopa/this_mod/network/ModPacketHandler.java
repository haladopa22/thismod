package net.haladopa.this_mod.network;

import net.haladopa.this_mod.this_mod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPacketHandler {
    private static final String PROTOCOL = "1";

    @SuppressWarnings("deprecation")
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(this_mod.MODID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void register() {
        CHANNEL.messageBuilder(ShootArrowPacket.class, 0)
                .encoder(ShootArrowPacket::encode)
                .decoder(ShootArrowPacket::decode)
                .consumerMainThread(ShootArrowPacket::handle)
                .add();
    }
}
