package mrburgerus.awg.world.deco;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

// for cactus

public class GenCactusAWG extends WorldGenerator {
    @Override
    public boolean generate(World worldIn, Random random, BlockPos position)
    {
        int i = position.getX();
        int j = position.getY();
        int k = position.getZ();
        for(int l = 0; l < 10; l++)
        {
            int i1 = (i + random.nextInt(8)) - random.nextInt(8);
            int j1 = (j + random.nextInt(4)) - random.nextInt(4);
            int k1 = (k + random.nextInt(8)) - random.nextInt(8);
            if(!worldIn.isAirBlock(new BlockPos(i1, j1, k1)))
            {
                continue;
            }
            int l1 = 1 + random.nextInt(random.nextInt(3) + 1);
            for(int i2 = 0; i2 < l1; i2++)
            {
                if(Blocks.CACTUS.canBlockStay(worldIn,new BlockPos( i1, j1 + i2, k1)))
                {
                    worldIn.setBlockState(new BlockPos(i1, j1 + i2, k1), Blocks.CACTUS.getDefaultState());
                }
            }

        }

        return true;
    }
}
