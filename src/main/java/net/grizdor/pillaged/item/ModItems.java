package net.grizdor.pillaged.item;

import net.grizdor.pillaged.Pillaged;
import net.grizdor.pillaged.entity.ModEntities;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Pillaged.MOD_ID);

    // SIMPLE ITEMS
    public static final DeferredItem<Item> DARK_EMERALD = ITEMS.register("dark_emerald",
            () -> new Item(new Item.Properties()));

    // TOOLS
    public static final DeferredItem<SwordItem> EMERALD_SWORD = ITEMS.register("emerald_sword",
            () -> new SwordItem(ModToolTiers.EMERALD, new Item.Properties()
                            .attributes(SwordItem.createAttributes(ModToolTiers.EMERALD, 5, -2.4F))));
    public static final DeferredItem<ShovelItem> EMERALD_SHOVEL = ITEMS.register("emerald_shovel",
            () -> new ShovelItem(ModToolTiers.EMERALD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.EMERALD, 3.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe",
            () -> new PickaxeItem(ModToolTiers.EMERALD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.EMERALD, 3.0F, -2.8F))));
    public static final DeferredItem<AxeItem> EMERALD_AXE = ITEMS.register("emerald_axe",
            () -> new AxeItem(ModToolTiers.EMERALD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.EMERALD, 8.0F, -3.1F))));
    public static final DeferredItem<HoeItem> EMERALD_HOE = ITEMS.register("emerald_hoe",
            () -> new HoeItem(ModToolTiers.EMERALD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.EMERALD, -3.0F, -3.0F))));

    public static final DeferredItem<SwordItem> DARK_EMERALD_SWORD = ITEMS.register("dark_emerald_sword",
            () -> new SwordItem(ModToolTiers.DARK_EMERALD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.DARK_EMERALD, 6, -2.4F))));
    public static final DeferredItem<ShovelItem> DARK_EMERALD_SHOVEL = ITEMS.register("dark_emerald_shovel",
            () -> new ShovelItem(ModToolTiers.DARK_EMERALD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.DARK_EMERALD, 4.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> DARK_EMERALD_PICKAXE = ITEMS.register("dark_emerald_pickaxe",
            () -> new PickaxeItem(ModToolTiers.DARK_EMERALD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.DARK_EMERALD, 4.0F, -2.8F))));
    public static final DeferredItem<AxeItem> DARK_EMERALD_AXE = ITEMS.register("dark_emerald_axe",
            () -> new AxeItem(ModToolTiers.DARK_EMERALD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.DARK_EMERALD, 8.0F, -3.0F))));
    public static final DeferredItem<HoeItem> DARK_EMERALD_HOE = ITEMS.register("dark_emerald_hoe",
            () -> new HoeItem(ModToolTiers.DARK_EMERALD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.DARK_EMERALD, 0.0F, -3.0F))));

    // SPAWN EGGS
    public static final DeferredItem<Item> ILLAGER_CAPTAIN_SPAWN_EGG = ITEMS.register("illager_captain_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.ILLAGER_CAPTAIN, 0x1823360, 0x959B9B, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
