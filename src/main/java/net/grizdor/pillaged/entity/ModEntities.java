package net.grizdor.pillaged.entity;

import net.grizdor.pillaged.Pillaged;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Pillaged.MOD_ID);

    public static final Supplier<EntityType<IllagerCaptainEntity>> ILLAGER_CAPTAIN =
            ENTITY_TYPES.register("illager_captain", () -> EntityType.Builder.of(IllagerCaptainEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F).build("illager_captain"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
