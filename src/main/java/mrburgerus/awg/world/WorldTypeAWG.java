package mrburgerus.awg.world;

import mrburgerus.awg.world.gen.ChunkProviderAWG;
import net.minecraft.init.Biomes;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class WorldTypeAWG extends WorldType
{
    //Variables
    public static ChunkProviderAWG chunkProvider;
    private final boolean hasNotificationData;

    public WorldTypeAWG(String name)
    {
        super(name);
        this.hasNotificationData = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean showWorldInfoNotice()
    {
        return this.hasNotificationData;
    }

    @Override @Nonnull
    public BiomeProvider getBiomeProvider(@Nonnull World world)
    {
        // DEFAULT
        //return new BiomeProviderSingle(AWG.alphaBiome);
        //NEW
        return new BiomeProviderSingle(Biomes.PLAINS);
    }


    @Override @Nonnull
    public IChunkGenerator getChunkGenerator(@Nonnull World world, String generatorOptions)
    {
        chunkProvider = new ChunkProviderAWG(world, world.getWorldInfo().isMapFeaturesEnabled());
        return chunkProvider;
    }

    @Override
    public boolean isCustomizable() {
        return false;
    }
}
