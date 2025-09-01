package me.devupdates.millenaireReborn.common.registry;

import java.rmi.registry.Registry;
import java.util.Map;
import java.util.jar.Attributes.Name;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;

public class MillFoodItemBuilder {

    public static enum MillFoodType {
        VEG_CURRY
    }

    // Inner class
    private static class MillFoodItem {
        final MillFoodType Name;
        final Integer Nutrition;
        final Integer MaxDamage;
        final Float Saturation;
        final Boolean IsAlwaysEdible;
        final Boolean IsDrink;
        final StatusEffectInstance StatusEffect;

        public MillFoodItem(MillFoodType name, Integer nutrition, Float saturation, Integer maxDamage, Boolean isAlwaysEdible, Boolean isDrink, StatusEffectInstance statusEffect) {
            Name = name;
            Nutrition = nutrition;
            Saturation = saturation;
            MaxDamage = maxDamage;
            IsAlwaysEdible = isAlwaysEdible;
            IsDrink = isDrink;
            StatusEffect = statusEffect;
        }
    }

    public static Item CreateItem(Item.Settings settings, MillFoodType foodType)
    {
        MillFoodItem foodItem = AllFood.get(foodType);

        if (!foodItem.IsDrink){
            // Create food item here
            MillCustomFoodItem item = null;

            if (foodItem.StatusEffect == null){
                item = new MillCustomFoodItem(settings, new FoodComponent(foodItem.Nutrition, foodItem.Saturation, foodItem.IsAlwaysEdible), foodItem.MaxDamage);
            }

            return item;
        }
        else {
            // Create Drink item here
            return null;            
        }

    }

    // All Food Items
    private static final Map<MillFoodType, MillFoodItem> AllFood =
        Map.of(
            //VEG_CURRY
            MillFoodType.VEG_CURRY, new MillFoodItem(MillFoodType.VEG_CURRY, 6, 0.6f, 6, false, false, null)
        );

}
