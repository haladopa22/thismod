package net.haladopa.this_mod.item;

import net.haladopa.this_mod.block.ModBlocks;
import net.haladopa.this_mod.this_mod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, this_mod.MODID);

    public static final RegistryObject<Item> TITANIUM_BLOCK_ITEM = ITEMS.register("titanium_block",
            () -> new BlockItem(ModBlocks.TITANIUM_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> STAFF = ITEMS.register("staff",
            () -> new StaffItem(new Item.Properties()));
    public static final RegistryObject<Item> LASER_GUN = ITEMS.register("laser_gun",
            () -> new LaserGunItem(new Item.Properties()));
    public static final RegistryObject<Item> MOO_COLA = ITEMS.register("moo_cola",
            () -> new MooColaItem(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(6).saturationMod(0.3f)
                    .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0), 1.0f)
                    .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0), 1.0f)
                    .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0), 1.0f)
                    .build())));
    public static final RegistryObject<Item> titanium = ITEMS.register("titanium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> danielhelmate = ITEMS.register("tophat",
            () -> new ArmorItem(ModArmorMaterials.titanium, ArmorItem.Type.HELMET,new Item.Properties()));
    public static final RegistryObject<Item> danielcheastplate = ITEMS.register("shirt",
            () -> new ArmorItem(ModArmorMaterials.titanium, ArmorItem.Type.CHESTPLATE,new Item.Properties()));
    public static final RegistryObject<Item> danielleggings = ITEMS.register("trousers",
            () -> new ArmorItem(ModArmorMaterials.titanium, ArmorItem.Type.LEGGINGS,new Item.Properties()));
    public static final RegistryObject<Item> danielboots = ITEMS.register("shoes",
            () -> new ArmorItem(ModArmorMaterials.titanium, ArmorItem.Type.BOOTS,new Item.Properties()));

    public static final RegistryObject<Item> tom_helmet = ITEMS.register("tom_helmet",
            () -> new ArmorItem(ModArmorMaterials.tom, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> tom_chestplate = ITEMS.register("tom_chestplate",
            () -> new ArmorItem(ModArmorMaterials.tom, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> tom_leggings = ITEMS.register("tom_leggings",
            () -> new ArmorItem(ModArmorMaterials.tom, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> tom_boots = ITEMS.register("tom_boots",
            () -> new ArmorItem(ModArmorMaterials.tom, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> lucas_helmet = ITEMS.register("lucas_helmet",
            () -> new ArmorItem(ModArmorMaterials.lucas, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> lucas_chestplate = ITEMS.register("lucas_chestplate",
            () -> new ArmorItem(ModArmorMaterials.lucas, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> lucas_leggings = ITEMS.register("lucas_leggings",
            () -> new ArmorItem(ModArmorMaterials.lucas, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> lucas_boots = ITEMS.register("lucas_boots",
            () -> new ArmorItem(ModArmorMaterials.lucas, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> finn_helmet = ITEMS.register("finn_helmet",
            () -> new ArmorItem(ModArmorMaterials.finn, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> finn_chestplate = ITEMS.register("finn_chestplate",
            () -> new ArmorItem(ModArmorMaterials.finn, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> finn_leggings = ITEMS.register("finn_leggings",
            () -> new ArmorItem(ModArmorMaterials.finn, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> finn_boots = ITEMS.register("finn_boots",
            () -> new ArmorItem(ModArmorMaterials.finn, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> merry_helmet = ITEMS.register("merry_helmet",
            () -> new ArmorItem(ModArmorMaterials.merry, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> merry_chestplate = ITEMS.register("merry_chestplate",
            () -> new ArmorItem(ModArmorMaterials.merry, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> merry_leggings = ITEMS.register("merry_leggings",
            () -> new ArmorItem(ModArmorMaterials.merry, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> merry_boots = ITEMS.register("merry_boots",
            () -> new ArmorItem(ModArmorMaterials.merry, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> harry_helmet = ITEMS.register("harry_helmet",
            () -> new ArmorItem(ModArmorMaterials.harry, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> harry_chestplate = ITEMS.register("harry_chestplate",
            () -> new ArmorItem(ModArmorMaterials.harry, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> harry_leggings = ITEMS.register("harry_leggings",
            () -> new ArmorItem(ModArmorMaterials.harry, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> harry_boots = ITEMS.register("harry_boots",
            () -> new ArmorItem(ModArmorMaterials.harry, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> olly_helmet = ITEMS.register("olly_helmet",
            () -> new ArmorItem(ModArmorMaterials.olly, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> olly_chestplate = ITEMS.register("olly_chestplate",
            () -> new ArmorItem(ModArmorMaterials.olly, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> olly_leggings = ITEMS.register("olly_leggings",
            () -> new ArmorItem(ModArmorMaterials.olly, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> olly_boots = ITEMS.register("olly_boots",
            () -> new ArmorItem(ModArmorMaterials.olly, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void regester(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
