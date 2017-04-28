package mrburgerus.awg.world.gen;

import mrburgerus.awg.world.deco.GenCactusAWG;
import mrburgerus.awg.world.deco.GenFlowersAWG;
import mrburgerus.awg.world.deco.GenGrassAWG;
import mrburgerus.awg.world.deco.GenTreeAWG;
import mrburgerus.awg.world.gen.noisegenerator.NoiseGeneratorOctaves3DAWG;
import net.minecraft.block.BlockStone;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkProviderAWG implements IChunkGenerator
{
    private final World worldObj;
    private final Random random;
    private Biome[] biomesForGeneration;
    private final boolean mapFeaturesEnabled;

    public NoiseGeneratorOctaves3DAWG treeNoise;

    private MapGenStronghold strongholdGenerator = new MapGenStronghold();
    private MapGenVillage villageGenerator = new MapGenVillage();
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
    private MapGenBase ravineGenerator = new MapGenRavine();
    private StructureOceanMonument oceanMonumentGenerator = new StructureOceanMonument();

    //Variables
    public WorldGenerator dirtGen;
    public WorldGenerator gravelGen;
    public WorldGenerator graniteGen;
    public WorldGenerator dioriteGen;
    public WorldGenerator andesiteGen;
    public WorldGenerator coalGen;
    public WorldGenerator ironGen;
    public WorldGenerator goldGen;
    public WorldGenerator redstoneGen;
    public WorldGenerator diamondGen;
    public WorldGenerator lapisGen;

    public BlockPos chunkPos;

    private MapGenBase caveGenerator = new MapGenCaves();
    private TerrainGeneratorAWG terraingen = new TerrainGeneratorAWG();

    public ChunkProviderAWG(World world, boolean mapFeaturesEnabledIn)
    {
        this.worldObj = world;
        long seed = worldObj.getSeed();
        this.random = new Random(seed);
        terraingen.setup(worldObj, random);
        world.setSeaLevel(64);
        this.mapFeaturesEnabled = mapFeaturesEnabledIn;
        treeNoise = new NoiseGeneratorOctaves3DAWG(random, 8);
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        ChunkPrimer chunkprimer = new ChunkPrimer();

        // Setup biomes for terraingen
        terraingen.generate(x, z, chunkprimer);

        // Setup biomes again for actual biome decoration
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        // This will replace stone with the biome specific stones
        terraingen.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);

        // Generate caves
        this.caveGenerator.generate(this.worldObj, x, z, chunkprimer);

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);

        byte[] biomeArray = chunk.getBiomeArray();
        for (int i = 0; i < biomeArray.length; ++i) {
            biomeArray[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        ChunkPos chunkpos = new ChunkPos(x, z);
        Biome biome = this.worldObj.getBiome(blockpos.add(16, 0, 16));

        // vanilla decorator, DISABLED
        //biome.decorate(this.worldObj, this.random, blockpos);


        WorldEntitySpawner.performWorldGenSpawning(this.worldObj, biome, i + 8, j + 8, 16, 16, this.random);

        //STRUCTURES
        if (this.mapFeaturesEnabled)
        {
                this.mineshaftGenerator.generateStructure(this.worldObj, this.random, chunkpos);
                this.villageGenerator.generateStructure(this.worldObj, this.random, chunkpos);
                this.strongholdGenerator.generateStructure(this.worldObj, this.random, chunkpos);
                this.scatteredFeatureGenerator.generateStructure(this.worldObj, this.random, chunkpos);
                this.oceanMonumentGenerator.generateStructure(this.worldObj, this.random, chunkpos);
        }

        //ALPHA FEATURES (Thanks ted80)
        for(int i10 = 0; i10 < 1; i10++)
        {
            int l14 = i + random.nextInt(16) + 8;
            int j17 = random.nextInt(128);
            int l18 = j + random.nextInt(16) + 8;
            (new GenCactusAWG()).generate(worldObj, random, new BlockPos(l14, j17, l18));
        }


        //GENERATE TREES
        double d = 0.5D;
        int l3 = (int)((treeNoise.generateNoise((double)i * d, (double)j * d) / 8D + random.nextDouble() * 4D + 4D) / 3D);
        for(int k8 = 0; k8 < l3; k8++)
        {
            int j13 = i + random.nextInt(16) + 8;
            int l15 = j + random.nextInt(16) + 8;
            WorldGenerator worldgenerator = new GenTreeAWG(true);
            worldgenerator.generate(worldObj, random, new BlockPos(j13, worldObj.getHeight(j13, l15), l15));
        }

        //GENERATE TALL GRASS
        int chancesGrass = 1;
        for(int l14 = 0; l14 < chancesGrass; l14++) //GOOD
        {
            int l19 = i + random.nextInt(16) + 8;
            int k22 = random.nextInt(128);
            int j24 = j + random.nextInt(16) + 8;
            (new GenGrassAWG(Blocks.TALLGRASS, 1)).generate(worldObj, random, new BlockPos(l19, k22, j24));
        }

        //GENERATE FLOWERS
        if(random.nextInt(2) == 0) //GOOD
        {
            int j15 = i + random.nextInt(16) + 8;
            int j17 = random.nextInt(128);
            int j20 = j + random.nextInt(16) + 8;
            (new GenFlowersAWG(Blocks.RED_FLOWER)).generate(worldObj, random, new BlockPos(j15, j17, j20));
        }

        //GENERATE WORLD MATERIALS

        this.chunkPos = blockpos;


        //finally decorate
        decorate(worldObj, random, Biomes.PLAINS, blockpos);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.worldObj.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean boolean1) {
        if (this.mapFeaturesEnabled == true)
        {
            return strongholdGenerator.getClosestStrongholdPos(worldIn, position, boolean1);
        }
        else
            return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {
        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.generate(this.worldObj, x, z, (ChunkPrimer)null);
            this.villageGenerator.generate(this.worldObj, x, z, (ChunkPrimer)null);
            this.strongholdGenerator.generate(this.worldObj, x, z, (ChunkPrimer)null);
            this.scatteredFeatureGenerator.generate(this.worldObj, x, z, (ChunkPrimer)null);
            this.oceanMonumentGenerator.generate(this.worldObj, x, z, (ChunkPrimer)null);
        }
    }

    // VANILLA METHODS
    //TOTALLY COPIED THIS

    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
            this.dirtGen = new WorldGenMinable(Blocks.DIRT.getDefaultState(), 33);
            this.gravelGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), 33);
            this.graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), 33);
            this.dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), 33);
            this.andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), 33);
            this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 17);
            this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 9);
            this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 9);
            this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 8);
            this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 8);
            this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 7);
            this.generateOres(worldIn, random);
    }

    protected void generateOres(World worldIn, Random random)
    {
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, random, chunkPos));
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dirtGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT))
            this.genStandardOre1(worldIn, random, 33, this.dirtGen, 0, 128);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, gravelGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRAVEL))
            this.genStandardOre1(worldIn, random, 33, this.gravelGen, 0, 128);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dioriteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIORITE))
            this.genStandardOre1(worldIn, random, 33, this.dioriteGen, 0, 80);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, graniteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRANITE))
            this.genStandardOre1(worldIn, random, 33, this.graniteGen, 0, 80);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, andesiteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.ANDESITE))
            this.genStandardOre1(worldIn, random, 33, this.andesiteGen, 0,80);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, coalGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL))
            this.genStandardOre1(worldIn, random, 17, this.coalGen, 0, 128);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, ironGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON))
            this.genStandardOre1(worldIn, random, 9, this.ironGen, 0, 64);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, goldGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD))
            this.genStandardOre1(worldIn, random, 9, this.goldGen, 0, 32);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, redstoneGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE))
            this.genStandardOre1(worldIn, random, 8, this.redstoneGen, 0, 16);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, diamondGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND))
            this.genStandardOre1(worldIn, random, 8, this.diamondGen, 0, 16);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, lapisGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS))
            this.genStandardOre2(worldIn, random, 7, this.lapisGen, 16, 16);
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, random, chunkPos));
    }

    protected void genStandardOre1(World worldIn, Random random, int blockCount, WorldGenerator generator, int minHeight, int maxHeight)
    {
        if (maxHeight < minHeight)
        {
            int i = minHeight;
            minHeight = maxHeight;
            maxHeight = i;
        }
        else if (maxHeight == minHeight)
        {
            if (minHeight < 255)
            {
                ++maxHeight;
            }
            else
            {
                --minHeight;
            }
        }

        for (int j = 0; j < blockCount; ++j)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }


    protected void genStandardOre2(World worldIn, Random random, int blockCount, WorldGenerator generator, int centerHeight, int spread)
    {
        for (int i = 0; i < blockCount; ++i)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + centerHeight - spread, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }
}

