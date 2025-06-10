package net.grizdor.pillaged.datagen;

import net.grizdor.pillaged.block.ModBlocks;
import net.grizdor.pillaged.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // SHAPED RECIPES
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.EMERALD_SWORD.get())
                .pattern(" E ")
                .pattern(" E ")
                .pattern(" S ")
                .define('E', Items.EMERALD).define('S', Items.STICK)
                .unlockedBy("has_emerald", has(Items.EMERALD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.EMERALD_SHOVEL.get())
                .pattern(" E ")
                .pattern(" S ")
                .pattern(" S ")
                .define('E', Items.EMERALD).define('S', Items.STICK)
                .unlockedBy("has_emerald", has(Items.EMERALD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.EMERALD_PICKAXE.get())
                .pattern("EEE")
                .pattern(" S ")
                .pattern(" S ")
                .define('E', Items.EMERALD).define('S', Items.STICK)
                .unlockedBy("has_emerald", has(Items.EMERALD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.EMERALD_AXE.get())
                .pattern("EE ")
                .pattern("ES ")
                .pattern(" S ")
                .define('E', Items.EMERALD).define('S', Items.STICK)
                .unlockedBy("has_emerald", has(Items.EMERALD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.EMERALD_HOE.get())
                .pattern("EE ")
                .pattern(" S ")
                .pattern(" S ")
                .define('E', Items.EMERALD).define('S', Items.STICK)
                .unlockedBy("has_emerald", has(Items.EMERALD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.REINFORCED_DEEPSLATE)
                .pattern("BDB")
                .pattern("DBD")
                .pattern("BDB")
                .define('B', Items.BONE).define('D',Items.DEEPSLATE)
                .unlockedBy("has_deepslate", has(Items.DEEPSLATE))
                .unlockedBy("has_bone",has(Items.BONE))
                .save(recipeOutput);

        // STONECUTTER RECIPES
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.COBBLED_DEEPSLATE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.PILLAGER_DEEPSLATE.get(), 1)
                .unlockedBy("has_cobbled_deepslate", has(Blocks.COBBLED_DEEPSLATE.asItem()))
                .save(recipeOutput, "pillaged:pillager_deepslate_from_cobbled_deepslate_stonecutting");
    }
}
