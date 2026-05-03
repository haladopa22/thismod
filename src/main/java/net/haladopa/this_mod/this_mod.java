package net.haladopa.this_mod;

import com.mojang.logging.LogUtils;
import net.haladopa.this_mod.block.ModBlocks;
import net.haladopa.this_mod.network.ModPacketHandler;
import net.haladopa.this_mod.client.renderer.LucasHorseRenderer;
import net.haladopa.this_mod.entity.ModEntities;
import net.haladopa.this_mod.entity.LucasHorse;
import net.haladopa.this_mod.item.modcreativemodetabs;
import net.haladopa.this_mod.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(this_mod.MODID)
public class this_mod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "this_mod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public this_mod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();


        ModPacketHandler.register();

        modcreativemodetabs.register(modEventBus);

        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.regester(modEventBus);

        modEventBus.addListener(this::registerEntityAttributes);


        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered

        // Register the Deferred Register to the mod event bus so items get registered

        // Register the Deferred Register to the mod event bus so tabs get registered


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us

    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LUCAS_HORSE.get(), SkeletonHorse.createAttributes().build());
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");


            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));




    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    if(event.getTabKey() == CreativeModeTabs.COMBAT) {
        event.accept(ModItems.STAFF);
        event.accept(ModItems.titanium);
    }

    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.LUCAS_HORSE.get(), LucasHorseRenderer::new);
        }
    }
}
