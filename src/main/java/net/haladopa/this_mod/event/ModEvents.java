package net.haladopa.this_mod.event;

import net.haladopa.this_mod.entity.DanielDrone;
import net.haladopa.this_mod.entity.LucasHorse;
import net.haladopa.this_mod.entity.ModEntities;
import net.haladopa.this_mod.this_mod;
import net.haladopa.this_mod.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = this_mod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final Map<UUID, UUID> playerHorseMap = new HashMap<>();
    private static final Map<UUID, List<UUID>> playerDroneMap = new HashMap<>();

    private static final UUID TOM_HEALTH_UUID = UUID.fromString("a8d4f2b1-3c7e-4a9f-b2d5-1e8f3c6a7b4d");

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

        // --- Daniel drones ---
        manageDanielDrones(player, wearingFullSuit);

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

        // --- Tom suit shrink ---
        boolean wearingTomSet = isWearingFullTomSet(player);
        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);

        if (wearingTomSet) {
            if (maxHealth != null && maxHealth.getModifier(TOM_HEALTH_UUID) == null) {
                maxHealth.addPermanentModifier(new AttributeModifier(TOM_HEALTH_UUID,
                        "tom_shrink_health", -10.0, AttributeModifier.Operation.ADDITION));
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth((float) player.getMaxHealth());
                }
            }
            // Every 10 ticks, clear mob targets that are beyond 1.5 blocks
            if (player.tickCount % 10 == 0) {
                player.level().getEntitiesOfClass(Mob.class,
                        player.getBoundingBox().inflate(32),
                        mob -> mob.getTarget() == player && mob.distanceTo(player) > 1.5
                ).forEach(mob -> mob.setTarget(null));
            }
        } else {
            if (maxHealth != null && maxHealth.getModifier(TOM_HEALTH_UUID) != null) {
                maxHealth.removeModifier(TOM_HEALTH_UUID);
            }
        }
    }

    @SubscribeEvent
    public static void onEntitySize(EntityEvent.Size event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.getInventory() == null) return; // fired before inventory is initialized during Entity.<init>
        if (!isWearingFullTomSet(player)) return;
        event.setNewSize(EntityDimensions.scalable(0.4f, 0.9f), true);
    }

    private static void manageDanielDrones(ServerPlayer player, boolean wearingDanielSet) {
        ServerLevel level = (ServerLevel) player.level();
        List<UUID> droneUUIDs = playerDroneMap.get(player.getUUID());

        if (wearingDanielSet) {
            if (!areDronesValid(player, droneUUIDs, level)) {
                discardDrones(droneUUIDs, level);
                List<UUID> newUUIDs = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    DanielDrone drone = new DanielDrone(ModEntities.DANIEL_DRONE.get(), level);
                    drone.setOwnerUUID(player.getUUID());
                    drone.setOrbitIndex(i);
                    double angle = 2 * Math.PI * i / 5.0;
                    drone.moveTo(
                            player.getX() + 2.5 * Math.cos(angle),
                            player.getY() + 2,
                            player.getZ() + 2.5 * Math.sin(angle),
                            0, 0);
                    level.addFreshEntity(drone);
                    newUUIDs.add(drone.getUUID());
                }
                playerDroneMap.put(player.getUUID(), newUUIDs);
            }
        } else {
            discardDrones(droneUUIDs, level);
            playerDroneMap.remove(player.getUUID());
        }
    }

    private static boolean areDronesValid(ServerPlayer player, List<UUID> droneUUIDs, ServerLevel level) {
        if (droneUUIDs == null || droneUUIDs.size() != 5) return false;
        for (UUID uuid : droneUUIDs) {
            Entity e = level.getEntity(uuid);
            if (!(e instanceof DanielDrone) || !e.isAlive()) return false;
        }
        return true;
    }

    private static void discardDrones(List<UUID> droneUUIDs, ServerLevel level) {
        if (droneUUIDs == null) return;
        droneUUIDs.stream()
                .map(level::getEntity)
                .filter(Objects::nonNull)
                .forEach(Entity::discard);
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

    public static boolean isWearingFullTomSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.tom_helmet.get()) &&
               player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.tom_chestplate.get()) &&
               player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.tom_leggings.get()) &&
               player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.tom_boots.get());
    }
}
