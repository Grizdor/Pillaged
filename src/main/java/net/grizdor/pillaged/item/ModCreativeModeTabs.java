package net.grizdor.pillaged.item;

import net.grizdor.pillaged.Pillaged;
import net.grizdor.pillaged.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pillaged.MOD_ID);

    public static final Supplier<CreativeModeTab> PILLAGED_TAB = CREATIVE_MODE_TAB.register("pillaged_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.PILLAGER_DEEPSLATE.get().asItem().getDefaultInstance().getItem()))
                    .title(Component.translatable("itemGroup.pillaged"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .displayItems((itemDisplayParameters, output) -> {
                        // BLOCKS
                        output.accept(ModBlocks.PILLAGER_DEEPSLATE);
                        output.accept(ModBlocks.DARK_EMERALD_ORE);
                        output.accept(ModBlocks.DEEPSLATE_DARK_EMERALD_ORE);
                        // ITEMS
                        output.accept(ModItems.EMERALD_SWORD);
                        output.accept(ModItems.EMERALD_SHOVEL);
                        output.accept(ModItems.EMERALD_PICKAXE);
                        output.accept(ModItems.EMERALD_AXE);
                        output.accept(ModItems.EMERALD_HOE);
                        output.accept(ModItems.DARK_EMERALD);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
