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

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
