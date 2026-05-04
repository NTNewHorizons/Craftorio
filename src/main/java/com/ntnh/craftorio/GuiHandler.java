package com.ntnh.craftorio;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.ntnh.craftorio.inventory.ContainerDraftingTable;
import com.ntnh.craftorio.tileentity.TileEntityDraftingTable;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) { // Наш ID для Drafting Table
            return new ContainerDraftingTable(player.inventory, (TileEntityDraftingTable) world.getTileEntity(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            return new com.ntnh.craftorio.client.gui.GuiDraftingTable(
                player.inventory,
                (TileEntityDraftingTable) world.getTileEntity(x, y, z));
        }
        return null;
    }
}
