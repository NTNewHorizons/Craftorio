package com.ntnh.craftorio.block;

import net.minecraft.block.material.Material;

import com.ntnh.craftorio.Craftorio;

public class BlockDraftingTable extends net.minecraft.block.BlockContainer {

    public BlockDraftingTable() {
        super(Material.iron);
        this.setBlockName("drafting_table");
        this.setCreativeTab(Craftorio.tabCraftorio);
        this.setHardness(2.0F);
        this.setBlockTextureName("craftorio:drafting_table");
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(net.minecraft.world.World world, int meta) {
        return new com.ntnh.craftorio.tileentity.TileEntityDraftingTable();
    }

    @Override
    public boolean onBlockActivated(net.minecraft.world.World world, int x, int y, int z,
        net.minecraft.entity.player.EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {
            player.openGui(Craftorio.instance, 0, world, x, y, z);
        }
        return true;
    }
}
