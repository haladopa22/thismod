package net.haladopa.this_mod.entity;

import net.haladopa.this_mod.this_mod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, this_mod.MODID);

    public static final RegistryObject<EntityType<LucasHorse>> LUCAS_HORSE =
            ENTITY_TYPES.register("lucas_horse", () ->
                    EntityType.Builder.<LucasHorse>of(LucasHorse::new, MobCategory.CREATURE)
                            .sized(1.4f, 1.6f)
                            .build("lucas_horse"));

    public static final RegistryObject<EntityType<DanielDrone>> DANIEL_DRONE =
            ENTITY_TYPES.register("daniel_drone", () ->
                    EntityType.Builder.<DanielDrone>of(DanielDrone::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build("daniel_drone"));

    public static final RegistryObject<EntityType<LaserBoltEntity>> LASER_BOLT =
            ENTITY_TYPES.register("laser_bolt", () ->
                    EntityType.Builder.<LaserBoltEntity>of(LaserBoltEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .build("laser_bolt"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
