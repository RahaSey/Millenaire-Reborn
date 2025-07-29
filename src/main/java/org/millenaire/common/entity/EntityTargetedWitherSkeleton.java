package org.millenaire.common.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityTargetedWitherSkeleton extends EntitySkeleton {
  public EntityTargetedWitherSkeleton(World par1World) {
    super(par1World);
    registerData();
  }
  
  protected boolean canDespawn() {
    return false;
  }
  
  public void registerData() {
    super.registerData();
    setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
    this.goalSelector.taskEntries.clear();
    this.goalSelector.addGoal(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.goalSelector.addGoal(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.goalSelector.addGoal(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.goalSelector.addGoal(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.goalSelector.addGoal(4, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 0.3100000023841858D, false));
    this.targetSelector.taskEntries.clear();
    this.targetSelector.addGoal(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
    this.targetSelector.addGoal(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, 10, true, false, null));
  }
  
  public void livingTick() {
    super.livingTick();
    extinguish();
  }
}
