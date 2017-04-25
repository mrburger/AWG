package mrburgerus.awg.world.gen;

import mrburgerus.awg.world.gen.newnoise.NoiseGeneratorOctaves3DAWG;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class NewTerrainGeneratorAWG
{
    // I DONT KNOW WHAT IM DOING . JPG
    // BASED UPON McJty INFINITE WISDOM

    //Variables
    private final double[]  heightMap;
    private int seaLevel;
    private World world;
    private Random random;

    private NoiseGeneratorOctaves3DAWG noiseGen1;
    private NoiseGeneratorOctaves3DAWG noiseGen2;
    private NoiseGeneratorOctaves3DAWG noiseGen3;
    private NoiseGeneratorOctaves3DAWG noiseGen4;
    private NoiseGeneratorOctaves3DAWG noiseGen5;
    private NoiseGeneratorOctaves3DAWG noiseGen6;
    private NoiseGeneratorOctaves3DAWG noiseGen7;
    private NoiseGeneratorOctaves3DAWG treeNoise;

    //Constructors
    public NewTerrainGeneratorAWG()
    {
        this.heightMap = new double[825];
        seaLevel = 64;
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

    public void generate(int x, int z, ChunkPrimer primer)
    {

    }
}
