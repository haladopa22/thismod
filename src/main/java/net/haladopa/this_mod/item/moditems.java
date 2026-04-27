package net.haladopa.this_mod.item;

import net.haladopa.this_mod.this_mod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class moditems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, this_mod.MODID);

    public static final RegistryObject<Item> STAFF = ITEMS.register("staff",
            () -> new Item(new Item.Properties()));

    public static void regester(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
