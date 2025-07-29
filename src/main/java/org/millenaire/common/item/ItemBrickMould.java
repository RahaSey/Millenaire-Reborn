package org.millenaire.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.millenaire.common.block.MillBlocks;
import org.millenaire.common.network.ServerSender;
import org.millenaire.common.utilities.MillCommonUtilities;
import org.millenaire.common.utilities.Point;
import org.millenaire.common.utilities.WorldUtilities;

public class ItemBrickMould extends ItemMill {
  public ItemBrickMould(String itemName) {
    super(itemName);
  }
  
  public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
    if (world.getBlockState(pos).getBlock() == Blocks.SNOW) {
      side = EnumFacing.DOWN;
    } else {
      if (side == EnumFacing.DOWN)
        pos = pos.down(); 
      if (side == EnumFacing.UP)
        pos = pos.up(); 
      if (side == EnumFacing.EAST)
        pos = pos.east(); 
      if (side == EnumFacing.WEST)
        pos = pos.west(); 
      if (side == EnumFacing.SOUTH)
        pos = pos.south(); 
      if (side == EnumFacing.NORTH)
        pos = pos.north(); 
    } 
    if (!world.mayPlace((Block)MillBlocks.WET_BRICK, pos, false, side, (Entity)null))
      return EnumActionResult.PASS; 
    if (world.getBlockState(pos).getBlock() != Blocks.AIR)
      return EnumActionResult.PASS; 
    ItemStack is = player.getHeldItem(hand);
    if (is.getDamage() % 4 == 0) {
      if (MillCommonUtilities.countChestItems((IInventory)player.inventory, Blocks.DIRT, 0) == 0 || MillCommonUtilities.countChestItems((IInventory)player.inventory, (Block)Blocks.SAND, 0) == 0) {
        if (!world.isRemote)
          ServerSender.sendTranslatedSentence(player, 'f', "ui.brickinstructions", new String[0]); 
        return EnumActionResult.PASS;
      } 
      WorldUtilities.getItemsFromChest((IInventory)player.inventory, Blocks.DIRT, 0, 1);
      WorldUtilities.getItemsFromChest((IInventory)player.inventory, (Block)Blocks.SAND, 0, 1);
    } 
    WorldUtilities.setBlockstate(world, new Point(pos), MillBlocks.BS_WET_BRICK, true, false);
    is.damageItem(1, (EntityLivingBase)player);
    return EnumActionResult.SUCCESS;
  }
}
