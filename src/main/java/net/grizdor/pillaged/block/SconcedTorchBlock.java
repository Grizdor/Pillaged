package net.grizdor.pillaged.block;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SconcedTorchBlock extends BaseTorchBlock {
    protected static final MapCodec<SimpleParticleType> PARTICLE_OPTIONS_FIELD;
    public static final MapCodec<SconcedTorchBlock> CODEC;
    protected final SimpleParticleType flameParticle;

    @Override
    protected MapCodec<? extends SconcedTorchBlock> codec() {
        return CODEC;
    }

    protected SconcedTorchBlock(SimpleParticleType flameParticle, Properties properties) {
        super(properties);
        this.flameParticle = flameParticle;
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double d0 = (double)pos.getX() + (double)0.5F;
        double d1 = (double)pos.getY() + 0.7;
        double d2 = (double)pos.getZ() + (double)0.5F;
        level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, (double)0.0F, (double)0.0F, (double)0.0F);
        level.addParticle(this.flameParticle, d0, d1, d2, (double)0.0F, (double)0.0F, (double)0.0F);
    }

    static {
        PARTICLE_OPTIONS_FIELD = BuiltInRegistries.PARTICLE_TYPE.byNameCodec().comapFlatMap((particleType) -> {
            DataResult dataResult;
            if (particleType instanceof SimpleParticleType simpleparticletype) {
                dataResult = DataResult.success(simpleparticletype);
            } else {
                dataResult = DataResult.error(() -> "Not a SimpleParticleType: " + String.valueOf(particleType));
            }
            return dataResult;

        }, (object) -> (net.minecraft.core.particles.ParticleType<?>) object).fieldOf("particle_options");

        CODEC = RecordCodecBuilder.mapCodec(
                (blockInstance) -> blockInstance.group(
                        PARTICLE_OPTIONS_FIELD.forGetter(
                                (block) -> block.flameParticle), propertiesCodec()).apply(
                                        blockInstance, SconcedTorchBlock::new));
    }
}
