package net.haladopa.this_mod.event;

import net.haladopa.this_mod.entity.LucasHorse;
import net.haladopa.this_mod.this_mod;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = this_mod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        if (!(event.getEntity() instanceof LucasHorse)) return;
        Entity camera = Minecraft.getInstance().getCameraEntity();
        if (camera != null && camera.getVehicle() == event.getEntity()) {
            event.setCanceled(true);
        }
    }
}
