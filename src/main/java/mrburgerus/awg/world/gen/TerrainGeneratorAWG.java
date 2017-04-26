package mrburgerus.awg.world.gen;

import mrburgerus.awg.world.gen.noisegenerator.NoiseGeneratorOctaves3DAWG;
import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class TerrainGeneratorAWG
{
    // I DONT KNOW WHAT IM DOING . JPG
    // BASED UPON McJty INFINITE WISDOM

    //Variables
    private final double[]  heightMap;
    private int seaLevel;
    private World world;
    private Random random;
    private double temp;
    private double rain;

    private NoiseGeneratorOctaves3DAWG noiseGen1;
    private NoiseGeneratorOctaves3DAWG noiseGen2;
    private NoiseGeneratorOctaves3DAWG noiseGen3;
    private NoiseGeneratorOctaves3DAWG noiseGen4;
    private NoiseGeneratorOctaves3DAWG noiseGen5;
    private NoiseGeneratorOctaves3DAWG noiseGen6;
    private NoiseGeneratorOctaves3DAWG noiseGen7;
    private NoiseGeneratorOctaves3DAWG treeNoise;

    private double noise[];
    private double noise1[];
    private double noise2[];
    private double noise3[];
    private double noise6[];
    private double noise7[];

    private double sandNoise[] = new double[256];
    private double gravelNoise[] = new double[256];
    private double stoneNoise[] = new double[256];

    //Constructors
    public TerrainGeneratorAWG()
    {
        this.heightMap = new double[825];
        seaLevel = 64;
        temp = Biomes.PLAINS.getTemperature();
        rain = Biomes.PLAINS.getRainfall();
    }

    //Methods
    public void setup(World worldIn, Random randomIn)
    {
        //setup the class variables
        this.world = worldIn;
        this.random = randomIn;

        //init noise gens (some of this is copied, ffs I cannot figure it ALL out)
        noiseGen1 = new NoiseGeneratorOctaves3DAWG(random, 16);
        noiseGen2 = new NoiseGeneratorOctaves3DAWG(random, 16);
        noiseGen3 = new NoiseGeneratorOctaves3DAWG(random, 8);
        noiseGen4 = new NoiseGeneratorOctaves3DAWG(random, 4);
        noiseGen5 = new NoiseGeneratorOctaves3DAWG(random, 4);
        noiseGen6 = new NoiseGeneratorOctaves3DAWG(random, 10);
        noiseGen7 = new NoiseGeneratorOctaves3DAWG(random, 16);
        treeNoise = new NoiseGeneratorOctaves3DAWG(random, 8);
    }

    //generate actual noise
    public void generate(int x, int z, ChunkPrimer primer)
    {
        byte byte0 = 4;
        int k = byte0 + 1;
        byte b2 = 17;
        int l = byte0 + 1;
        noise = initNoiseField(noise, x * byte0, 0, z * byte0, k, b2, l);

        // More Stuff
        for(int xPiece = 0; xPiece < byte0; xPiece++) {
            for(int zPiece = 0; zPiece < byte0; zPiece++) {
                for(int yPiece = 0; yPiece < 16; yPiece++) {
                    double d = 0.125D;
                    double d1 = noise[((xPiece + 0) * l + (zPiece + 0)) * b2 + (yPiece + 0)];
                    double d2 = noise[((xPiece + 0) * l + (zPiece + 1)) * b2 + (yPiece + 0)];
                    double d3 = noise[((xPiece + 1) * l + (zPiece + 0)) * b2 + (yPiece + 0)];
                    double d4 = noise[((xPiece + 1) * l + (zPiece + 1)) * b2 + (yPiece + 0)];
                    double d5 = (noise[((xPiece + 0) * l + (zPiece + 0)) * b2 + (yPiece + 1)] - d1) * d;
                    double d6 = (noise[((xPiece + 0) * l + (zPiece + 1)) * b2 + (yPiece + 1)] - d2) * d;
                    double d7 = (noise[((xPiece + 1) * l + (zPiece + 0)) * b2 + (yPiece + 1)] - d3) * d;
                    double d8 = (noise[((xPiece + 1) * l + (zPiece + 1)) * b2 + (yPiece + 1)] - d4) * d;
                    for(int l1 = 0; l1 < 8; l1++) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for(int i2 = 0; i2 < 4; i2++) {
                            int xLoc = i2 + xPiece * 4;
                            int yLoc = yPiece * 8 + l1;
                            int zLoc = 0 + zPiece * 4;
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for(int k2 = 0; k2 < 4; k2++) {
                                Block block = Blocks.AIR;
                                if(yPiece * 8 + l1 < seaLevel)
                                {
                                        block = Blocks.WATER;
                                }
                                if(d15 > 0.0D) {
                                    block = Blocks.STONE;
                                }
                                primer.setBlockState(xLoc, yLoc, zLoc, block.getDefaultState());
                                zLoc++;
                                d15 += d16;
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

    // makes a noise field
    private double[] initNoiseField(double array[], int xPos, int yPos, int zPos, int xSize, int ySize, int zSize)
    {
        if(array == null)
        {
            array = new double[xSize * ySize * zSize];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        noise6 = noiseGen6.generateNoiseArray(noise6, xPos, yPos, zPos, xSize, 1, zSize, 1.0D, 0.0D, 1.0D);
        noise7 = noiseGen7.generateNoiseArray(noise7, xPos, yPos, zPos, xSize, 1, zSize, 100D, 0.0D, 100D);
        noise3 = noiseGen3.generateNoiseArray(noise3, xPos, yPos, zPos, xSize, ySize, zSize, d / 80D, d1 / 160D, d / 80D);
        noise1 = noiseGen1.generateNoiseArray(noise1, xPos, yPos, zPos, xSize, ySize, zSize, d, d1, d);
        noise2 = noiseGen2.generateNoiseArray(noise2, xPos, yPos, zPos, xSize, ySize, zSize, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        for(int i2 = 0; i2 < xSize; i2++)
        {
            for(int j2 = 0; j2 < zSize; j2++)
            {
                double d2 = (noise6[l1] + 256D) / 512D;
                if(d2 > 1.0D)
                {
                    d2 = 1.0D;
                }
                double d3 = 0.0D;
                double d4 = noise7[l1] / 8000D;
                if(d4 < 0.0D)
                {
                    d4 = -d4;
                }
                d4 = d4 * 3D - 3D;
                if(d4 < 0.0D)
                {
                    d4 /= 2D;
                    if(d4 < -1D)
                    {
                        d4 = -1D;
                    }
                    d4 /= 1.3999999999999999D;
                    d4 /= 2D;
                    d2 = 0.0D;
                } else
                {
                    if(d4 > 1.0D)
                    {
                        d4 = 1.0D;
                    }
                    d4 /= 6D;
                }
                d2 += 0.5D;
                d4 = (d4 * (double)ySize) / 16D;
                double d5 = (double)ySize / 2D + d4 * 4D;
                l1++;
                for(int k2 = 0; k2 < ySize; k2++)
                {
                    double d6 = 0.0D;
                    double d7 = (((double)k2 - d5) * 12D) / d2;
                    if(d7 < 0.0D)
                    {
                        d7 *= 4D;
                    }
                    double d8 = noise1[k1] / 512D;
                    double d9 = noise2[k1] / 512D;
                    double d10 = (noise3[k1] / 10D + 1.0D) / 2D;
                    if(d10 < 0.0D)
                    {
                        d6 = d8;
                    } else
                    if(d10 > 1.0D)
                    {
                        d6 = d9;
                    } else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }
                    d6 -= d7;
                    if(k2 > ySize - 4)
                    {
                        double d11 = (float)(k2 - (ySize - 4)) / 3F;
                        d6 = d6 * (1.0D - d11) + -10D * d11;
                    }
                    if((double)k2 < d3)
                    {
                        double d12 = (d3 - (double)k2) / 4D;
                        if(d12 < 0.0D)
                        {
                            d12 = 0.0D;
                        }
                        if(d12 > 1.0D)
                        {
                            d12 = 1.0D;
                        }
                        d6 = d6 * (1.0D - d12) + -10D * d12;
                    }
                    array[k1] = d6;
                    k1++;
                }

            }

        }

        return array;
    }

    // Replace Biome Blocks, wow!
    public void replaceBiomeBlocks(int x, int z, ChunkPrimer chunkPrimer, Biome[] biomesIn)
    {
        double d = 0.03125D;
        sandNoise = noiseGen4.generateNoiseArray(sandNoise, x * 16, z * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
        gravelNoise = noiseGen4.generateNoiseArray(gravelNoise, x * 16, 109.0134D, z * 16, 16, 1, 16, d, 1.0D, d);
        stoneNoise = noiseGen5.generateNoiseArray(stoneNoise, x * 16, z * 16, 0.0D, 16, 16, 1, d * 2D, d * 2D, d * 2D);

        // Prewritten, updated to 1.11
        for(int i1 = 0; i1 < 16; i1++) {
            for(int i2 = 0; i2 < 16; i2++) {
                Biome biome = biomesIn[i1 + i2 * 16];
                boolean sand = sandNoise[i1 + i2 * 16] + random.nextDouble() * 0.2D > 0.0D;
                boolean gravel = gravelNoise[i1 + i2 * 16] + random.nextDouble() * 0.2D > 3D;
                int depth = (int)(stoneNoise[i1 + i2 * 16] / 3D + 3D + random.nextDouble() * 0.25D);
                int prevDepth = -1;
                Block topBlock = Blocks.GRASS;
                Block fillerBlock = Blocks.DIRT;
                for(int y = 127; y >= 0; y--) {
                    if(y <= 0 + random.nextInt(5)) {
                        chunkPrimer.setBlockState(i1, y, i2, Blocks.BEDROCK.getDefaultState());
                        continue;
                    }
                    Block block = chunkPrimer.getBlockState(i1, y, i2).getBlock();
                    if(block == Blocks.AIR) {
                        prevDepth = -1;
                        continue;
                    }
                    if(block != Blocks.STONE) {
                        continue;
                    }
                    if(prevDepth == -1) {
                        if(depth <= 0) {
                            topBlock = Blocks.AIR;
                            fillerBlock = Blocks.STONE;
                        } else if(y >= seaLevel - 4 && y <= seaLevel + 1)
                        {
                            if(gravel) {
                                topBlock = Blocks.AIR;
                                fillerBlock = Blocks.GRAVEL;
                            }
                            if(sand) {
                                topBlock = Blocks.SAND;
                                fillerBlock = Blocks.SAND;
                            }
                        }
                        if(y < seaLevel && topBlock == Blocks.AIR) {
                            topBlock = Blocks.WATER;
                        }
                        prevDepth = depth;
                        if(y >= seaLevel - 1) {
                            chunkPrimer.setBlockState(i1, y, i2, topBlock.getDefaultState());
                        } else {
                            chunkPrimer.setBlockState(i1, y, i2, fillerBlock.getDefaultState());
                        }
                        continue;
                    }
                    if(prevDepth <= 0) {
                        continue;
                    }
                    prevDepth--;
                    chunkPrimer.setBlockState(i1, y, i2, fillerBlock.getDefaultState());
                    if(prevDepth == 0 && fillerBlock == Blocks.SAND) {
                        prevDepth = random.nextInt(4);
                        fillerBlock = Blocks.SANDSTONE;
                    }
                }

            }

        }
    }
}

