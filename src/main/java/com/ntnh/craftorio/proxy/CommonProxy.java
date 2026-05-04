package com.ntnh.craftorio.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.ntnh.craftorio.Craftorio;
import com.ntnh.craftorio.block.BlockDraftingTable;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    // Items
    public static Item blueprint;

    public class ItemSciencePack extends Item {

        public ItemSciencePack(String name) {
            this.setUnlocalizedName(name);
            this.setTextureName(Craftorio.MODID + ":" + name);
            this.setCreativeTab(Craftorio.tabCraftorio);
        }
    }

    // Blocks

    public static Block drafting_table = new BlockDraftingTable();

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        blueprint = new Item().setUnlocalizedName("blueprint")
            .setCreativeTab(Craftorio.tabCraftorio)
            .setTextureName("craftorio:blueprint");
        GameRegistry.registerItem(blueprint, "blueprint");

        String[] packs = { "automation_science_pack", "logistic_science_pack", "military_science_pack",
            "chemical_science_pack", "production_science_pack", "utility_science_pack", "space_science_pack",
            "metallurgic_science_pack", "electromagnetic_science_pack", "agricultural_science_pack",
            "cryogenic_science_pack", "promethium_science_pack" };
        for (String name : packs) {
            Item pack = new ItemSciencePack(name);
            cpw.mods.fml.common.registry.GameRegistry.registerItem(pack, name);
        }

        GameRegistry.registerBlock(drafting_table, "drafting_table");
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}
