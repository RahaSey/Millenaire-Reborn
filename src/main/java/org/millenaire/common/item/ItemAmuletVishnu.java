package org.millenaire.common.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.millenaire.common.utilities.Point;
import org.millenaire.common.utilities.WorldUtilities;

public class ItemAmuletVishnu extends ItemMill {
  private static final int radius = 20;
  
  public ItemAmuletVishnu(String itemName) {
    super(itemName);
    addPropertyOverride(new ResourceLocation("score"), new IItemPropertyGetter() {
          @SideOnly(Side.CLIENT)
          long lastUpdateTick;
          
          @SideOnly(Side.CLIENT)
          float savedScore;
          
          @SideOnly(Side.CLIENT)
          public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entityIn) {
            if (entityIn == null)
              return 0.0F; 
            world = entityIn.world;
            if (world.getGameTime() != this.lastUpdateTick) {
              double level = 0.0D;
              double closestDistance = Double.MAX_VALUE;
              if (world != null && entityIn != null) {
                Point p = new Point((Entity)entityIn);
                List<Entity> entities = WorldUtilities.getEntitiesWithinAABB(world, EntityMob.class, p, 20, 20);
                for (Entity ent : entities) {
                  if (p.distanceTo(ent) < closestDistance)
                    closestDistance = p.distanceTo(ent); 
                } 
              } 
              if (closestDistance > 20.0D) {
                level = 0.0D;
              } else {
                level = (20.0D - closestDistance) / 20.0D;
              } 
              this.savedScore = (float)(level * 15.0D);
              this.lastUpdateTick = world.getGameTime();
              return this.savedScore;
            } 
            return this.savedScore;
          }
        });
  }
}
