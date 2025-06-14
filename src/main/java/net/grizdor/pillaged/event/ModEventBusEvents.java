package net.grizdor.pillaged.event;

import net.grizdor.pillaged.Pillaged;
import net.grizdor.pillaged.entity.IllagerCaptainEntity;
import net.grizdor.pillaged.entity.IllagerCaptainModel;
import net.grizdor.pillaged.entity.ModEntities;
import net.grizdor.pillaged.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Pillaged.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(IllagerCaptainModel.LAYER_LOCATION, IllagerCaptainModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ILLAGER_CAPTAIN.get(), IllagerCaptainEntity.createAttributes().build());
    }
}
