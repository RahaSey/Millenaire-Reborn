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
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.component.type.ConsumableComponents;

public class MillFoodItemBuilder {

    //#region Inner Classes
    private static class MillCustomFoodItem extends Item {
        public MillCustomFoodItem(Settings settings, FoodComponent foodComponent, List<StatusEffectInstance> statusEffects, Float statusEffectChance, Integer maxDamage, Boolean isDrink) {
            super(settings.food(foodComponent, ConsumableComponents.food().consumeEffect(new ApplyEffectsConsumeEffect(statusEffects, statusEffectChance))
                                .sound(isDrink ? SoundEvents.ENTITY_GENERIC_DRINK : SoundEvents.ENTITY_GENERIC_EAT).build()).maxDamage(maxDamage));
        }

        public MillCustomFoodItem(Settings settings, FoodComponent foodComponent, List<StatusEffectInstance> statusEffects, Float statusEffectChance, Boolean isDrink) {
            super(settings.food(foodComponent, ConsumableComponents.food().consumeEffect(new ApplyEffectsConsumeEffect(statusEffects, statusEffectChance))
                                .sound(isDrink ? SoundEvents.ENTITY_GENERIC_DRINK : SoundEvents.ENTITY_GENERIC_EAT).build()));
        }

        public MillCustomFoodItem(Settings settings, FoodComponent foodComponent, Integer maxDamage, Boolean isDrink) {
            super(settings.food(foodComponent, ConsumableComponents.food()
                                .sound(isDrink ? SoundEvents.ENTITY_GENERIC_DRINK : SoundEvents.ENTITY_GENERIC_EAT).build()).maxDamage(maxDamage));
        }

        public MillCustomFoodItem(Settings settings, FoodComponent foodComponent, Boolean isDrink) {
            super(settings.food(foodComponent, ConsumableComponents.food()
                                .sound(isDrink ? SoundEvents.ENTITY_GENERIC_DRINK : SoundEvents.ENTITY_GENERIC_EAT).build()));
        }

        @Override
        public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
            // I copy the stack to get the nutrition without loosing the item if it has durability left
            super.finishUsing(stack.copy(), world, user);

            if (stack.isDamageable() && stack.getMaxDamage() - stack.getDamage() > 1) {
                stack.damage(1, user, EquipmentSlot.MAINHAND);
            } else {
                stack.decrement(1);
            }

            return stack;
        }
    }

    private static class MillFoodItem {
        final Integer Nutrition;
        final Integer MaxDamage;
        final Float Saturation;
        final Float StatusEffectChance;
        final Boolean IsAlwaysEdible;
        final Boolean IsDrink;
        final List<StatusEffectInstance> StatusEffects;

        public MillFoodItem(Integer nutrition, Float saturation, Integer maxDamage,
                            Boolean isAlwaysEdible, Boolean isDrink, List<StatusEffectInstance> statusEffects, Float statusEffectChance) {
            Nutrition = nutrition;
            Saturation = saturation;
            MaxDamage = maxDamage;
            IsAlwaysEdible = isAlwaysEdible;
            IsDrink = isDrink;
            StatusEffects = statusEffects;
            StatusEffectChance = statusEffectChance;
        }
    }
    //#endregion Inner Classes

    public static Item CreateItem(Item.Settings settings, MillFoodType foodType)
    {
        MillFoodItem foodItem = AllFood.get(foodType);

        if (foodItem.StatusEffects == null || foodItem.StatusEffects.isEmpty()){
            if (foodItem.MaxDamage > 0)
                return new MillCustomFoodItem(settings, 
                    new FoodComponent(foodItem.Nutrition, foodItem.Saturation, foodItem.IsAlwaysEdible), foodItem.MaxDamage, foodItem.IsDrink);
            else
                return new MillCustomFoodItem(settings, 
                        new FoodComponent(foodItem.Nutrition, foodItem.Saturation, foodItem.IsAlwaysEdible), foodItem.IsDrink);
        }
        else{
            if (foodItem.MaxDamage > 0)
                return new MillCustomFoodItem(settings, 
                    new FoodComponent(foodItem.Nutrition, foodItem.Saturation, foodItem.IsAlwaysEdible), foodItem.StatusEffects, foodItem.StatusEffectChance, foodItem.MaxDamage, foodItem.IsDrink);
            else
                return new MillCustomFoodItem(settings, 
                    new FoodComponent(foodItem.Nutrition, foodItem.Saturation, foodItem.IsAlwaysEdible), foodItem.StatusEffects, foodItem.StatusEffectChance, foodItem.IsDrink);
        }
    }


    //#region All Food Types
        public static enum MillFoodType {
        VEG_CURRY,
        CHICKEN_CURRY,
        RASGULLA,
        YOGURT,
        AYRAN,
        PIDE,
        LOKUM,
        HELVA,
        PISTACHIOS,
        BEAR_MEAT_RAW,
        BEAR_MEAT_COOKED,
        WOLF_MEAT_RAW,
        WOLF_MEAT_COOKED,
        SEAFOOD_RAW,
        SEAFOOD_COOKED,
        INUIT_BEAR_STEW,
        INUIT_MEATY_STEW,
        INUIT_POTATO_STEW,
        SAKE,
        UDON,
        IKAYAKI,
        WINE_BASIC,
        WINE_FANCY,
        SOUVLAKI,
        FETA,
    }
    //#endregion All Food Types

    //#region All Food Items
    private static final Map<MillFoodType, MillFoodItem> AllFood =
        Map.ofEntries(
            //VEG_CURRY
            Map.entry(MillFoodType.VEG_CURRY, new MillFoodItem(6, 7.2f, 6, false, false, null, null)),

            //CHICKEN_CURRY
            Map.entry(MillFoodType.CHICKEN_CURRY, new MillFoodItem(8, 12.8f, 8, false, false, null, null)),

            //RASGULLA
            Map.entry(MillFoodType.RASGULLA, new MillFoodItem(0, 0f, 0, true, false,
             List.of(
                new StatusEffectInstance(StatusEffects.REGENERATION, 30 * 20, 0),
                new StatusEffectInstance(StatusEffects.SPEED, 480 * 20, 1)), 1f)),

            //YOGURT
            Map.entry(MillFoodType.YOGURT, new MillFoodItem(0, 0f, 0, true, false,
             List.of(
                new StatusEffectInstance(StatusEffects.REGENERATION, 15 * 20, 0)), 1f)),

            //AYRAN
            Map.entry(MillFoodType.AYRAN, new MillFoodItem(0, 0f, 0, true, true,
             List.of(
                new StatusEffectInstance(StatusEffects.REGENERATION, 15 * 20, 0),
                new StatusEffectInstance(StatusEffects.NAUSEA, 5 * 20, 0)), 1f)),

            //PIDE
            Map.entry(MillFoodType.PIDE, new MillFoodItem(8, 16f, 0, false, false, null, null)),
            
            //LOKUM
            Map.entry(MillFoodType.LOKUM, new MillFoodItem(3, 0.6f, 0, true, false,
             List.of(
                new StatusEffectInstance(StatusEffects.SPEED, 120 * 20, 0)), 0.2f)),

            //HELVA
            Map.entry(MillFoodType.HELVA, new MillFoodItem(5, 0.6f, 0, true, false,
             List.of(
                new StatusEffectInstance(StatusEffects.RESISTANCE, 120 * 20, 0)), 0.2f)),

            //PISTACHIOS
            Map.entry(MillFoodType.PISTACHIOS, new MillFoodItem(1, 0.6f, 0, false, false, null, null)),
            
            //BEAR_MEAT_RAW
            Map.entry(MillFoodType.BEAR_MEAT_RAW, new MillFoodItem(4, 4f, 0, false, false,
             List.of(
                new StatusEffectInstance(StatusEffects.STRENGTH, 240 * 20, 0)), 1f)),

            //BEAR_MEAT_COOKED
            Map.entry(MillFoodType.BEAR_MEAT_COOKED, new MillFoodItem(10, 20f, 0, false, false,
             List.of(
                new StatusEffectInstance(StatusEffects.STRENGTH, 480 * 20, 1)), 1f)),

            //WOLF_MEAT_RAW
            Map.entry(MillFoodType.WOLF_MEAT_RAW, new MillFoodItem(3, 1.8f, 0, false, false, null, null)),

            //BEAR_MEAT_COOKED
            Map.entry(MillFoodType.WOLF_MEAT_COOKED, new MillFoodItem(5, 6f, 0, false, false,
             List.of(
                new StatusEffectInstance(StatusEffects.STRENGTH, 60 * 20, 1)), 1f)),

            //SEAFOOD_RAW
            Map.entry(MillFoodType.SEAFOOD_RAW, new MillFoodItem(2, 0.8f, 0, false, false, null, null)),

            //SEAFOOD_COOKED
            Map.entry(MillFoodType.SEAFOOD_COOKED, new MillFoodItem(2, 1f, 0, false, false, null, null)),

            //INUIT_BEAR_STEW
            Map.entry(MillFoodType.INUIT_BEAR_STEW, new MillFoodItem(8, 16f, 8, false, false,
             List.of(
                new StatusEffectInstance(StatusEffects.STRENGTH, 480 * 20, 2)), 1f)),

            //INUIT_MEATY_STEW
            Map.entry(MillFoodType.INUIT_MEATY_STEW, new MillFoodItem(8, 12.8f, 8, false, false, null, null)),

            //INUIT_POTATO_STEW
            Map.entry(MillFoodType.INUIT_POTATO_STEW, new MillFoodItem(6, 7.2f, 6, false, false, null, null)),

            //SAKE
            Map.entry(MillFoodType.SAKE, new MillFoodItem(0, 0f, 8, true, true,
             List.of(
                new StatusEffectInstance(StatusEffects.JUMP_BOOST, 480 * 20, 0),
                new StatusEffectInstance(StatusEffects.REGENERATION, 10 * 20, 0)), 1f)),

            //UDON
            Map.entry(MillFoodType.UDON, new MillFoodItem(8, 12.8f, 6, false, false, null, null)),

            //IKAYAKI
            Map.entry(MillFoodType.IKAYAKI, new MillFoodItem(10, 20f, 8, false, false,
             List.of(
                new StatusEffectInstance(StatusEffects.WATER_BREATHING, 480 * 20, 1)), 1f)),

            //WINE_BASIC
            Map.entry(MillFoodType.WINE_BASIC, new MillFoodItem(0, 0f, 6, true, true,
             List.of(
                new StatusEffectInstance(StatusEffects.NAUSEA, 8 * 20, 0),
                new StatusEffectInstance(StatusEffects.REGENERATION, 5 * 20, 0)), 1f)),

            //WINE_FANCY
            Map.entry(MillFoodType.WINE_FANCY, new MillFoodItem(0, 0f, 16, true, true,
             List.of(
                new StatusEffectInstance(StatusEffects.NAUSEA, 15 * 20, 0),
                new StatusEffectInstance(StatusEffects.REGENERATION, 10 * 20, 0),
                new StatusEffectInstance(StatusEffects.RESISTANCE, 480 * 20, 1)), 1f)),
            
            //SOUVLAKI
            Map.entry(MillFoodType.SOUVLAKI, new MillFoodItem(10, 20f, 8, false, false, null, null)),

            //FETA
            Map.entry(MillFoodType.FETA, new MillFoodItem(1, 1f, 0, true, false,
             List.of(
                new StatusEffectInstance(StatusEffects.REGENERATION, 50, 0)), 1f))
        );
    //#endregion All Food Items
}