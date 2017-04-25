package mrburgerus.awg.world.gen;

import mrburgerus.awg.world.gen.noise.NoiseOctavesAWG;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

/**
 * Created by mrburgerUS on 4/24/2017.
 */
public class TerrainGeneratorAWG {

    private World world;
    private Random random;
    private int seaLevel;

    private final double[] heightMap;
    private double[] mainNoiseRegion;
    private double[] minLimitRegion;
    private double[] maxLimitRegion;
    private double[] depthRegion;

    private NoiseOctavesAWG minLimitPerlinNoise;
    private NoiseOctavesAWG maxLimitPerlinNoise;
    private NoiseOctavesAWG mainPerlinNoise;
    private NoiseGeneratorPerlin surfaceNoise;

    // A NoiseGeneratorOctaves used in generating terrain
    private NoiseGeneratorOctaves depthNoise;

    private final float[] biomeWeights;
    private double[] depthBuffer = new double[256];

    private Biome[] biomesForGeneration;

    public TerrainGeneratorAWG() {
        this.heightMap = new double[825];

        this.biomeWeights = new float[25];
        for (int j = -2; j <= 2; ++j) {
            for (int k = -2; k <= 2; ++k) {
                float f = 10.0F / MathHelper.sqrt((j * j + k * k) + 0.2F);
                this.biomeWeights[j + 2 + (k + 2) * 5] = f;
            }
        }
        seaLevel = 64;
    }

    public void setup(World world, Random rand) {
        this.world = world;
        this.random = rand;

        this.minLimitPerlinNoise = new NoiseOctavesAWG(rand, 16);
        this.maxLimitPerlinNoise = new NoiseOctavesAWG(rand, 16);
        this.mainPerlinNoise = new NoiseOctavesAWG(rand, 8);
        this.surfaceNoise = new NoiseGeneratorPerlin(rand, 4);
        NoiseGeneratorOctaves noiseGen5 = new NoiseOctavesAWG(rand, 10);
        this.depthNoise = new NoiseOctavesAWG(rand, 16);
        NoiseGeneratorOctaves mobSpawnerNoise = new NoiseOctavesAWG(rand, 8);

        net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld ctx =
                new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld(minLimitPerlinNoise,
                        maxLimitPerlinNoise, mainPerlinNoise, surfaceNoise, noiseGen5, depthNoise, mobSpawnerNoise);
        ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(world, rand, ctx);
        this.minLimitPerlinNoise = (NoiseOctavesAWG) ctx.getLPerlin1();
        this.maxLimitPerlinNoise = (NoiseOctavesAWG) ctx.getLPerlin2();
        this.mainPerlinNoise = (NoiseOctavesAWG) ctx.getPerlin();
        this.surfaceNoise = ctx.getHeight();
        this.depthNoise = ctx.getDepth();
    }

    private void generateHeightmap(int chunkX4, int chunkY4, int chunkZ4) {
        this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, chunkX4, chunkZ4, 5, 5, 200.0D, 200.0D, 0.5D);
        this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, chunkX4, chunkY4, chunkZ4, 5, 33, 5, 8.55515D, 4.277575D, 8.55515D);
        this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, chunkX4, chunkY4, chunkZ4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, chunkX4, chunkY4, chunkZ4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        int l = 0;
        int i1 = 0;

        for (int j1 = 0; j1 < 5; ++j1) {
            for (int k1 = 0; k1 < 5; ++k1) {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;


                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        Biome biome = this.biomesForGeneration[0];
                        float baseHeight = biome.getBaseHeight();
                        float variation = biome.getHeightVariation();

                        float f5 = biomeWeights[l1 + 2 + (i2 + 2) * 5] / (baseHeight + 2.0F);
                        f += variation * f5;
                        f1 += baseHeight * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d12 = this.depthRegion[i1] / 8000.0D;

                if (d12 < 0.0D) {
                    d12 = -d12 * 0.3D;
                }

                d12 = d12 * 3.0D - 2.0D;

                if (d12 < 0.0D) {
                    d12 /= 2.0D;

                    if (d12 < -1.0D) {
                        d12 = -1.0D;
                    }

                    d12 /= 1.4D;
                    d12 /= 2.0D;
                } else {
                    if (d12 > 1.0D) {
                        d12 = 1.0D;
                    }

                    d12 /= 8.0D;
                }

                ++i1;
                double d13 = f1;
                double d14 = f;
                d13 += d12 * 0.2D;
                d13 = d13 * 8.5D / 8.0D;
                double d5 = 8.5D + d13 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2) {
                    double d6 = (j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D) {
                        d6 *= 4.0D;
                    }

                    double d7 = this.minLimitRegion[l] / 512.0D;
                    double d8 = this.maxLimitRegion[l] / 512.0D;
                    double d9 = (this.mainNoiseRegion[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.clamp(d7, d8, d9) - d6;

                    if (j2 > 29) {
                        double d11 = ((j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.heightMap[l] = d10;
                    ++l;
                }
            }
        }
    }

    public void generate(int x, int z, ChunkPrimer primer) {
        this.generateHeightmap(x * 4, 0, z * 4);

        for (int i = 0; i < 4; ++i) {
            int j = i * 5;
            int k = (i + 1) * 5;

            for (int l = 0; l < 4; ++l) {
                int i1 = (j + l) * 33;
                int j1 = (j + l + 1) * 33;
                int k1 = (k + l) * 33;
                int l1 = (k + l + 1) * 33;

                for (int i2 = 0; i2 < 32; ++i2) {
                    double d0 = 0.125D;
                    double d1 = this.heightMap[i1 + i2];
                    double d2 = this.heightMap[j1 + i2];
                    double d3 = this.heightMap[k1 + i2];
                    double d4 = this.heightMap[l1 + i2];
                    double d5 = (this.heightMap[i1 + i2 + 1] - d1) * 0.125D;
                    double d6 = (this.heightMap[j1 + i2 + 1] - d2) * 0.125D;
                    double d7 = (this.heightMap[k1 + i2 + 1] - d3) * 0.125D;
                    double d8 = (this.heightMap[l1 + i2 + 1] - d4) * 0.125D;

                    for (int j2 = 0; j2 < 8; ++j2) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for (int k2 = 0; k2 < 4; ++k2) {
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * 0.25D;
                            double lvt_45_1_ = d10 - d16;

                            for (int l2 = 0; l2 < 4; ++l2) {
                                if ((lvt_45_1_ += d16) > 0.0D) {
                                    primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.STONE.getDefaultState());
                                } else if (i2 * 8 + j2 < this.seaLevel) {
                                    primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.WATER.getDefaultState());
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn)
    {
        double d0 = 0.03125D;
        this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double) (x * 16), (double) (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                Biome biome = biomesIn[j + i * 16];
                biome.genTerrainBlocks(this.world, this.random, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
            }
        }
    }
}
