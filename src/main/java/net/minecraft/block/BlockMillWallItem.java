package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.millenaire.common.block.MillBlocks;

public class BlockMillWallItem extends Block {
  public static final PropertyDirection FACING = BlockHorizontal.HORIZONTAL_FACING;
  
  protected static final AxisAlignedBB LADDER_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
  
  protected static final AxisAlignedBB LADDER_WEST_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
  
  protected static final AxisAlignedBB LADDER_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
  
  protected static final AxisAlignedBB LADDER_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
  
  public BlockMillWallItem(String blockName, Material material, SoundType soundType) {
    super(material);
    setSoundType(soundType);
    setDefaultState(this.stateContainer.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
    setCreativeTab(CreativeTabs.DECORATIONS);
    setUnlocalizedName("millenaire." + blockName);
    setRegistryName(blockName);
    setCreativeTab(MillBlocks.tabMillenaire);
    setHarvestLevel("pickaxe", 0);
  }
  
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    switch ((EnumFacing)state.get((IProperty)FACING)) {
      case NORTH:
        return LADDER_NORTH_AABB;
      case SOUTH:
        return LADDER_SOUTH_AABB;
      case WEST:
        return LADDER_WEST_AABB;
    } 
    return LADDER_EAST_AABB;
  }
  
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }
  
  public boolean isFullCube(IBlockState state) {
    return false;
  }
  
  public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
    if (canAttachTo(worldIn, pos.west(), side))
      return true; 
    if (canAttachTo(worldIn, pos.east(), side))
      return true; 
    if (canAttachTo(worldIn, pos.north(), side))
      return true; 
    return canAttachTo(worldIn, pos.south(), side);
  }
  
  private boolean canAttachTo(World p_193392_1_, BlockPos p_193392_2_, EnumFacing p_193392_3_) {
    IBlockState iblockstate = p_193392_1_.getBlockState(p_193392_2_);
    boolean flag = isExceptBlockForAttachWithPiston(iblockstate.getBlock());
    return (!flag && iblockstate.getBlockFaceShape((IBlockAccess)p_193392_1_, p_193392_2_, p_193392_3_) == BlockFaceShape.SOLID && !iblockstate.canProvidePower());
  }
  
  public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    if (facing.getAxis().isHorizontal() && canAttachTo(worldIn, pos.offset(facing.getOpposite()), facing))
      return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing); 
    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
      if (canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing))
        return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing); 
    } 
    return getDefaultState();
  }
  
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    EnumFacing enumfacing = (EnumFacing)state.get((IProperty)FACING);
    if (!canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing)) {
      dropBlockAsItem(worldIn, pos, state, 0);
      worldIn.setBlockToAir(pos);
    } 
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
  }
  
  public IBlockState getStateFromMeta(int meta) {
    EnumFacing enumfacing = EnumFacing.byIndex(meta);
    if (enumfacing.getAxis() == EnumFacing.Axis.Y)
      enumfacing = EnumFacing.NORTH; 
    return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
  }
  
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.CUTOUT;
  }
  
  public int getMetaFromState(IBlockState state) {
    return ((EnumFacing)state.get((IProperty)FACING)).getIndex();
  }
  
  public IBlockState rotate(IBlockState state, Rotation rot) {
    return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.get((IProperty)FACING)));
  }
  
  public IBlockState mirror(IBlockState state, Mirror mirrorIn) {
    return state.rotate(mirrorIn.toRotation((EnumFacing)state.get((IProperty)FACING)));
  }
  
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING });
  }
  
  public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
    return true;
  }
  
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }
  
  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), ""));
  }
}
