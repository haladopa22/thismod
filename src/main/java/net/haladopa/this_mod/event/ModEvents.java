package net.haladopa.this_mod.event;

import net.haladopa.this_mod.entity.LucasHorse;
import net.haladopa.this_mod.entity.ModEntities;
import net.haladopa.this_mod.this_mod;
import net.haladopa.this_mod.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = this_mod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final Map<UUID, UUID> playerHorseMap = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (player.isCreative() || player.isSpectator()) return;

        // --- Daniel suit flight ---
        boolean wearingFullSuit =
                player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.danielhelmate.get()) &&
                player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.danielcheastplate.get()) &&
                player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.danielleggings.get()) &&
                player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.danielboots.get());

        if (player.getAbilities().mayfly != wearingFullSuit) {
            player.getAbilities().mayfly = wearingFullSuit;
            if (!wearingFullSuit && player.getAbilities().flying) {
                player.getAbilities().flying = false;
            }
            player.onUpdateAbilities();
        }

        // --- Lucas horse management ---
        boolean wearingLucasSet = isWearingFullLucasSet(player);
        boolean inWater = player.isInWater();
        LucasHorse horse = getTrackedHorse(player);

        if (horse != null && horse.isAlive()) {
            if (!wearingLucasSet || inWater) {
                horse.discard();
                playerHorseMap.remove(player.getUUID());
            }
        } else {
            playerHorseMap.remove(player.getUUID());
            if (wearingLucasSet && !inWater) {
                ServerLevel level = (ServerLevel) player.level();
                LucasHorse newHorse = new LucasHorse(ModEntities.LUCAS_HORSE.get(), level);
                newHorse.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), 0);
                level.addFreshEntity(newHorse);
                playerHorseMap.put(player.getUUID(), newHorse.getUUID());
            }
        }
    }

    private static LucasHorse getTrackedHorse(ServerPlayer player) {
        UUID horseUUID = playerHorseMap.get(player.getUUID());
        if (horseUUID == null) return null;
        Entity entity = ((ServerLevel) player.level()).getEntity(horseUUID);
        return entity instanceof LucasHorse ? (LucasHorse) entity : null;
    }

    private static boolean isWearingFullLucasSet(ServerPlayer player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.lucas_helmet.get()) &&
               player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.lucas_chestplate.get()) &&
               player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.lucas_leggings.get()) &&
               player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.lucas_boots.get());
    }
}
