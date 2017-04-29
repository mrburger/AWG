package mrburgerus.awg.world.gen.map;

import com.google.common.base.Objects;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

import java.util.Random;

// HOLY INCOMPLETE BATMAN


public class NewMapGenCavesAWG extends MapGenBase
{
    protected Random rand = new Random();
    protected int range = 8;

    protected void caveFunction(int i, int j, ChunkPrimer chunkPrimer, double d, double d1,
                                double d2) {
        addTunnel(this.rand.nextLong(), i, j, chunkPrimer, d, d1, d2, 1.0F + rand.nextFloat() * 6F, 0.0F, 0.0F, -1, -1, 0.5D);
    }


    /*
    protected void releaseEntitySkin(int chunkX, int chunkZ, ChunkPrimer primer, double doubleVal1, double doubleVal2,
                                     double doubleVal3, float floatVal1, float floatVal2, float floatVal3, int intVal3, int intVal4,
                                     double doubleVal4) {

        //l maps to p_180702_16_
        //k maps to p_180702_15_

        double d0 = chunkX * 16 + 8;
        double d1 = chunkZ * 16 + 8;
        float f = 0.0F;
        float f1 = 0.0F;
        Random random = new Random(rand.nextLong());
        if (intVal4 <= 0) {
            int i1 = range * 16 - 16;
            intVal4 = i1 - random.nextInt(i1 / 4);
        }
        boolean flag2 = false;
        if (intVal3 == -1) {
            intVal3 = intVal4 / 2;
            flag2 = true;
        }

        int j = random.nextInt(intVal4 / 2) + intVal4 / 4;

        //maps to flag
        boolean flag1 = random.nextInt(6) == 0;

        for (; intVal3 < intVal4; intVal3++) {
            double d6 = 1.5D + (double) (MathHelper.sin(((float) intVal3 * 3.141593F) / (float) intVal4) * floatVal1 * 1.0F);
            double d7 = d6 * doubleVal4;
            float f5 = MathHelper.cos(floatVal3);
            float f6 = MathHelper.sin(floatVal3);
            doubleVal1 += MathHelper.cos(floatVal2) * f5;
            doubleVal2 += f6;
            doubleVal3 += MathHelper.sin(floatVal2) * f5;
            if (flag1) {
                floatVal3 *= 0.92F;
            } else {
                floatVal3 *= 0.7F;
            }
            floatVal3 += f1 * 0.1F;
            floatVal2 += f * 0.1F;
            f1 *= 0.9F;
            f *= 0.75F;
            f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4F;
            if (!flag2 && intVal3 == j && floatVal1 > 1.0F) {
                releaseEntitySkin(chunkX, chunkZ, primer, doubleVal1, doubleVal2, doubleVal3, random.nextFloat() * 0.5F + 0.5F, floatVal2 - 1.570796F, floatVal3 / 3F, intVal3, intVal4, 1.0D);
                releaseEntitySkin(chunkX, chunkZ, primer, doubleVal1, doubleVal2, doubleVal3, random.nextFloat() * 0.5F + 0.5F, floatVal2 + 1.570796F, floatVal3 / 3F, intVal3, intVal4, 1.0D);
                return;
            }
            if (!flag2 && random.nextInt(4) == 0) {
                continue;
            }
            double d8a = doubleVal1 - d0;
            double d9a = doubleVal3 - d1;
            double d10a = intVal4 - intVal3;
            double d11 = floatVal1 + 2.0F + 16F;
            if ((d8a * d8a + d9a * d9a) - d10a * d10a > d11 * d11) {
                return;
            }
            if (doubleVal1 < d0 - 16D - d6 * 2D || doubleVal3 < d1 - 16D - d6 * 2D || doubleVal1 > d0 + 16D + d6 * 2D || doubleVal3 > d1 + 16D + d6 * 2D) {
                continue;
            }
            //maps to k2
            int k2 = MathHelper.floor(doubleVal1 - d6) - chunkX * 16 - 1;
            //maps to k
            int k = (MathHelper.floor(doubleVal1 + d6) - chunkX * 16) + 1;
            //maps to l2
            int l2 = MathHelper.floor(doubleVal2 - d7) - 1;
            //maps to l
            int yVal = MathHelper.floor(doubleVal2 + d7) + 1;
            //maps to i3
            int i3 = MathHelper.floor(doubleVal3 - d6) - chunkZ * 16 - 1;
            //maps to i1
            int i2 = (MathHelper.floor(doubleVal3 + d6) - chunkZ * 16) + 1;
            if (k2 < 0) {
                k2 = 0;
            }
            if (k > 16) {
                k = 16;
            }
            if (l2 < 1) {
                l2 = 1;
            }
            if (yVal > 120) {
                yVal = 120;
            }
            if (i3 < 0) {
                i3 = 0;
            }
            if (i2 > 16) {
                i2 = 16;
            }

            boolean flag3 = false;


            for (int j1 = k2; !flag3 && j1 < k; ++j1) {
                for (int k1 = i3; !flag3 && k1 < i2; ++k1) {
                    for (int l1 = yVal + 1; !flag3 && l1 >= l2 - 1; --l1) {
                        if (l1 >= 0 && l1 < 256) {
                            if (isOceanBlock(primer, j1, l1, k1, chunkX, chunkZ)) {
                                flag3 = true;
                            }

                            if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i2 - 1) {
                                l1 = l2;
                            }
                        }
                    }
                }
            }


            //TOTALLY REFACTORED VANILLA CODE
            if (!flag3) {
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int j3 = k2; j3 < k; ++j3) {
                    double d10 = ((double) (j3 + chunkX * 16) + 0.5D - doubleVal1) / d6;

                    for (int counter1 = yVal; counter1 < i2; ++counter1) {
                        double d8 = ((double) (counter1 + chunkZ * 16) + 0.5D - doubleVal3) / d6;
                        boolean flagsAreGreat = false;

                        if (d10 * d10 + d8 * d8 < 1.0D) {
                            for (int j2 = yVal; j2 > l2; --j2) {
                                double d9 = ((double) (j2 - 1) + 0.5D - doubleVal2) / d7;

                                if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
                                    IBlockState iblockstate1 = primer.getBlockState(j3, j2, counter1);
                                    IBlockState iblockstate2 = (IBlockState) Objects.firstNonNull(primer.getBlockState(j3, j2 + 1, counter1), Blocks.AIR);

                                    if (isTopBlock(primer, j3, j2, counter1, chunkX, chunkZ)) {
                                        flagsAreGreat = true;
                                    }

                                    digBlock(primer, j3, j2, counter1, chunkX, chunkZ, flagsAreGreat, iblockstate1, iblockstate2);
                                }
                            }
                        }
                    }
                }

                if (flag2) {
                    break;
                }

                //END OF LOOP
            }

        }
    }
    */

    protected void addTunnel(long p_180702_1_, int p_180702_3_, int p_180702_4_, ChunkPrimer primer, double p_180702_6_, double p_180702_8_, double p_180702_10_, float p_180702_12_, float p_180702_13_, float p_180702_14_, int p_180702_15_, int p_180702_16_, double p_180702_17_)
    {
        double d0 = (double)(p_180702_3_ * 16 + 8);
        double d1 = (double)(p_180702_4_ * 16 + 8);
        float f = 0.0F;
        float f1 = 0.0F;
        Random random = new Random(p_180702_1_);

        if (p_180702_16_ <= 0)
        {
            int i = this.range * 16 - 16;
            p_180702_16_ = i - random.nextInt(i / 4);
        }

        boolean flag2 = false;

        if (p_180702_15_ == -1)
        {
            p_180702_15_ = p_180702_16_ / 2;
            flag2 = true;
        }

        int j = random.nextInt(p_180702_16_ / 2) + p_180702_16_ / 4;

        for (boolean flag = random.nextInt(6) == 0; p_180702_15_ < p_180702_16_; ++p_180702_15_)
        {
            double d2 = 1.5D + (double)(MathHelper.sin((float)p_180702_15_ * (float)Math.PI / (float)p_180702_16_) * p_180702_12_);
            double d3 = d2 * p_180702_17_;
            float f2 = MathHelper.cos(p_180702_14_);
            float f3 = MathHelper.sin(p_180702_14_);
            p_180702_6_ += (double)(MathHelper.cos(p_180702_13_) * f2);
            p_180702_8_ += (double)f3;
            p_180702_10_ += (double)(MathHelper.sin(p_180702_13_) * f2);

            if (flag)
            {
                p_180702_14_ = p_180702_14_ * 0.92F;
            }
            else
            {
                p_180702_14_ = p_180702_14_ * 0.7F;
            }

            p_180702_14_ = p_180702_14_ + f1 * 0.1F;
            p_180702_13_ += f * 0.1F;
            f1 = f1 * 0.9F;
            f = f * 0.75F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if (!flag2 && p_180702_15_ == j && p_180702_12_ > 1.0F && p_180702_16_ > 0)
            {
                this.addTunnel(random.nextLong(), p_180702_3_, p_180702_4_, primer, p_180702_6_, p_180702_8_, p_180702_10_, random.nextFloat() * 0.5F + 0.5F, p_180702_13_ - ((float)Math.PI / 2F), p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
                this.addTunnel(random.nextLong(), p_180702_3_, p_180702_4_, primer, p_180702_6_, p_180702_8_, p_180702_10_, random.nextFloat() * 0.5F + 0.5F, p_180702_13_ + ((float)Math.PI / 2F), p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
                return;
            }

            if (flag2 || random.nextInt(4) != 0)
            {
                double d4 = p_180702_6_ - d0;
                double d5 = p_180702_10_ - d1;
                double d6 = (double)(p_180702_16_ - p_180702_15_);
                double d7 = (double)(p_180702_12_ + 2.0F + 16.0F);

                if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7)
                {
                    return;
                }

                if (p_180702_6_ >= d0 - 16.0D - d2 * 2.0D && p_180702_10_ >= d1 - 16.0D - d2 * 2.0D && p_180702_6_ <= d0 + 16.0D + d2 * 2.0D && p_180702_10_ <= d1 + 16.0D + d2 * 2.0D)
                {
                    int k2 = MathHelper.floor(p_180702_6_ - d2) - p_180702_3_ * 16 - 1;
                    int k = MathHelper.floor(p_180702_6_ + d2) - p_180702_3_ * 16 + 1;
                    int l2 = MathHelper.floor(p_180702_8_ - d3) - 1;
                    int l = MathHelper.floor(p_180702_8_ + d3) + 1;
                    int i3 = MathHelper.floor(p_180702_10_ - d2) - p_180702_4_ * 16 - 1;
                    int i1 = MathHelper.floor(p_180702_10_ + d2) - p_180702_4_ * 16 + 1;

                    if (k2 < 0)
                    {
                        k2 = 0;
                    }

                    if (k > 16)
                    {
                        k = 16;
                    }

                    if (l2 < 1)
                    {
                        l2 = 1;
                    }

                    if (l > 248)
                    {
                        l = 248;
                    }

                    if (i3 < 0)
                    {
                        i3 = 0;
                    }

                    if (i1 > 16)
                    {
                        i1 = 16;
                    }

                    boolean flag3 = false;

                    for (int j1 = k2; !flag3 && j1 < k; ++j1)
                    {
                        for (int k1 = i3; !flag3 && k1 < i1; ++k1)
                        {
                            for (int l1 = l + 1; !flag3 && l1 >= l2 - 1; --l1)
                            {
                                if (l1 >= 0 && l1 < 256)
                                {
                                    if (isOceanBlock(primer, j1, l1, k1, p_180702_3_, p_180702_4_))
                                    {
                                        flag3 = true;
                                    }

                                    if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
                                    {
                                        l1 = l2;
                                    }
                                }
                            }
                        }
                    }

                    if (!flag3)
                    {
                        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                        for (int j3 = k2; j3 < k; ++j3)
                        {
                            double d10 = ((double)(j3 + p_180702_3_ * 16) + 0.5D - p_180702_6_) / d2;

                            for (int i2 = i3; i2 < i1; ++i2)
                            {
                                double d8 = ((double)(i2 + p_180702_4_ * 16) + 0.5D - p_180702_10_) / d2;
                                boolean flag1 = false;

                                if (d10 * d10 + d8 * d8 < 1.0D)
                                {
                                    for (int j2 = l; j2 > l2; --j2)
                                    {
                                        double d9 = ((double)(j2 - 1) + 0.5D - p_180702_8_) / d3;

                                        if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D)
                                        {
                                            IBlockState iblockstate1 = primer.getBlockState(j3, j2, i2);
                                            IBlockState iblockstate2 = (IBlockState)Objects.firstNonNull(primer.getBlockState(j3, j2 + 1, i2), Blocks.AIR.getDefaultState());

                                            if (isTopBlock(primer, j3, j2, i2, p_180702_3_, p_180702_4_))
                                            {
                                                flag1 = true;
                                            }

                                            digBlock(primer, j3, j2, i2, p_180702_3_, p_180702_4_, flag1, iblockstate1, iblockstate2);
                                        }
                                    }
                                }
                            }
                        }

                        if (flag2)
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int i, int j, ChunkPrimer chunkPrimerIn) {
        int i1 = rand.nextInt(rand.nextInt(rand.nextInt(40) + 1) + 1);
        if (rand.nextInt(15) != 0) {
            i1 = 0;
        }
        for (int j1 = 0; j1 < i1; j1++) {
            double d = i * 16 + rand.nextInt(16);
            double d1 = rand.nextInt(rand.nextInt(120) + 8);
            double d2 = j * 16 + rand.nextInt(16);
            int k1 = 1;
            if (rand.nextInt(4) == 0) {
                caveFunction(i, j, chunkPrimerIn, d, d1, d2);
                k1 += rand.nextInt(4);
            }
            for (int l1 = 0; l1 < k1; l1++) {
                float f = rand.nextFloat() * 3.141593F * 2.0F;
                float f1 = ((rand.nextFloat() - 0.5F) * 2.0F) / 8F;
                float f2 = rand.nextFloat() * 2.0F + rand.nextFloat();
                addTunnel(this.rand.nextLong(), i, j, chunkPrimerIn, d, d1, d2, f2, f, f1, 0, 0, 1.0D);
            }

        }

    }

    //VANILLA
    protected boolean isOceanBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ) {
        net.minecraft.block.Block block = data.getBlockState(x, y, z).getBlock();
        return block == Blocks.FLOWING_WATER || block == Blocks.WATER;
    }

    private boolean isTopBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        net.minecraft.world.biome.Biome biome = world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState state = data.getBlockState(x, y, z);
        return (isExceptionBiome(biome) ? state.getBlock() == Blocks.GRASS : state.getBlock() == biome.topBlock);
    }

    //just to make the vanilla methods happy
    private boolean isExceptionBiome(net.minecraft.world.biome.Biome biome)
    {
        if (biome == net.minecraft.init.Biomes.BEACH) return true;
        if (biome == net.minecraft.init.Biomes.DESERT) return true;
        return false;
    }

    protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, IBlockState state, IBlockState up)
    {
        net.minecraft.world.biome.Biome biome = world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
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
    protected boolean canReplaceBlock(IBlockState blockState1, IBlockState blockState2)
    {
        return blockState1.getBlock() == Blocks.STONE ? true : (blockState1.getBlock() == Blocks.DIRT ? true : (blockState1.getBlock() == Blocks.GRASS ? true : (blockState1.getBlock() == Blocks.HARDENED_CLAY ? true : (blockState1.getBlock() == Blocks.STAINED_HARDENED_CLAY ? true : (blockState1.getBlock() == Blocks.SANDSTONE ? true : (blockState1.getBlock() == Blocks.RED_SANDSTONE ? true : (blockState1.getBlock() == Blocks.MYCELIUM ? true : (blockState1.getBlock() == Blocks.SNOW_LAYER ? true : (blockState1.getBlock() == Blocks.SAND || blockState1.getBlock() == Blocks.GRAVEL) && blockState2.getMaterial() != Material.WATER))))))));
    }
}
