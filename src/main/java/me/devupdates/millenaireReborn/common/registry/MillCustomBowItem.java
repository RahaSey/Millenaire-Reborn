package me.devupdates.millenaireReborn.common.registry;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class MillCustomBowItem extends BowItem {

    private float drawSpeedMultiplier = 1.0F; // 1.0F = no multiplier
    private float damageMultiplier = 1.0F; // 1.0F = no multiplier
    private float projectileSpeedMultiplier = 1.0F; // 1.0F = no multiplier

    public MillCustomBowItem(Settings settings, float drawSpeedMultiplier, float projectileSpeedMultiplier , float damageMultiplier) {
        super(settings);
        this.drawSpeedMultiplier = drawSpeedMultiplier;
        this.projectileSpeedMultiplier = projectileSpeedMultiplier;
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity playerEntity)) {
			return false;
		} else {
			ItemStack itemStack = playerEntity.getProjectileType(stack);
			if (itemStack.isEmpty()) {
				return false;
			} else {
				int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
				float f = getPullProgress((int)(i * this.drawSpeedMultiplier
                
                ));
				if (f < 0.1) {
					return false;
				} else {
					List<ItemStack> list = load(stack, itemStack, playerEntity);
					if (world instanceof ServerWorld serverWorld && !list.isEmpty()) {
						this.shootAll(serverWorld, playerEntity, playerEntity.getActiveHand(), stack, list, f * 3.0F * projectileSpeedMultiplier, 1.0F, f == 1.0F, null);
					}

					world.playSound(
						null,
						playerEntity.getX(),
						playerEntity.getY(),
						playerEntity.getZ(),
						SoundEvents.ENTITY_ARROW_SHOOT,
						SoundCategory.PLAYERS,
						1.0F,
						1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
					);
					playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
					return true;
				}
			}
		}
    }

    @Override
	protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
		projectile.setVelocity(shooter, shooter.getPitch(), shooter.getYaw() + yaw, 0.0F, speed, divergence);

        if (projectile instanceof PersistentProjectileEntity arrow) {
            arrow.applyDamageModifier(this.damageMultiplier);
        }
	}
    
}
