package net.grizdor.pillaged.datagen;

import net.grizdor.pillaged.Pillaged;
import net.grizdor.pillaged.entity.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider,
                                    @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Pillaged.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EntityTypeTags.ILLAGER)
                .add(ModEntities.ILLAGER_CAPTAIN.get());

        tag(EntityTypeTags.ILLAGER_FRIENDS)
                .add(ModEntities.ILLAGER_CAPTAIN.get());
    }
}
