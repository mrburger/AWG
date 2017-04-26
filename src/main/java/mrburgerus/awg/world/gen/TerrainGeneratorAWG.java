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
    private double[] initNoiseField(double array[], int xPos, int yPos, int zPos, int xSize, int ySize, int zSize) {

        // init array
        if(array == null)
        {
            array = new double[xSize * ySize * zSize];
        }
        // values I copied
        double d0 = 684.412D;
        double d1 = 684.412D;

        noise6 = noiseGen6.generateNoiseArray(noise6, xPos, yPos, zPos,xSize, ySize, zSize, 1.0D, 0.0D, 1.0D);
        noise7 = noiseGen7.generateNoiseArray(noise7, xPos, yPos, zPos,xSize, ySize, zSize,  100D, 0.0D, 100D);
        noise3 = noiseGen3.generateNoiseArray(noise3, xPos, yPos, zPos, xSize, ySize, zSize,
                d0 / 80D, d1 / 160D, d0 / 80D);
        noise1 = noiseGen1.generateNoiseArray(noise1, xPos, yPos, zPos, xSize, ySize, zSize,
                d0, d1, d0);
        noise2 = noiseGen2.generateNoiseArray(noise2, xPos, yPos, zPos, xSize, ySize, zSize,
                d0, d1, d0);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / xSize;
        for(int x = 0; x < xSize; x++) {
            int k2 = x * i2 + i2 / 2;
            for(int z = 0; z < zSize; z++) {
                int i3 = z * i2 + i2 / 2;
                double d2 = temp;
                double d3 = rain * d2;
                double d4 = 1.0D - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0D - d4;
                double d5 = (noise6[l1] + 256D) / 512D;
                d5 *= d4;
                if(d5 > 1.0D) {
                    d5 = 1.0D;
                }
                double d6 = noise7[l1] / 8000D;
                if(d6 < 0.0D) {
                    d6 = -d6 * 0.3D;
                }
                d6 = d6 * 3D - 2D;
                if(d6 < 0.0D) {
                    d6 /= 2D;
                    if(d6 < -1D) {
                        d6 = -1D;
                    }
                    d6 /= 1.4D;
                    d6 /= 2D;
                    d5 = 0.0D;
                } else {
                    if(d6 > 1.0D) {
                        d6 = 1.0D;
                    }
                    d6 /= 8D;
                }
                if(d5 < 0.0D) {
                    d5 = 0.0D;
                }
                d5 += 0.5D;
                d6 = (d6 * (double)ySize) / 16D;
                double d7 = (double)ySize / 2D + d6 * 4D;
                l1++;
                for(int y = 0; y < ySize; y++) {
                    double d8 = 0.0D;
                    double d9 = (((double)y - d7) * 12D)
                            / d5;
                    if(d9 < 0.0D) {
                        d9 *= 4D;
                    }
                    double d10 = noise1[k1] / 512D;
                    double d11 = noise2[k1] / 512D;
                    double d12 = (this.noise3[k1] / 10D + 1.0D) / 2D;
                    if(d12 < 0.0D) {
                        d8 = d10;
                    } else if(d12 > 1.0D) {
                        d8 = d11;
                    } else {
                        d8 = d10 + (d11 - d10) * d12;
                    }
                    d8 -= d9;
                    if(y > ySize - 4) {
                        double d13 = (double)((float)(y - (ySize - 4)) / 3F);
                        d8 = d8 * (1.0D - d13) + -10D * d13;
                    }
                    array[k1] = d8;
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

