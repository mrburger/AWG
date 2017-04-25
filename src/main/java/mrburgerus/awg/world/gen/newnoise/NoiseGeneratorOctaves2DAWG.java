package mrburgerus.awg.world.gen.newnoise;

import java.util.Random;

/**
 * Created by mrburgerUS on 4/25/2017.
 */
public class NoiseGeneratorOctaves2DAWG
{
    //BEGIN PREWRITTEN CODE
    private final NoiseGenerator2DAWG noiseGenerators[];
    private final int octaves;

    public NoiseGeneratorOctaves2DAWG(Random rand, int i) {
        octaves = i;
        noiseGenerators = new NoiseGenerator2DAWG[i];
        for(int j = 0; j < i; j++) {
            noiseGenerators[j] = new NoiseGenerator2DAWG(rand);
        }

    }

    public double[] generateNoiseArray(double array[], double x, double z, int xSize, int zSize,
                                       double gridX, double gridZ, double fq) {
        return generateNoiseArray(array, x, z, xSize, zSize, gridX, gridZ, fq, 0.5D);
    }

    public double[] generateNoiseArray(double array[], double xPos, double zPos, int xSize, int zSize,
                                       double gridX, double gridZ, double fq, double persistance) {
        gridX /= 1.5D;
        gridZ /= 1.5D;
        if(array == null || array.length < xSize * zSize) {
            array = new double[xSize * zSize];
        } else {
            for(int k = 0; k < array.length; k++) {
                array[k] = 0.0D;
            }

        }
        double amplitude = 1.0D;
        double frequency = 1.0D;
        for(int l = 0; l < octaves; l++) {
            noiseGenerators[l].generateNoiseArray(array, xPos, zPos, xSize, zSize, gridX * frequency, gridZ * frequency, 0.55D / amplitude);
            frequency *= fq;
            amplitude *= persistance;
        }

        return array;
    }
}
