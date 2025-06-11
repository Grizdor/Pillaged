package net.grizdor.pillaged.datagen;

import net.grizdor.pillaged.Pillaged;
import net.grizdor.pillaged.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Pillaged.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheldItem(ModItems.EMERALD_SWORD.get());
        handheldItem(ModItems.EMERALD_SHOVEL.get());
        handheldItem(ModItems.EMERALD_PICKAXE.get());
        handheldItem(ModItems.EMERALD_AXE.get());
        handheldItem(ModItems.EMERALD_HOE.get());
        basicItem(ModItems.DARK_EMERALD.get());
        handheldItem(ModItems.DARK_EMERALD_SWORD.get());
        handheldItem(ModItems.DARK_EMERALD_SHOVEL.get());
        handheldItem(ModItems.DARK_EMERALD_PICKAXE.get());
        handheldItem(ModItems.DARK_EMERALD_AXE.get());
        handheldItem(ModItems.DARK_EMERALD_HOE.get());
    }
}
