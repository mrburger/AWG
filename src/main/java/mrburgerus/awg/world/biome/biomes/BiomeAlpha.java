package mrburgerus.awg.world.biome.biomes;

import mrburgerus.awg.AWG;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by mrburgerUS on 4/23/2017.
 */
public class BiomeAlpha extends Biome
{
    public BiomeAlpha(BiomeProperties properties) {
        super(properties);
        this.setRegistryName(AWG.MOD_ID, "alpha_biome");
    }

    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        return 0xABFF67;
    }


    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 0x4FFF2B;
    }
}
