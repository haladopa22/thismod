package net.haladopa.this_mod.item;

import net.haladopa.this_mod.this_mod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class modcreativemodetabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, this_mod.MODID);


    public static final RegistryObject<CreativeModeTab> this_tab = CREATIVE_MODE_TABS.register("this_tab",
            ()-> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.STAFF.get()))

                    .title(Component.translatable("creativetab.this_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.TITANIUM_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.STAFF.get());
                        pOutput.accept(ModItems.titanium.get());
                        pOutput.accept(ModItems.MOO_COLA.get());
                        pOutput.accept(ModItems.danielhelmate.get());
                        pOutput.accept(ModItems.danielcheastplate.get());
                        pOutput.accept(ModItems.danielleggings.get());
                        pOutput.accept(ModItems.danielboots.get());
                        pOutput.accept(ModItems.tom_helmet.get());
                        pOutput.accept(ModItems.tom_chestplate.get());
                        pOutput.accept(ModItems.tom_leggings.get());
                        pOutput.accept(ModItems.tom_boots.get());
                        pOutput.accept(ModItems.lucas_helmet.get());
                        pOutput.accept(ModItems.lucas_chestplate.get());
                        pOutput.accept(ModItems.lucas_leggings.get());
                        pOutput.accept(ModItems.lucas_boots.get());
                        pOutput.accept(ModItems.finn_helmet.get());
                        pOutput.accept(ModItems.finn_chestplate.get());
                        pOutput.accept(ModItems.finn_leggings.get());
                        pOutput.accept(ModItems.finn_boots.get());
                        pOutput.accept(ModItems.LASER_GUN.get());
                        pOutput.accept(ModItems.merry_helmet.get());
                        pOutput.accept(ModItems.merry_chestplate.get());
                        pOutput.accept(ModItems.merry_leggings.get());
                        pOutput.accept(ModItems.merry_boots.get());
                        pOutput.accept(ModItems.harry_helmet.get());
                        pOutput.accept(ModItems.harry_chestplate.get());
                        pOutput.accept(ModItems.harry_leggings.get());
                        pOutput.accept(ModItems.harry_boots.get());
                        pOutput.accept(ModItems.olly_helmet.get());
                        pOutput.accept(ModItems.olly_chestplate.get());
                        pOutput.accept(ModItems.olly_leggings.get());
                        pOutput.accept(ModItems.olly_boots.get());
                    })







                    .build());



    public static void register(IEventBus eventBus){

        CREATIVE_MODE_TABS.register(eventBus);
    }
}
