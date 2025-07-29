package org.millenaire.common.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.millenaire.common.utilities.MillCommonUtilities;
import org.millenaire.common.utilities.WorldUtilities;

public class ItemPurse extends ItemMill {
  private static final String ML_PURSE_DENIER = "ml_Purse_DENIER";
  
  private static final String ML_PURSE_DENIERARGENT = "ml_Purse_DENIERargent";
  
  private static final String ML_PURSE_DENIEROR = "ml_Purse_DENIERor";
  
  private static final String ML_PURSE_RAND = "ml_Purse_rand";
  
  public ItemPurse(String itemName) {
    super(itemName);
    setMaxStackSize(1);
  }
  
  @SideOnly(Side.CLIENT)
  public String getItemStackDisplayName(ItemStack purse) {
    if (purse.getTag() == null)
      return I18n.format(MillItems.PURSE.getTranslationKey() + ".name", new Object[0]); 
    int DENIERs = purse.getTag().getInt("ml_Purse_DENIER");
    int DENIERargent = purse.getTag().getInt("ml_Purse_DENIERargent");
    int DENIERor = purse.getTag().getInt("ml_Purse_DENIERor");
    String label = "";
    if (DENIERor != 0)
      label = "§e" + DENIERor + "o "; 
    if (DENIERargent != 0)
      label = label + "§f" + DENIERargent + "a "; 
    if (DENIERs != 0 || label.length() == 0)
      label = label + "§6" + DENIERs + "d"; 
    label = label.trim();
    return "§f" + I18n.format(MillItems.PURSE.getTranslationKey() + ".name", new Object[0]) + ": " + label;
  }
  
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack purse = playerIn.getHeldItem(handIn);
    if (totalDeniers(purse) > 0) {
      removeDeniersFromPurse(purse, playerIn);
    } else {
      storeDeniersInPurse(purse, playerIn);
    } 
    return super.onItemRightClick(worldIn, playerIn, handIn);
  }
  
  private void removeDeniersFromPurse(ItemStack purse, EntityPlayer player) {
    if (purse.getTag() != null) {
      int DENIERs = purse.getTag().getInt("ml_Purse_DENIER");
      int DENIERargent = purse.getTag().getInt("ml_Purse_DENIERargent");
      int DENIERor = purse.getTag().getInt("ml_Purse_DENIERor");
      int result = MillCommonUtilities.putItemsInChest((IInventory)player.inventory, MillItems.DENIER, DENIERs);
      purse.getTag().putInt("ml_Purse_DENIER", DENIERs - result);
      result = MillCommonUtilities.putItemsInChest((IInventory)player.inventory, MillItems.DENIER_ARGENT, DENIERargent);
      purse.getTag().putInt("ml_Purse_DENIERargent", DENIERargent - result);
      result = MillCommonUtilities.putItemsInChest((IInventory)player.inventory, MillItems.DENIER_OR, DENIERor);
      purse.getTag().putInt("ml_Purse_DENIERor", DENIERor - result);
      purse.getTag().putInt("ml_Purse_rand", player.world.isRemote ? 0 : 1);
    } 
  }
  
  public void setDeniers(ItemStack purse, EntityPlayer player, int amount) {
    int denier = amount % 64;
    int denier_argent = (amount - denier) / 64 % 64;
    int denier_or = (amount - denier - denier_argent * 64) / 4096;
    setDeniers(purse, player, denier, denier_argent, denier_or);
  }
  
  public void setDeniers(ItemStack purse, EntityPlayer player, int DENIER, int DENIERargent, int DENIERor) {
    if (purse.getTag() == null)
      purse.setTag(new NBTTagCompound()); 
    purse.getTag().putInt("ml_Purse_DENIER", DENIER);
    purse.getTag().putInt("ml_Purse_DENIERargent", DENIERargent);
    purse.getTag().putInt("ml_Purse_DENIERor", DENIERor);
    purse.getTag().putInt("ml_Purse_rand", player.world.isRemote ? 0 : 1);
  }
  
  private void storeDeniersInPurse(ItemStack purse, EntityPlayer player) {
    int deniers = WorldUtilities.getItemsFromChest((IInventory)player.inventory, MillItems.DENIER, 0, 2147483647);
    int deniersargent = WorldUtilities.getItemsFromChest((IInventory)player.inventory, MillItems.DENIER_ARGENT, 0, 2147483647);
    int deniersor = WorldUtilities.getItemsFromChest((IInventory)player.inventory, MillItems.DENIER_OR, 0, 2147483647);
    int total = totalDeniers(purse) + deniers + deniersargent * 64 + deniersor * 64 * 64;
    int new_denier = total % 64;
    int new_deniers_argent = (total - new_denier) / 64 % 64;
    int new_deniers_or = (total - new_denier - new_deniers_argent * 64) / 4096;
    setDeniers(purse, player, new_denier, new_deniers_argent, new_deniers_or);
  }
  
  public int totalDeniers(ItemStack purse) {
    if (purse.getTag() == null)
      return 0; 
    int deniers = purse.getTag().getInt("ml_Purse_DENIER");
    int denier_argent = purse.getTag().getInt("ml_Purse_DENIERargent");
    int denier_or = purse.getTag().getInt("ml_Purse_DENIERor");
    return deniers + denier_argent * 64 + denier_or * 64 * 64;
  }
}
