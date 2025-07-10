package net.grizdor.pillaged.block;

import net.grizdor.pillaged.Pillaged;
import net.grizdor.pillaged.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Pillaged.MOD_ID);

    public static final DeferredBlock<Block> PILLAGER_DEEPSLATE = registerBlock("pillager_deepslate",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_DEEPSLATE)));

    public static final DeferredBlock<DropExperienceBlock> DARK_EMERALD_ORE = registerBlock("dark_emerald_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.EMERALD_ORE)));

    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_DARK_EMERALD_ORE = registerBlock("deepslate_dark_emerald_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_EMERALD_ORE)));

    public static final DeferredBlock<SconcedTorchBlock> SCONCED_TORCH = registerBlockNoItem("sconced_torch",
            () -> new SconcedTorchBlock(ParticleTypes.FLAME, BlockBehaviour.Properties.of()
                    .noCollission().instabreak().lightLevel(blockState -> 14).sound(SoundType.DEEPSLATE).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<SconcedWallTorchBlock> SCONCED_WALL_TORCH = registerBlockNoItem("sconced_wall_torch",
            () -> new SconcedWallTorchBlock(ParticleTypes.FLAME, BlockBehaviour.Properties.of()
                    .noCollission().instabreak().lightLevel(blockState -> 14).sound(SoundType.DEEPSLATE).pushReaction(PushReaction.DESTROY)));

    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
