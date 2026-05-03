package net.haladopa.this_mod.event;

import net.haladopa.this_mod.entity.LucasHorse;
import net.haladopa.this_mod.network.ModPacketHandler;
import net.haladopa.this_mod.network.ShootArrowPacket;
import net.haladopa.this_mod.this_mod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = this_mod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {
    private static int cooldown = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (cooldown > 0) { cooldown--; return; }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (!(mc.player.getVehicle() instanceof LucasHorse)) return;
        if (!mc.options.keyAttack.isDown()) return;

        cooldown = 3;
        ModPacketHandler.CHANNEL.sendToServer(new ShootArrowPacket());
    }
}
