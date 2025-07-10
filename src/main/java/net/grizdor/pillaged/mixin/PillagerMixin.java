package net.grizdor.pillaged.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pillager.class)
public abstract class PillagerMixin extends AbstractIllager implements CrossbowAttackMob, InventoryCarrier, RangedAttackMob {

    protected PillagerMixin(EntityType<? extends AbstractIllager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void registerGoals(CallbackInfo callbackInfo) {
        this.goalSelector.addGoal(6, new RangedBowAttackGoal<>(this, (double)1.0F, 40, 15.0F));
    }

    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    public void getArmPose(CallbackInfoReturnable<IllagerArmPose> callbackInfo) {
        if (this.isAggressive() && this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof net.minecraft.world.item.BowItem) {
            callbackInfo.setReturnValue(IllagerArmPose.BOW_AND_ARROW);
        }
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo callbackInfo) {
        int i = random.nextInt(3);
        if (i == 0) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        }
    }

    @Inject(method = "performRangedAttack", at = @At("HEAD"), cancellable = true)
    public void performRangedAttack(LivingEntity target, float distanceFactor, CallbackInfo callbackInfo) {
        if (this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof net.minecraft.world.item.BowItem) {
            ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem));
            ItemStack itemstack1 = this.getProjectile(itemstack);
            AbstractArrow abstractarrow = ProjectileUtil.getMobArrow(this, itemstack1, distanceFactor, itemstack);
            if (itemstack.getItem() instanceof net.minecraft.world.item.BowItem bowItem)
                abstractarrow = bowItem.customArrow(abstractarrow, itemstack1, itemstack);
            double d0 = target.getX() - this.getX();
            double d1 = target.getY(0.3333333333333333) - abstractarrow.getY();
            double d2 = target.getZ() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            abstractarrow.shoot(d0, d1 + d3 * 0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
            this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level().addFreshEntity(abstractarrow);
            callbackInfo.cancel();
        }
    }
}
