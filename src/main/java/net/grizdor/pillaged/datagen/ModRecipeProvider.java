package net.grizdor.pillaged.datagen;

import net.grizdor.pillaged.Pillaged;
import net.grizdor.pillaged.block.ModBlocks;
import net.grizdor.pillaged.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> DARK_EMERALD_SMELTABLES = List.of(ModBlocks.DARK_EMERALD_ORE, ModBlocks.DEEPSLATE_DARK_EMERALD_ORE);

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

        // SMELTING RECIPES
        oreSmelting(recipeOutput, DARK_EMERALD_SMELTABLES, RecipeCategory.MISC, ModItems.DARK_EMERALD, 1.0F, 200, "dark_emerald");

        // BLASTING RECIPES
        oreBlasting(recipeOutput, DARK_EMERALD_SMELTABLES, RecipeCategory.MISC, ModItems.DARK_EMERALD, 1.0F, 100, "dark_emerald");

        // STONECUTTER RECIPES
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.COBBLED_DEEPSLATE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.PILLAGER_DEEPSLATE.get(), 1)
                .unlockedBy("has_cobbled_deepslate", has(Blocks.COBBLED_DEEPSLATE.asItem()))
                .save(recipeOutput, "pillaged:pillager_deepslate_from_cobbled_deepslate_stonecutting");
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> ingredients,
                                      RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> ingredients,
                                      RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput recipeOutput, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipeFactory, List<ItemLike> ingredients,
            RecipeCategory category, ItemLike result, float experience, int cookingTime, String group, String suffix) {
        for(ItemLike itemlike : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, serializer, recipeFactory)
                    .group(group).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, Pillaged.MOD_ID + ":" + getItemName(result) + suffix + "_" + getItemName(itemlike));
        }
    }
}
