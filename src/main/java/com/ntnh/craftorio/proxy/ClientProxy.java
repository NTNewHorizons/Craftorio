package com.ntnh.craftorio.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        if (cpw.mods.fml.common.Loader.isModLoaded("NotEnoughItems")) {
            registerNEIIntegration();
        }
    }

    private void registerNEIIntegration() {
        com.ntnh.craftorio.nei.NEIDraftingRecipeHandler handler = new com.ntnh.craftorio.nei.NEIDraftingRecipeHandler();

        codechicken.nei.api.API.registerRecipeHandler(handler);
        codechicken.nei.api.API.registerUsageHandler(handler);
    }

}
