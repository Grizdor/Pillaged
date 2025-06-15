package net.grizdor.pillaged.event;

import net.grizdor.pillaged.Pillaged;
import net.grizdor.pillaged.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = Pillaged.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event){
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.SHULKER_SHELL, ModPotions.RESISTANCE);
        builder.addMix(ModPotions.RESISTANCE, Items.GLOWSTONE_DUST, ModPotions.STRONG_RESISTANCE);
        builder.addMix(ModPotions.RESISTANCE, Items.REDSTONE, ModPotions.LONG_RESISTANCE);


    }
}
