package mrburgerus.awg.world.gen;

import mrburgerus.awg.AWG;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class WorldProviderAWG extends WorldProvider
{
    @Override
    public DimensionType getDimensionType() {
        return DimensionType.register(AWG.MOD_ID, "_alpha", 100, WorldProviderAWG.class, false);
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderAWG(world, world.getWorldInfo().isMapFeaturesEnabled());
    }
}
