package net.haladopa.this_mod.item;

import net.haladopa.this_mod.this_mod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    titanium("titanium", 12, new int[]{5, 7, 4, 2}, 23,
            SoundEvents.ARMOR_EQUIP_LEATHER, 16f, 8f, () -> Ingredient.of(ModItems.titanium.get())),
    tom("tom", 40, new int[]{4, 9, 7, 4}, 18,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5f, 0.15f, () -> Ingredient.of(ModItems.titanium.get())),
    lucas("lucas", 44, new int[]{5, 10, 8, 4}, 20,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0f, 0.2f, () -> Ingredient.of(ModItems.titanium.get())),
    finn("finn", 48, new int[]{5, 10, 9, 5}, 22,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 4.5f, 0.25f, () -> Ingredient.of(ModItems.titanium.get())),
    merry("merry", 52, new int[]{6, 11, 9, 5}, 24,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 5.0f, 0.3f, () -> Ingredient.of(ModItems.titanium.get())),
    harry("harry", 56, new int[]{6, 12, 10, 6}, 26,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 5.5f, 0.35f, () -> Ingredient.of(ModItems.titanium.get())),
    olly("olly", 60, new int[]{7, 13, 11, 7}, 30,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 6.0f, 0.4f, () -> Ingredient.of(ModItems.titanium.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[]  BASE_DURABILITY = {11, 16, 16, 13};

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this_mod.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
