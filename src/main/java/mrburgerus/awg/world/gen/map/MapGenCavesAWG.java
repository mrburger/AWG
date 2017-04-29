package mrburgerus.awg.world.gen.map;

// Generates Caves in alpha style
// Updated from ted80
// STRONG WORK IN PROGRESS

import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

import java.util.Random;

public class MapGenCavesAWG extends MapGenBase {
    protected Random rand = new Random();
    protected int range = 8;

    protected void caveFunction(int i, int j, ChunkPrimer chunkPrimer, double d, double d1,
                                double d2) {
        releaseEntitySkin(i, j, chunkPrimer, d, d1, d2, 1.0F + rand.nextFloat() * 6F, 0.0F, 0.0F, -1, -1, 0.5D);
    }

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
        //flag maps to flag2
        boolean flag = false;
        if (intVal3 == -1) {
            intVal3 = intVal4 / 2;
            flag = true;
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
            if (!flag && intVal3 == j && floatVal1 > 1.0F) {
                releaseEntitySkin(chunkX, chunkZ, primer, doubleVal1, doubleVal2, doubleVal3, random.nextFloat() * 0.5F + 0.5F, floatVal2 - 1.570796F, floatVal3 / 3F, intVal3, intVal4, 1.0D);
                releaseEntitySkin(chunkX, chunkZ, primer, doubleVal1, doubleVal2, doubleVal3, random.nextFloat() * 0.5F + 0.5F, floatVal2 + 1.570796F, floatVal3 / 3F, intVal3, intVal4, 1.0D);
                return;
            }
            if (!flag && random.nextInt(4) == 0) {
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
            int d8 = MathHelper.floor(doubleVal1 - d6) - chunkX * 16 - 1;
            //maps to k
            int k1 = (MathHelper.floor(doubleVal1 + d6) - chunkX * 16) + 1;
            //maps to l2
            int d9 = MathHelper.floor(doubleVal2 - d7) - 1;
            //maps to l
            int yVal = MathHelper.floor(doubleVal2 + d7) + 1;
            int d10 = MathHelper.floor(doubleVal3 - d6) - chunkZ * 16 - 1;
            int i2 = (MathHelper.floor(doubleVal3 + d6) - chunkZ * 16) + 1;
            if (d8 < 0) {
                d8 = 0;
            }
            if (k1 > 16) {
                k1 = 16;
            }
            if (d9 < 1) {
                d9 = 1;
            }
            if (yVal > 120) {
                yVal = 120;
            }
            if (d10 < 0) {
                d10 = 0;
            }
            if (i2 > 16) {
                i2 = 16;
            }

            //maps to flag3
            boolean flag2 = false;
            //j2 maps to j1
            for (int xVal = d8; !flag2 && xVal < k1; xVal++) {
                for (int zVal = d10; !flag2 && zVal < i2; zVal++) {
                    for (int i3 = yVal + 1; !flag2 && i3 >= d9 - 1; i3--) {
                        int j3 = (xVal * 16 + zVal) * 128 + i3;
                        if (i3 < 0 || i3 >= 128) {
                            continue;
                        }
                        if (isOceanBlock(primer, xVal, yVal, zVal, chunkX, chunkZ)) {
                            flag2 = true;
                        }
                        if (i3 != d9 - 1 && xVal != d8 && xVal != k1 - 1 && zVal != d10 && zVal != i2 - 1) {
                            i3 = d9;
                        }
                    }

                }

            }

            if (flag2) {
                continue;
            }

            //BEGIN CONFUSION
            /*
            for (int k2 = d8; k2 < k1; k2++) {
                double d12 = (((double) (k2 + chunkX * 16) + 0.5D) - doubleVal1) / d6;
                label0:
                for (int k3 = d10; k3 < i2; k3++) {
                    double d13 = (((double) (k3 + chunkZ * 16) + 0.5D) - doubleVal3) / d6;
                    boolean flag3 = false;
                    if (d12 * d12 + d13 * d13 >= 1.0D) {
                        continue;
                    }
                    int i4 = yVal - 1;
                    do {
                        if (i4 < d9) {
                            continue label0;
                        }
                        double d14 = (((double) i4 + 0.5D) - doubleVal2) / d7;
                        if (d14 > -0.69999999999999996D && d12 * d12 + d14 * d14 + d13 * d13 < 1.0D) {
                            //STRONG WIP
                            //x ?= k
                            //y ?= i4 (probably, looks like it is...)
                            //z ?= k3

                            IBlockState iblockstate1 = primer.getBlockState(k2, j2, k3);
                            IBlockState iblockstate2 = (IBlockState) Objects.firstNonNull(primer.getBlockState(k2, j2 + 1, k3), Blocks.AIR);

                            if (iblockstate2.getBlock() == Blocks.GRASS) {
                                flag3 = true;
                            }
                            if (iblockstate1.getBlock() == Blocks.STONE || byte0 == Blocks.DIRT || byte0 == Blocks.GRASS) {
                                if (i4 < 10) {
                                    primer.setBlockState(intVal3, i4, k3, Blocks.FLOWING_LAVA.getDefaultState());
                                } else {
                                    primer.setBlockState(intVal3, i4, k3, Blocks.AIR.getDefaultState());
                                    if (flag3 && primer.getBlockState(intVal3, i4 - 1, k3).getBlock() == Blocks.DIRT) {
                                        primer.setBlockState(intVal3, i4 - 1, k3, Blocks.GRASS.getDefaultState());
                                    }
                                }
                            }
                        }
                        i4--;
                    } while (true);
                }

            }

            if (flag) {
                break;
            }
            */
            //END OF LOOP
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
                releaseEntitySkin(i, j, chunkPrimerIn, d, d1, d2, f2, f, f1, 0, 0, 1.0D);
            }

        }

    }

    //VANILLA
    protected boolean isOceanBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ) {
        net.minecraft.block.Block block = data.getBlockState(x, y, z).getBlock();
        return block == Blocks.FLOWING_WATER || block == Blocks.WATER;
    }
}
