package mrburgerus.awg.world.deco;

import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

// This class generates trees like alpha

public class GenTreeAWG extends WorldGenAbstractTree {

    public GenTreeAWG(boolean notify) {
        super(notify);
    }

    public boolean generate(World world, Random random, BlockPos pos)
    {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        int l = random.nextInt(3) + 4;
        boolean flag = true;
        if(j < 1 || j + l + 1 > 128)
        {
            return false;
        }
        for(int i1 = j; i1 <= j + 1 + l; i1++)
        {
            byte byte0 = 1;
            if(i1 == j)
            {
                byte0 = 0;
            }
            if(i1 >= (j + 1 + l) - 2)
            {
                byte0 = 2;
            }
            for(int i2 = i - byte0; i2 <= i + byte0 && flag; i2++)
            {
                for(int l2 = k - byte0; l2 <= k + byte0 && flag; l2++)
                {
                    if(i1 >= 0 && i1 < 128)
                    {
                        Block j3 = world.getBlockState(new BlockPos(i2, i1, l2)).getBlock();
                        if(j3 != Blocks.AIR && j3 != Blocks.LEAVES && j3 != Blocks.LEAVES2)
                        {
                            flag = false;
                        }
                    } else
                    {
                        flag = false;
                    }
                }

            }

        }

        if(!flag)
        {
            return false;
        }
        Block j1 = world.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        if(j1 != Blocks.GRASS && j1 != Blocks.DIRT || j >= 128 - l - 1)
        {
            return false;
        }
        world.setBlockState(new BlockPos(i, j - 1, k), Blocks.DIRT.getDefaultState());


        if (random.nextInt(10) == 0)
            generateBirch(world, i, j, k, l, random);
        else
            generateOak(world, i, j, k, l, random);

        return true;
    }

    private void generateOak(World world, int i, int j, int k, int l, Random random)
    {
        for(int k1 = (j - 3) + l; k1 <= j + l; k1++)
        {
            int j2 = k1 - (j + l);
            int i3 = 1 - j2 / 2;
            for(int k3 = i - i3; k3 <= i + i3; k3++)
            {
                int l3 = k3 - i;
                for(int i4 = k - i3; i4 <= k + i3; i4++)
                {
                    int j4 = i4 - k;
                    if((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !world.getBlockState(new BlockPos(k3, k1, i4)).isOpaqueCube())
                    {
                        world.setBlockState(new BlockPos(k3, k1, i4), Blocks.LEAVES.getDefaultState());
                    }
                }

            }

        }

        for(int l1 = 0; l1 < l; l1++)
        {
            Block k2 = world.getBlockState(new BlockPos(i, j + l1, k)).getBlock();
            if(k2 == Blocks.AIR || k2 == Blocks.LEAVES || k2 == Blocks.LEAVES2)
            {
                    world.setBlockState(new BlockPos(i, j + l1, k), Blocks.LOG.getDefaultState());
            }
        }
    }

    private void generateBirch(World world, int i, int j, int k, int l, Random random)
    {
        for(int k1 = (j - 3) + l; k1 <= j + l; k1++)
        {
            int j2 = k1 - (j + l);
            int i3 = 1 - j2 / 2;
            for(int k3 = i - i3; k3 <= i + i3; k3++)
            {
                int l3 = k3 - i;
                for(int i4 = k - i3; i4 <= k + i3; i4++)
                {
                    int j4 = i4 - k;
                    if((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !world.getBlockState(new BlockPos(k3, k1, i4)).isOpaqueCube())
                    {
                        world.setBlockState(new BlockPos(k3, k1, i4), Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH));
                    }
                }

            }

        }

        for(int l1 = 0; l1 < l; l1++)
        {
            Block k2 = world.getBlockState(new BlockPos(i, j + l1, k)).getBlock();
            if(k2 == Blocks.AIR || k2 == Blocks.LEAVES || k2 == Blocks.LEAVES2)
            {
                world.setBlockState(new BlockPos(i, j + l1, k), Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH));
            }
        }
    }
}
