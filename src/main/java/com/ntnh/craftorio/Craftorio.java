package com.ntnh.craftorio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ntnh.craftorio.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Craftorio.MODID, version = Tags.VERSION, name = "Craftorio", acceptedMinecraftVersions = "[1.7.10]")
public class Craftorio {

    public static final String MODID = "craftorio";
    public static final Logger LOG = LogManager.getLogger(MODID);
    @Mod.Instance(MODID)
    public static Craftorio instance;

    // Creating TAB
    public static net.minecraft.creativetab.CreativeTabs tabCraftorio = new net.minecraft.creativetab.CreativeTabs(
        "tabCraftorio") {

        @Override
        public net.minecraft.item.Item getTabIconItem() {
            return (CommonProxy.blueprint != null) ? CommonProxy.blueprint : net.minecraft.init.Items.paper;
        }
    };

    @SidedProxy(
        clientSide = "com.ntnh.craftorio.proxy.ClientProxy",
        serverSide = "com.ntnh.craftorio.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        cpw.mods.fml.common.network.NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        proxy.init(event);
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
