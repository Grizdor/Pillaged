package net.grizdor.pillaged.potion;

import net.grizdor.pillaged.Pillaged;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, Pillaged.MOD_ID);

    public static final Holder<Potion> RESISTANCE = POTIONS.register("resistance",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 0)));
    public static final Holder<Potion> LONG_RESISTANCE = POTIONS.register("long_resistance",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 9600, 0)));
    public static final Holder<Potion> STRONG_RESISTANCE = POTIONS.register("strong_resistance",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800, 1)));

    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }
}
