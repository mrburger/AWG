package mrburgerus.awg.world.gen.noisegenerator;

import java.util.Random;

// Port of ted80, updated & everything!
// Yay, lets see if it works (it does)

public class NoiseGenerator3DPerlinAWG
{
    public double randomDX;
    public double randomDY;
    public double randomDZ;

    private int permutations[];

    public NoiseGenerator3DPerlinAWG(Random random) {
        permutations = new int[512];
        randomDX = random.nextDouble() * 256D;
        randomDY = random.nextDouble() * 256D;
        randomDZ = random.nextDouble() * 256D;
        for(int i = 0; i < 256; i++) {
            permutations[i] = i;
        }
        //array of random values
        for(int j = 0; j < 256; j++) {
            int k = random.nextInt(256 - j) + j;
            int l = permutations[j];
            permutations[j] = permutations[k];
            permutations[k] = l;
            permutations[j + 256] = permutations[j];
        }
    }

    @SuppressWarnings("cast")
    public double generateNoise(double xPos, double yPos, double zPos) {
        double x = xPos + randomDX;
        double y = yPos + randomDY;
        double z = zPos + randomDZ;
        int intX = (int)x;
        int intY = (int)y;
        int intZ = (int)z;
        if(x < (double)intX) {
            intX--;
        }
        if(y < (double)intY) {
            intY--;
        }
        if(z < (double)intZ) {
            intZ--;
        }
        int p1 = intX & 0xff;
        int p2 = intY & 0xff;
        int p3 = intZ & 0xff;
        x -= intX;
        y -= intY;
        z -= intZ;
        double fx = x * x * x * (x * (x * 6D - 15D) + 10D);
        double fy = y * y * y * (y * (y * 6D - 15D) + 10D);
        double fz = z * z * z * (z * (z * 6D - 15D) + 10D);
        int a1 = permutations[p1] + p2;
        int a2 = permutations[a1] + p3;
        int a3 = permutations[a1 + 1] + p3;
        int a4 = permutations[p1 + 1] + p2;
        int a5 = permutations[a4] + p3;
        int a6 = permutations[a4 + 1] + p3;
        return lerp(fz, lerp(fy,
                        lerp(fx, grad3d(permutations[a2], x, y, z),
                                grad3d(permutations[a5], x - 1.0D, y, z)),
                        lerp(fx, grad3d(permutations[a3], x, y - 1.0D, z),
                                grad3d(permutations[a6], x - 1.0D, y - 1.0D, z))),
                lerp(fy,
                        lerp(fx, grad3d(permutations[a2 + 1], x, y, z - 1.0D),
                                grad3d(permutations[a5 + 1], x - 1.0D, y, z - 1.0D)),
                        lerp(fx, grad3d(permutations[a3 + 1], x, y - 1.0D, z - 1.0D),
                                grad3d(permutations[a6 + 1], x - 1.0D, y - 1.0D, z - 1.0D))));
    }

    // maps to func_804_b
    public final double lerp(double d, double d1, double d2) {
        return d1 + d * (d2 - d1);
    }

    // maps to func_4110_a
    public final double grad2d(int i, double x, double z) {
        int j = i & 0xf;
        double d2 = (double)(1 - ((j & 8) >> 3)) * x;
        double d3 = j >= 4 ? j != 12 && j != 14 ? z : x : 0.0D;
        return ((j & 1) != 0 ? -d2 : d2) + ((j & 2) != 0 ? -d3 : d3);
    }

    //maps to grad as well as func_803_a
    public final double grad3d(int i, double x, double y, double z) {
        int j = i & 0xf;
        double d3 = j >= 8 ? y : x;
        double d4 = j >= 4 ? j != 12 && j != 14 ? z : x : y;
        return ((j & 1) != 0 ? -d3 : d3) + ((j & 2) != 0 ? -d4 : d4);
    }

    // maps to func_801_a
    public double generateNoise(double d, double d1) {
        return generateNoise(d, d1, 0.0D);
    }

    // maps to func_805_a
    public void generateNoiseArray(double ad[], double d, double d1, double d2,
                           int i, int j, int k, double d3, double d4,
                           double d5, double d6)
    {
        int l = 0;
        double d7 = 1.0D / d6;
        int i1 = -1;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = false;
        double d8 = 0.0D;
        double d9 = 0.0D;
        double d10 = 0.0D;
        double d11 = 0.0D;
        for(int l2 = 0; l2 < i; l2++)
        {
            double d12 = (d + (double)l2) * d3 + randomDX;
            int i3 = (int)d12;
            if(d12 < (double)i3)
            {
                i3--;
            }
            int j3 = i3 & 0xff;
            d12 -= i3;
            double d13 = d12 * d12 * d12 * (d12 * (d12 * 6D - 15D) + 10D);
            for(int k3 = 0; k3 < k; k3++)
            {
                double d14 = (d2 + (double)k3) * d5 + randomDZ;
                int l3 = (int)d14;
                if(d14 < (double)l3)
                {
                    l3--;
                }
                int i4 = l3 & 0xff;
                d14 -= l3;
                double d15 = d14 * d14 * d14 * (d14 * (d14 * 6D - 15D) + 10D);
                for(int j4 = 0; j4 < j; j4++)
                {
                    double d16 = (d1 + (double)j4) * d4 + randomDY;
                    int k4 = (int)d16;
                    if(d16 < (double)k4)
                    {
                        k4--;
                    }
                    int l4 = k4 & 0xff;
                    d16 -= k4;
                    double d17 = d16 * d16 * d16 * (d16 * (d16 * 6D - 15D) + 10D);
                    if(j4 == 0 || l4 != i1)
                    {
                        i1 = l4;
                        int j1 = permutations[j3] + l4;
                        int k1 = permutations[j1] + i4;
                        int l1 = permutations[j1 + 1] + i4;
                        int i2 = permutations[j3 + 1] + l4;
                        int j2 = permutations[i2] + i4;
                        int k2 = permutations[i2 + 1] + i4;
                        d8 = lerp(d13, grad3d(permutations[k1], d12, d16, d14), grad3d(permutations[j2], d12 - 1.0D, d16, d14));
                        d9 = lerp(d13, grad3d(permutations[l1], d12, d16 - 1.0D, d14), grad3d(permutations[k2], d12 - 1.0D, d16 - 1.0D, d14));
                        d10 = lerp(d13, grad3d(permutations[k1 + 1], d12, d16, d14 - 1.0D), grad3d(permutations[j2 + 1], d12 - 1.0D, d16, d14 - 1.0D));
                        d11 = lerp(d13, grad3d(permutations[l1 + 1], d12, d16 - 1.0D, d14 - 1.0D), grad3d(permutations[k2 + 1], d12 - 1.0D, d16 - 1.0D, d14 - 1.0D));
                    }
                    double d18 = lerp(d17, d8, d9);
                    double d19 = lerp(d17, d10, d11);
                    double d20 = lerp(d15, d18, d19);
                    ad[l++] += d20 * d7;
                }

            }

        }

    }

}
