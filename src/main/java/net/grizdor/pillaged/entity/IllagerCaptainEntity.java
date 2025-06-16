package net.grizdor.pillaged.entity;

import net.grizdor.pillaged.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class IllagerCaptainEntity extends AbstractIllager implements CrossbowAttackMob {
    private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW;
    private static final EntityDataAccessor<Boolean> IS_ARMED_WITH_CROSSBOW;
    private int usingTime;

    public IllagerCaptainEntity(EntityType<? extends AbstractIllager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedCrossbowAttackGoal(this, (double)1.0F, 8.0F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, (double)1.0F, false));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0F)
                .add(Attributes.ARMOR, 5.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.35F)
                .add(Attributes.ATTACK_DAMAGE, 5.0F)
                .add(Attributes.FOLLOW_RANGE, 32.0F);
    }

    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isChargingCrossbow()) {
            return IllagerArmPose.CROSSBOW_CHARGE;
        } else if (this.isHolding((is) -> is.getItem() instanceof CrossbowItem)) {
            return IllagerArmPose.CROSSBOW_HOLD;
        } else if (this.isAggressive()) {
            return IllagerArmPose.ATTACKING;
        } else if (this.isCelebrating()) {
            return IllagerArmPose.CELEBRATING;
        } else {
            return IllagerArmPose.CROSSED;
        }
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide && this.isAlive()) {
            // Swap Weapons
            if (this.isArmedWithCrossbow()) {
                if (this.usingTime-- <= 0 && !this.isChargingCrossbow()) {
                    this.setArmedWithCrossbow(false);
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.EMERALD_SWORD.get()));
                    this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
                }
            } else {
                if (this.random.nextFloat() < 0.5F && this.getTarget() != null && this.getTarget().distanceToSqr(this) > (double)121.0F) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
                    this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    this.usingTime = this.random.nextIntBetweenInclusive(10*20,30*20);
                    this.setArmedWithCrossbow(true);
                }
            }
        }
        super.aiStep();
    }

    @Override
    public void applyRaidBuffs(ServerLevel serverLevel, int wave, boolean bool) {

    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_CHARGING_CROSSBOW, false);
        builder.define(IS_ARMED_WITH_CROSSBOW, false);
    }

    // finalizeSpawn & populateDefaultEquipmentSlots
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData spawngroupdata = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        RandomSource randomsource = level.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, difficulty);
        this.populateDefaultEquipmentEnchantments(level, randomsource, difficulty);
        return spawngroupdata;
    }

    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        if (this.getCurrentRaid() == null) {
            this.setItemSlot(EquipmentSlot.HEAD, Raid.getLeaderBannerInstance(this.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN)));
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.EMERALD_SWORD.get()));
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    // Crossbow & Ranged Attack
    public boolean isArmedWithCrossbow() {
        return (Boolean)this.getEntityData().get(IS_ARMED_WITH_CROSSBOW);
    }

    public void setArmedWithCrossbow(boolean isArmed) {
        this.entityData.set(IS_ARMED_WITH_CROSSBOW, isArmed);
    }

    public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeapon) {
        return projectileWeapon == Items.CROSSBOW;
    }

    public boolean isChargingCrossbow() {
        return (Boolean)this.entityData.get(IS_CHARGING_CROSSBOW);
    }

    @Override
    public void setChargingCrossbow(boolean isCharging) {
        this.entityData.set(IS_CHARGING_CROSSBOW, isCharging);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        this.performCrossbowAttack(this, 1.6F);
    }

    // SoundEvents Getters
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PILLAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.PILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }

    // Assignment of static variables
    static {
        IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(IllagerCaptainEntity.class, EntityDataSerializers.BOOLEAN);
        IS_ARMED_WITH_CROSSBOW = SynchedEntityData.defineId(IllagerCaptainEntity.class, EntityDataSerializers.BOOLEAN);
    }
}
