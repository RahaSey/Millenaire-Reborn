package me.devupdates.millenaireReborn.common.registry;

import java.util.Map;

import me.devupdates.millenaireReborn.MillenaireReborn;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class MillCustomMaterials {

    // Armor Asset Ids
    private static final RegistryKey<EquipmentAsset> NORMAN_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MillenaireReborn.MOD_ID, "norman"));
    private static final RegistryKey<EquipmentAsset> BYZANTINE_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MillenaireReborn.MOD_ID, "byzantine"));
    private static final RegistryKey<EquipmentAsset> JAPANESE_RED_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MillenaireReborn.MOD_ID, "japanese_red"));
    private static final RegistryKey<EquipmentAsset> JAPANESE_BLUE_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MillenaireReborn.MOD_ID, "japanese_blue"));
    private static final RegistryKey<EquipmentAsset> JAPANESE_GUARD_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MillenaireReborn.MOD_ID, "japanese_guard"));
    private static final RegistryKey<EquipmentAsset> SELJUK_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MillenaireReborn.MOD_ID, "seljuk"));
    private static final RegistryKey<EquipmentAsset> SELJUK_WOOL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MillenaireReborn.MOD_ID, "seljuk_wool"));
    private static final RegistryKey<EquipmentAsset> FUR_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MillenaireReborn.MOD_ID, "furcoat"));
    private static final RegistryKey<EquipmentAsset> MAYAN_CROWN_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MillenaireReborn.MOD_ID, "mayan_quest_crown"));

    // Armor Materials
    public static final ArmorMaterial NORMAN_ARMOR_MATERIAL = new ArmorMaterial(66, 
        Map.of(EquipmentType.HELMET, 3, EquipmentType.CHESTPLATE, 8, EquipmentType.LEGGINGS, 6, EquipmentType.BOOTS, 3),
        10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2, 0, ItemTags.DIAMOND_TOOL_MATERIALS, NORMAN_ARMOR_MATERIAL_KEY);

    public static final ArmorMaterial BYZANTINE_ARMOR_MATERIAL = new ArmorMaterial(33, 
        Map.of(EquipmentType.HELMET, 3, EquipmentType.CHESTPLATE, 8, EquipmentType.LEGGINGS, 6, EquipmentType.BOOTS, 3),
        20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0, ItemTags.DIAMOND_TOOL_MATERIALS, BYZANTINE_ARMOR_MATERIAL_KEY);

    public static final ArmorMaterial JAPANESE_RED_ARMOR_MATERIAL = new ArmorMaterial(33, 
        Map.of(EquipmentType.HELMET, 2, EquipmentType.CHESTPLATE, 6, EquipmentType.LEGGINGS, 5, EquipmentType.BOOTS, 2),
        25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0, ItemTags.DIAMOND_TOOL_MATERIALS, JAPANESE_RED_ARMOR_MATERIAL_KEY);

    public static final ArmorMaterial JAPANESE_BLUE_ARMOR_MATERIAL = new ArmorMaterial(33, 
        Map.of(EquipmentType.HELMET, 2, EquipmentType.CHESTPLATE, 6, EquipmentType.LEGGINGS, 5, EquipmentType.BOOTS, 2),
        25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0, ItemTags.DIAMOND_TOOL_MATERIALS, JAPANESE_BLUE_ARMOR_MATERIAL_KEY);

    public static final ArmorMaterial JAPANESE_GUARD_ARMOR_MATERIAL = new ArmorMaterial(25, 
        Map.of(EquipmentType.HELMET, 2, EquipmentType.CHESTPLATE, 5, EquipmentType.LEGGINGS, 4, EquipmentType.BOOTS, 1),
        25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0, ItemTags.DIAMOND_TOOL_MATERIALS, JAPANESE_GUARD_ARMOR_MATERIAL_KEY);

    public static final ArmorMaterial SELJUK_ARMOR_MATERIAL = new ArmorMaterial(66, 
        Map.of(EquipmentType.HELMET, 3, EquipmentType.CHESTPLATE, 8, EquipmentType.LEGGINGS, 6, EquipmentType.BOOTS, 3),
        10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2, 0, ItemTags.DIAMOND_TOOL_MATERIALS, SELJUK_ARMOR_MATERIAL_KEY);

    public static final ArmorMaterial SELJUK_WOOL_ARMOR_MATERIAL = new ArmorMaterial(7, 
        Map.of(EquipmentType.HELMET, 2, EquipmentType.CHESTPLATE, 5, EquipmentType.LEGGINGS, 3, EquipmentType.BOOTS, 1),
        10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1, 0, ItemTags.WOOL, SELJUK_WOOL_ARMOR_MATERIAL_KEY);

    public static final ArmorMaterial FUR_ARMOR_MATERIAL = new ArmorMaterial(7, 
        Map.of(EquipmentType.HELMET, 2, EquipmentType.CHESTPLATE, 5, EquipmentType.LEGGINGS, 3, EquipmentType.BOOTS, 1),
        25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2, 0, ItemTags.DIAMOND_TOOL_MATERIALS, FUR_ARMOR_MATERIAL_KEY);

    public static final ArmorMaterial MAYAN_CROWN_ARMOR_MATERIAL = new ArmorMaterial(33, 
        Map.of(EquipmentType.HELMET, 3, EquipmentType.CHESTPLATE, 6, EquipmentType.LEGGINGS, 8, EquipmentType.BOOTS, 3),
        10, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 3, 0, ItemTags.GOLD_TOOL_MATERIALS, MAYAN_CROWN_ARMOR_MATERIAL_KEY);

    // Tool Materials
    public static final ToolMaterial NORMAN_TOOL_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 10.0F, 4.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial BYZANTINE_TOOL_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 12.0F, 3.0F, 15, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial OBSIDIAN_TOOL_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 6.0F, 2.0F, 25, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial BETTER_STEEL_TOOL_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 5.0F, 3.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
}
