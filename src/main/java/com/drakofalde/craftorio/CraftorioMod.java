package com.drakofalde.craftorio;

import com.drakofalde.craftorio.config.ConfigHandler;
import com.drakofalde.craftorio.event.CommonEventHandler;
import com.drakofalde.craftorio.proxy.CommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

@Mod(modid = CraftorioMod.MODID, name = CraftorioMod.NAME, version = CraftorioMod.VERSION)
public class CraftorioMod {
    public static final String MODID = "craftorio";
    public static final String NAME = "Craftorio";
    public static final String VERSION = "0.1.0";

    @Mod.Instance(MODID)
    public static CraftorioMod instance;

    @SidedProxy(
        clientSide = "com.drakofalde.craftorio.proxy.ClientProxy",
        serverSide = "com.drakofalde.craftorio.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    public static Logger log;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(ConfigHandler.INSTANCE);
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        CommonEventHandler handler = new CommonEventHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}