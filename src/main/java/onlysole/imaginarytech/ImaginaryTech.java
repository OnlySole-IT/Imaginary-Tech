package onlysole.imaginarytech;

import gregtech.api.worldgen.config.WorldGenRegistry;
import gregtech.common.ConfigHolder;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import onlysole.imaginary_tech.Tags;
import onlysole.imaginarytech.api.utils.ITLog;
import onlysole.imaginarytech.common.CommonProxy;

import java.io.IOException;

@Mod(modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        version = Tags.VERSION,
        dependencies = "required-after:gregtech@[2.8.10-beta,);")
public class ImaginaryTech {

    @SidedProxy(
            modId = Tags.MOD_ID,
            clientSide = "onlysole.imaginarytech.client.ClientProxy",
            serverSide = "onlysole.imaginarytech.common.CommonProxy"
    )
    public static CommonProxy proxy;
    @Mod.EventHandler
    public void onConstruction(FMLConstructionEvent event)
    {
        ConfigHolder.machines.highTierContent = true;
    }
    @Mod.EventHandler
    public void onpreInit(FMLPreInitializationEvent event) {
        ITLog.init(event.getModLog());
        ConfigHolder.machines.highTierContent = true;
        ITLog.logger.info("Enabled GregTechCEu highTierContent");
        //ITMetaItems.init();
        //ITMetaBlocks.init();
        //ITAPI.APIBlockInit();
        //MetaTileEntities.init();
        //ITWorldGenRegistry.init();

        //CapabilityManager.INSTANCE.register(IPollution.class, new PollutionProvider.Storage(), PollutionProvider.Impl::new);
        proxy.preLoad();
        proxy.preInit(event);

    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        //NTWorldGenRegistry.override();
        try {
            WorldGenRegistry.INSTANCE.reinitializeRegisteredVeins();
        } catch (IOException | RuntimeException exception) {
            ITLog.logger.fatal("Failed to initialize worldgen registry.", exception);
        }
    }
}