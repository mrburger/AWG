package mrburgerus.awg;

import mrburgerus.awg.proxy.ClientProxy;
import mrburgerus.awg.proxy.CommonProxy;
import mrburgerus.awg.world.WorldTypeAWG;
import mrburgerus.awg.world.biome.biomes.BiomeAlpha;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by mrburgerUS on 4/23/2017.
 */

@Mod(modid = AWG.MOD_ID, name = AWG.NAME, version = AWG.VERSION, acceptableRemoteVersions = "*")
public class AWG
{
    //MOD INFO
    public static final String MOD_ID = "awg";
    public static final String NAME = "alphaworldgenerator";
    public static final String VERSION = "0.01";

    //Constants
    public static final String WORLD_TYPE = "AWG";
    public static final String FILE_LOC = "AWG";

    //Variables
    public static WorldTypeAWG worldtype;
    public static BiomeAlpha alphaBiome;
    public static String configPath;
    public static Logger logger;

    //The Party
    @Mod.Instance(AWG.MOD_ID)
    public static AWG instance;

    @SidedProxy(serverSide = CommonProxy.LOC, clientSide = ClientProxy.LOC)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;

        worldtype = new WorldTypeAWG(WORLD_TYPE);

        //alphaBiome = new BiomeAlpha(new Biome.BiomeProperties("Alpha Biome"));
        //GameRegistry.register(alphaBiome);
        //BiomeManager.addSpawnBiome(alphaBiome);
        //BiomeManager.addStrongholdBiome(alphaBiome);
        //BiomeManager.addVillageBiome(alphaBiome, true);

        configPath = event.getModConfigurationDirectory() + File.separator + AWG.FILE_LOC + File.separator;


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        //BiomeDictionary.addTypes(alphaBiome);
    }

}
