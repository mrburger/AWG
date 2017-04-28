package mrburgerus.awg.world.deco;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

// Generates Tall Grass (WIP)

public class GenGrassAWG extends WorldGenerator
{

    private Block block;

    public GenGrassAWG(Block i, int j)
    {
        block = i;
    }

    @Override
    public boolean generate(World worldIn, Random random, BlockPos position)
    {
        int i = position.getX();
        int j = position.getY();
        int k = position.getZ();
        Block b;
        for(; ((b = worldIn.getBlockState(new BlockPos(i, j, k)).getBlock()) == Blocks.AIR || b == Blocks.LEAVES) && j > 0; j--) { }
        for(int i1 = 0; i1 < 128; i1++)
        {
            int j1 = (i + random.nextInt(8)) - random.nextInt(8);
            int k1 = (j + random.nextInt(4)) - random.nextInt(4);
            int l1 = (k + random.nextInt(8)) - random.nextInt(8);
            if(worldIn.isAirBlock(new BlockPos(j1, k1, l1)) && Blocks.TALLGRASS.canBlockStay(worldIn, new BlockPos(j1, k1, l1), block.getDefaultState()))
            {
                //set to grass
                worldIn.setBlockState(new BlockPos(j1, k1, l1), Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS));
            }
        }

        return true;
    }
}

