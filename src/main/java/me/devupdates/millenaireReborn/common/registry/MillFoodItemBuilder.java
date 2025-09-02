package me.devupdates.millenaireReborn.common.registry;

import java.util.List;
import java.util.Map;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.world.World;
import net.minecraft.component.type.ConsumableComponents;

public class MillFoodItemBuilder {

    //#region Inner Classes
    private static class MillCustomFoodItem extends Item {

        public MillCustomFoodItem(Settings settings, FoodComponent foodComponent, List<StatusEffectInstance> statusEffects, Integer maxDamage) {
            super(settings.food(foodComponent, ConsumableComponents.food().consumeEffect(new ApplyEffectsConsumeEffect(statusEffects)).build()).maxDamage(maxDamage));
        }

        public MillCustomFoodItem(Settings settings, FoodComponent foodComponent, Integer maxDamage) {
            super(settings.food(foodComponent).maxDamage(maxDamage));
        }

        @Override
        public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
            // I copy the stack to get the nutrition without loosing the item if it has durability left
            super.finishUsing(stack.copy(), world, user);

            if (stack.isDamageable()) {
                stack.damage(1, user, EquipmentSlot.MAINHAND);
            } else {
                stack.decrement(1);
            }

            return stack;
        }
    }

    private static class MillFoodItem {
        final MillFoodType Name;
        final Integer Nutrition;
        final Integer MaxDamage;
        final Float Saturation;
        final Boolean IsAlwaysEdible;
        final Boolean IsDrink;
        final List<StatusEffectInstance> StatusEffects;

        public MillFoodItem(MillFoodType name, Integer nutrition, Float saturation, Integer maxDamage, Boolean isAlwaysEdible, Boolean isDrink, List<StatusEffectInstance> statusEffects) {
            Name = name;
            Nutrition = nutrition;
            Saturation = saturation;
            MaxDamage = maxDamage;
            IsAlwaysEdible = isAlwaysEdible;
            IsDrink = isDrink;
            StatusEffects = statusEffects;
        }
    }
    //#endregion Inner Classes

    public static Item CreateItem(Item.Settings settings, MillFoodType foodType)
    {
        MillFoodItem foodItem = AllFood.get(foodType);

        if (!foodItem.IsDrink){
            // Create food item here
            MillCustomFoodItem item = null;

            if (foodItem.StatusEffects == null || foodItem.StatusEffects.isEmpty()){
                item = new MillCustomFoodItem(settings, new FoodComponent(foodItem.Nutrition, foodItem.Saturation, foodItem.IsAlwaysEdible), foodItem.MaxDamage);
            }
            else{
                item = new MillCustomFoodItem(settings, new FoodComponent(foodItem.Nutrition, foodItem.Saturation, foodItem.IsAlwaysEdible), foodItem.StatusEffects, foodItem.MaxDamage);
            }

            return item;
        }
        else {
            // Create Drink item here
            return null;            
        }

    }


    //#region All Food Types
        public static enum MillFoodType {
        VEG_CURRY,
        CHICKEN_CURRY,
        RASGULLA,
        YOGURT,
        AYRAN,
    }
    //#endregion All Food Types

    //#region All Food Items
    private static final Map<MillFoodType, MillFoodItem> AllFood =
        Map.of(
            //VEG_CURRY
            MillFoodType.VEG_CURRY, new MillFoodItem(MillFoodType.VEG_CURRY, 6, 7.2f, 6, false, false, null),
            //CHICKEN_CURRY
            MillFoodType.CHICKEN_CURRY, new MillFoodItem(MillFoodType.CHICKEN_CURRY, 8, 12.8f, 8, false, false, null),
            //RASGULLA
            MillFoodType.RASGULLA, new MillFoodItem(MillFoodType.RASGULLA, 0, 0f, 0, true, false,
             List.of(
                new StatusEffectInstance(StatusEffects.REGENERATION, 30 * 20, 0),
                new StatusEffectInstance(StatusEffects.SPEED, 480 * 20, 1))),
            //YOGURT
            MillFoodType.YOGURT, new MillFoodItem(MillFoodType.YOGURT, 0, 0f, 0, true, false,
             List.of(
                new StatusEffectInstance(StatusEffects.REGENERATION, 15 * 20, 0))),
            //AYRAN
            MillFoodType.AYRAN, new MillFoodItem(MillFoodType.AYRAN, 0, 0f, 0, true, false,
             List.of(
                new StatusEffectInstance(StatusEffects.REGENERATION, 15 * 20, 0),
                new StatusEffectInstance(StatusEffects.NAUSEA, 5 * 20, 0)))
        );
    //#endregion All Food Items
}