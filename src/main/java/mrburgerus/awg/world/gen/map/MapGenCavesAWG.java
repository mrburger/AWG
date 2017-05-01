package mrburgerus.awg.world.gen.map;

// Generates Caves in alpha style
// Updated from ted80
// STRONG WORK IN PROGRESS

import com.google.common.base.Objects;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class MapGenCavesAWG extends MapGenAWG
{
    protected void addTunnel(int chunkX, int chunkZ, ChunkPrimer chunkPrimer, double doubleIn1, double doubleIn2, double doubleIn3, float floatIn1, float floatIn2, float floatIn3, int intIn1, int intIn2, double doubleIn4)
    {
        double doubleVal1 = (double)(chunkX * 16 + 8);
        double doubleVal2 = (double)(chunkZ * 16 + 8);
        float floatVal1 = 0.0F;
        float floatVal2 = 0.0F;
        Random random = new Random(rand.nextLong());

        if (intIn2 <= 0)
        {
            int intVal1 = this.range * 16 - 16;
            intIn2 = intVal1 - random.nextInt(intVal1 / 4);
        }

        boolean bool1 = false;

        if (intIn1 == -1)
        {
            intIn1 = intIn2 / 2;
            bool1 = true;
        }

        int intVal2 = random.nextInt(intIn2 / 2) + intIn2 / 4;

        for (boolean bool2 = random.nextInt(6) == 0; intIn1 < intIn2; ++intIn1)
        {
            double doubleVal3 = 1.5D + (double)(MathHelper.sin((float) intIn1 * (float) Math.PI / (float) intIn2) * floatIn1 * 1.0F);
            double doubleVal4 = doubleVal3 * doubleIn4;
            float floatVal3 = MathHelper.cos(floatIn3);
            float floatVal4 = MathHelper.sin(floatIn3);
            doubleIn1 += (double)(MathHelper.cos(floatIn2) * floatVal3);
            doubleIn2 += (double)floatVal4;
            doubleIn3 += (double)(MathHelper.sin(floatIn2) * floatVal3);

            if (bool2)
            {
                floatIn3 = floatIn3 * 0.92F;
            }
            else
            {
                floatIn3 = floatIn3 * 0.7F;
            }
            //EQUALS LINE 70
            floatIn3 += floatVal2 * 0.1F;
            floatIn2 += floatVal1 * 0.1F;
            floatVal2 *= 0.9F;
            floatVal1 *= 0.75F;
            floatVal2 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            floatVal1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4F;

            if (!bool1 && intIn1 == intVal2 && floatIn1 > 1.0F && intIn2 > 0)
            {
                this.addTunnel(chunkX, chunkZ, chunkPrimer, doubleIn1, doubleIn2, doubleIn3, random.nextFloat() * 0.5F + 0.5F, floatIn2 - ((float)Math.PI / 2F), floatIn3 / 3.0F, intIn1, intIn2, 1.0D);
                this.addTunnel(chunkX, chunkZ, chunkPrimer, doubleIn1, doubleIn2, doubleIn3, random.nextFloat() * 0.5F + 0.5F, floatIn2 + ((float)Math.PI / 2F), floatIn3 / 3.0F, intIn1, intIn2, 1.0D);
                return;
            }

            if (bool1 || random.nextInt(4) != 0)
            {
                double doubleVal5 = doubleIn1 - doubleVal1;
                double doubleVal6 = doubleIn3 - doubleVal2;
                double doubleVal7 = (double)(intIn2 - intIn1);
                double doubleVal8 = (double)(floatIn1 + 2.0F + 16.0F);

                if ((doubleVal5 * doubleVal5 + doubleVal6 * doubleVal6) - doubleVal7 * doubleVal7 > doubleVal8 * doubleVal8)
                {
                    return;
                }

                if (doubleIn1 >= doubleVal1 - 16.0D - doubleVal3 * 2.0D && doubleIn3 >= doubleVal2 - 16.0D - doubleVal3 * 2.0D && doubleIn1 <= doubleVal1 + 16.0D + doubleVal3 * 2.0D && doubleIn3 <= doubleVal2 + 16.0D + doubleVal3 * 2.0D)
                {
                    int intVal3 = MathHelper.floor(doubleIn1 - doubleVal3) - chunkX * 16 - 1;
                    int intVal4 = MathHelper.floor(doubleIn1 + doubleVal3) - chunkX * 16 + 1;
                    int intVal5 = MathHelper.floor(doubleIn2 - doubleVal4) - 1;
                    int intVal6 = MathHelper.floor(doubleIn2 + doubleVal4) + 1;
                    int intVal7 = MathHelper.floor(doubleIn3 - doubleVal3) - chunkZ * 16 - 1;
                    int intVal8 = MathHelper.floor(doubleIn3 + doubleVal3) - chunkZ * 16 + 1;

                    if (intVal3 < 0)
                    {
                        intVal3 = 0;
                    }

                    if (intVal4 > 16)
                    {
                        intVal4 = 16;
                    }

                    if (intVal5 < 1)
                    {
                        intVal5 = 1;
                    }

                    if (intVal6 > 120)
                    {
                        intVal6 = 120;
                    }

                    if (intVal7 < 0)
                    {
                        intVal7 = 0;
                    }

                    if (intVal8 > 16)
                    {
                        intVal8 = 16;
                    }

                    boolean bool3 = false;

                    for (int counter1 = intVal3; !bool3 && counter1 < intVal4; ++counter1)
                    {
                        for (int counter2 = intVal7; !bool3 && counter2 < intVal8; ++counter2)
                        {
                            for (int counter3 = intVal6 + 1; !bool3 && counter3 >= intVal5 - 1; --counter3)
                            {
                                if (counter3 >= 0 && counter3 < 256)
                                {
                                    if (isOceanBlock(chunkPrimer, counter1, counter3, counter2, chunkX, chunkZ))
                                    {
                                        bool3 = true;
                                    }

                                    if (counter3 != intVal5 - 1 && counter1 != intVal3 && counter1 != intVal4 - 1 && counter2 != intVal7 && counter2 != intVal8 - 1)
                                    {
                                        counter3 = intVal5;
                                    }
                                }
                            }
                        }
                    }

                    if (!bool3)
                    {
                        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                        for (int counter4 = intVal3; counter4 < intVal4; ++counter4)
                        {
                            double doubleVal9 = ((double)(counter4 + chunkX * 16) + 0.5D - doubleIn1) / doubleVal3;

                            for (int counter5 = intVal7; counter5 < intVal8; ++counter5)
                            {
                                double doubleVal10 = ((double)(counter5 + chunkZ * 16) + 0.5D - doubleIn3) / doubleVal3;
                                boolean bool4 = false;

                                if (doubleVal9 * doubleVal9 + doubleVal10 * doubleVal10 < 1.0D)
                                {
                                    for (int counter6 = intVal6; counter6 > intVal5; --counter6)
                                    {
                                        double doubleVal11 = ((double)(counter6 - 1) + 0.5D - doubleIn2) / doubleVal4;

                                        //if (doubleVal11 > -0.7D && doubleVal9 * doubleVal9 + doubleVal11 * doubleVal11 + doubleVal10 * doubleVal10 < 1.0D)
                                        if (doubleVal11 > -0.69999999999999996D && doubleVal9 * doubleVal9 + doubleVal11 * doubleVal11 + doubleVal10 * doubleVal10 < 1.0D)
                                        {
                                            IBlockState iblockstate1 = chunkPrimer.getBlockState(counter4, counter6, counter5);
                                            IBlockState iblockstate2 = (IBlockState) Objects.firstNonNull(chunkPrimer.getBlockState(counter4, counter6 + 1, counter5), Blocks.AIR.getDefaultState());

                                            if (isTopBlock(chunkPrimer, counter4, counter6, counter5, chunkX, chunkZ))
                                            {
                                                bool2 = true;
                                            }

                                            digBlock(chunkPrimer, counter4, counter6, counter5, chunkX, chunkZ, bool2, iblockstate1, iblockstate2);
                                        }
                                    }
                                }
                            }
                        }

                        if (bool1)
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    protected boolean canReplaceBlock(IBlockState p_175793_1_, IBlockState p_175793_2_)
    {
        return p_175793_1_.getBlock() == Blocks.STONE ? true : (p_175793_1_.getBlock() == Blocks.DIRT ? true : (p_175793_1_.getBlock() == Blocks.GRASS ? true : (p_175793_1_.getBlock() == Blocks.HARDENED_CLAY ? true : (p_175793_1_.getBlock() == Blocks.STAINED_HARDENED_CLAY ? true : (p_175793_1_.getBlock() == Blocks.SANDSTONE ? true : (p_175793_1_.getBlock() == Blocks.RED_SANDSTONE ? true : (p_175793_1_.getBlock() == Blocks.MYCELIUM ? true : (p_175793_1_.getBlock() == Blocks.SNOW_LAYER ? true : (p_175793_1_.getBlock() == Blocks.SAND || p_175793_1_.getBlock() == Blocks.GRAVEL) && p_175793_2_.getMaterial() != Material.WATER))))))));
    }

    /**
     * Recursively called by generate()
     */
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int x, int z, ChunkPrimer chunkPrimerIn)
    {
        int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);

        if (this.rand.nextInt(7) != 0)
        {
            i = 0;
        }

        for (int j = 0; j < i; ++j)
        {
            double d0 = (double)(chunkX * 16 + this.rand.nextInt(16));
            double d1 = (double)this.rand.nextInt(this.rand.nextInt(120) + 8);
            double d2 = (double)(chunkZ * 16 + this.rand.nextInt(16));
            int k = 1;

            if (this.rand.nextInt(4) == 0)
            {
                //this.addRoom(this.rand.nextLong(), x, z, chunkPrimerIn, d0, d1, d2);
                k += this.rand.nextInt(4);
            }

            for (int l = 0; l < k; ++l)
            {
                float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();

                if (this.rand.nextInt(10) == 0)
                {
                    f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
                }

                this.addTunnel(x, z, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
            }
        }
    }

    protected boolean isOceanBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        net.minecraft.block.Block block = data.getBlockState(x, y, z).getBlock();
        return block== Blocks.FLOWING_WATER || block == Blocks.WATER;
    }

    //Determine if the block at the specified location is the top block for the biome, we take into account
    //Vanilla bugs to make sure that we generate the map the same way vanilla does.
    private boolean isTopBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        net.minecraft.world.biome.Biome biome = worldObj.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState state = data.getBlockState(x, y, z);
        return state.getBlock() == Blocks.GRASS.getDefaultState();
    }


    protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, IBlockState state, IBlockState up)
    {
        net.minecraft.world.biome.Biome biome = worldObj.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState top = biome.topBlock;
        IBlockState filler = biome.fillerBlock;

        if (this.canReplaceBlock(state, up) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock())
        {
            if (y - 1 < 10)
            {
                data.setBlockState(x, y, z, Blocks.LAVA.getDefaultState());
            }
            else
            {
                data.setBlockState(x, y, z, Blocks.AIR.getDefaultState());

                if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock())
                {
                    data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
                }
            }
        }
    }
}
