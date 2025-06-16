package net.grizdor.pillaged.datagen;

import net.grizdor.pillaged.Pillaged;
import net.grizdor.pillaged.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Pillaged.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.SWORDS)
                .add(ModItems.EMERALD_SWORD.get())
                .add(ModItems.DARK_EMERALD_SWORD.get());

        tag(ItemTags.SHOVELS)
                .add(ModItems.EMERALD_SHOVEL.get())
                .add(ModItems.DARK_EMERALD_SHOVEL.get());

        tag(ItemTags.PICKAXES)
                .add(ModItems.EMERALD_PICKAXE.get())
                .add(ModItems.DARK_EMERALD_PICKAXE.get());

        tag(ItemTags.AXES)
                .add(ModItems.EMERALD_AXE.get())
                .add(ModItems.DARK_EMERALD_AXE.get());

        tag(ItemTags.HOES)
                .add(ModItems.EMERALD_HOE.get())
                .add(ModItems.DARK_EMERALD_HOE.get());
    }
}
