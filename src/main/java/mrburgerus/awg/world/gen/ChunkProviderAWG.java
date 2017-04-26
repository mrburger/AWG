package mrburgerus.awg.world.gen;

import mrburgerus.awg.world.deco.GenCactusAWG;
import mrburgerus.awg.world.deco.GenTreeAWG;
import mrburgerus.awg.world.gen.noisegenerator.NoiseGeneratorOctaves3DAWG;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
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

    private ChunkProviderSettings settings;

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
        biome.decorate(this.worldObj, this.random, blockpos);


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
}

