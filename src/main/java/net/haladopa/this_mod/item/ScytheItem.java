package net.haladopa.this_mod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class ScytheItem extends SwordItem {
    public ScytheItem(Properties properties) {
        super(Tiers.NETHERITE, 5, -2.4f, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (attacker instanceof Player player && isWearingFullOllySet(player)) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
            target.setSecondsOnFire(5);
        }
        return result;
    }

    public static boolean isWearingFullOllySet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.olly_helmet.get()) &&
               player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.olly_chestplate.get()) &&
               player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.olly_leggings.get()) &&
               player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.olly_boots.get());
    }
}
