package me.devupdates.millenaireReborn.common.registry;

import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.world.World;

public class MillCustomFoodItem extends Item {
    private final FoodComponent foodComponent;

    public MillCustomFoodItem(Settings settings, FoodComponent foodComponent, StatusEffectInstance statusEffect, Integer maxDamage) {
        super(settings.food(foodComponent, ConsumableComponents.food().consumeEffect(new ApplyEffectsConsumeEffect(statusEffect)).build()).maxDamage(maxDamage));
        this.foodComponent = foodComponent;
    }

    public MillCustomFoodItem(Settings settings, FoodComponent foodComponent, Integer maxDamage) {
        super(settings.food(foodComponent).maxDamage(maxDamage));
        this.foodComponent = foodComponent;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // I copy the stack to get the nutrition without loosing the item if it has durability left
        ItemStack result = super.finishUsing(stack.copy(), world, user);
        
        if (stack.isDamageable()) {
            stack.damage(1, user, EquipmentSlot.MAINHAND);
        } else {
            stack.decrement(1);
        }
        
        return stack;
    }
}
