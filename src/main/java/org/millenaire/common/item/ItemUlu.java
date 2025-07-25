package org.millenaire.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.millenaire.common.block.BlockSod;
import org.millenaire.common.block.MillBlocks;
import org.millenaire.common.network.ServerSender;
import org.millenaire.common.utilities.LanguageUtilities;
import org.millenaire.common.utilities.MillCommonUtilities;
import org.millenaire.common.utilities.Point;
import org.millenaire.common.utilities.WorldUtilities;

public class ItemUlu extends ItemMill {
  private static final IBlockState COARSE_DIRT = Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.COARSE_DIRT);
  
  public ItemUlu(String itemName) {
    super(itemName);
  }
  
  private EnumActionResult attemptSodPlanks(EntityPlayer player, World world, BlockPos pos, EnumFacing side, EnumHand hand) {
    if (world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER) {
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
    if (world.getBlockState(pos).getBlock() != Blocks.AIR)
      return EnumActionResult.PASS; 
    ItemStack is = player.getHeldItem(hand);
    BlockPlanks.EnumType chosenPlankType = null;
    for (BlockPlanks.EnumType plankType : BlockPlanks.EnumType.values()) {
      if (chosenPlankType == null && MillCommonUtilities.countChestItems((IInventory)player.inventory, Blocks.PLANKS.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, (Comparable)plankType)) > 0)
        chosenPlankType = plankType; 
    } 
    if (chosenPlankType == null) {
      if (!world.isRemote) {
        ServerSender.sendTranslatedSentence(player, 'f', "ui.uluexplanations", new String[0]);
        ServerSender.sendTranslatedSentence(player, '6', "ui.ulunoplanks", new String[0]);
      } 
      return EnumActionResult.PASS;
    } 
    if (!is.hasTagCompound())
      is.setTagCompound(new NBTTagCompound()); 
    int resUseCount = is.getTagCompound().getInteger("resUseCount");
    if (resUseCount == 0) {
      if (MillCommonUtilities.countChestItems((IInventory)player.inventory, COARSE_DIRT) == 0) {
        if (!world.isRemote)
          ServerSender.sendTranslatedSentence(player, '6', "ui.ulunodirt", new String[0]); 
        return EnumActionResult.PASS;
      } 
      WorldUtilities.getItemsFromChest((IInventory)player.inventory, COARSE_DIRT, 1);
      WorldUtilities.getItemsFromChest((IInventory)player.inventory, Blocks.PLANKS.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, (Comparable)chosenPlankType), 1);
      resUseCount = 3;
    } else {
      resUseCount--;
    } 
    is.getTagCompound().setInteger("resUseCount", resUseCount);
    WorldUtilities.setBlockstate(world, new Point(pos), MillBlocks.SOD.getDefaultState().withProperty((IProperty)BlockSod.VARIANT, (Comparable)chosenPlankType), true, true);
    is.damageItem(1, (EntityLivingBase)player);
    return EnumActionResult.SUCCESS;
  }
  
  @SideOnly(Side.CLIENT)
  public String getItemStackDisplayName(ItemStack stack) {
    if (stack.getTagCompound() != null) {
      int resUseCount = stack.getTagCompound().getInteger("resUseCount");
      return super.getItemStackDisplayName(stack) + " - " + LanguageUtilities.string("ui.ulusodplanksleft", new String[] { "" + resUseCount });
    } 
    return super.getItemStackDisplayName(stack);
  }
  
  public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
    ItemStack uluIS = player.getHeldItem(hand);
    if (world.getBlockState(pos).getBlock() == Blocks.SNOW) {
      world.setBlockState(pos, Blocks.AIR.getDefaultState());
      MillCommonUtilities.putItemsInChest((IInventory)player.inventory, (Block)MillBlocks.SNOW_BRICK, 0, 4);
      uluIS.damageItem(1, (EntityLivingBase)player);
      return EnumActionResult.SUCCESS;
    } 
    if (world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER) {
      int snowDepth = ((Integer)world.getBlockState(pos).getValue((IProperty)BlockSnow.LAYERS)).intValue();
      world.setBlockState(pos, Blocks.AIR.getDefaultState());
      MillCommonUtilities.putItemsInChest((IInventory)player.inventory, (Block)MillBlocks.SNOW_BRICK, 0, (snowDepth + 1) / 2);
      uluIS.damageItem(1, (EntityLivingBase)player);
      return EnumActionResult.SUCCESS;
    } 
    if (world.getBlockState(pos).getBlock() == Blocks.ICE) {
      MillCommonUtilities.putItemsInChest((IInventory)player.inventory, (Block)MillBlocks.ICE_BRICK, 0, 4);
      world.setBlockState(pos, Blocks.AIR.getDefaultState());
      uluIS.damageItem(1, (EntityLivingBase)player);
      return EnumActionResult.SUCCESS;
    } 
    return attemptSodPlanks(player, world, pos, side, hand);
  }
}
