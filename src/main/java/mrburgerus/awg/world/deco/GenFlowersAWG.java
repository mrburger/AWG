package mrburgerus.awg.world.deco;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

// This class generates flowers. Neat!

public class GenFlowersAWG extends WorldGenerator
{
    private Block plantBlockId;

    public GenFlowersAWG(Block b)
    {
        plantBlockId = b;
    }

    @Override
    public boolean generate(World worldIn, Random random, BlockPos position)
    {
        int i = position.getX();
        int j = position.getY();
        int k = position.getZ();
        for(int l = 0; l < 64; l++)
        {
            int i1 = (i + random.nextInt(8)) - random.nextInt(8);
            int j1 = (j + random.nextInt(4)) - random.nextInt(4);
            int k1 = (k + random.nextInt(8)) - random.nextInt(8);

            if(plantBlockId == Blocks.RED_MUSHROOM || plantBlockId == Blocks.BROWN_MUSHROOM)
            {
                if(worldIn.isAirBlock(new BlockPos(i1, j1, k1)) && canMushroomStay(worldIn, i1, j1, k1))
                {
                    worldIn.setBlockState(new BlockPos(i1, j1, k1), plantBlockId.getDefaultState());
                }
            }
            else
            {
                if(worldIn.isAirBlock(new BlockPos(i1, j1, k1)) && Blocks.RED_FLOWER.canBlockStay(worldIn, new BlockPos(i1, j1, k1), plantBlockId.getDefaultState()))
                {
                    worldIn.setBlockState(new BlockPos(i1, j1, k1), plantBlockId.getDefaultState());
                }
            }
        }

        return true;
    }

    public boolean canMushroomStay(World world, int i, int j, int k)
    {
        if(j < 0 || j >= 128)
        {
            return false;
        }
        else
        {
            return world.getLightBrightness(new BlockPos(i, j, k)) < 13 && world.getBlockState(new BlockPos(i, j - 1, k)).isOpaqueCube();
        }
    }
}
