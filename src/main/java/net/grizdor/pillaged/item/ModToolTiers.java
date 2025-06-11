package net.grizdor.pillaged.item;

import net.grizdor.pillaged.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class ModToolTiers {
    public static final Tier EMERALD = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_EMERALD_TOOL,
            800, 7.0F, 1.0F, 10,
            () -> Ingredient.of(Tags.Items.GEMS_EMERALD)
    );

    public static final Tier DARK_EMERALD = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_DARK_EMERALD_TOOL,
            1700, 8.0F, 1.0F, 18,
            () -> Ingredient.of(ModItems.DARK_EMERALD)
    );
}
