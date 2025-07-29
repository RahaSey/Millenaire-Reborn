package org.millenaire.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.millenaire.common.utilities.Point;

public class EntityTargetedGhast extends EntityGhast {
  public Point target = null;
  
  public EntityTargetedGhast(World par1World) {
    super(par1World);
  }
  
  protected boolean canDespawn() {
    return false;
  }
  
  public void func_70071_h_() {
    if (this.target != null)
      if (this.target.distanceTo((Entity)this) > 20.0D) {
        getMoveHelper().setMoveTo(this.target.x, this.target.y, this.target.z, getAIMoveSpeed());
      } else if (this.target.distanceTo((Entity)this) < 10.0D) {
        getMoveHelper().setMoveTo(this.target.x + ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F), this.target.y + ((this.rand
            .nextFloat() * 2.0F - 1.0F) * 16.0F), this.target.z + ((this.rand
            .nextFloat() * 2.0F - 1.0F) * 16.0F), getAIMoveSpeed());
      }  
    super.func_70071_h_();
  }
  
  public void read(NBTTagCompound par1nbtTagCompound) {
    super.read(par1nbtTagCompound);
    this.target = Point.read(par1nbtTagCompound, "targetPoint");
  }
  
  public NBTTagCompound writeWithoutTypeId(NBTTagCompound par1nbtTagCompound) {
    super.writeWithoutTypeId(par1nbtTagCompound);
    if (this.target != null)
      this.target.write(par1nbtTagCompound, "targetPoint"); 
    return par1nbtTagCompound;
  }
}
