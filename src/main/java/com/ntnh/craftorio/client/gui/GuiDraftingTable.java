package com.ntnh.craftorio.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ntnh.craftorio.inventory.ContainerDraftingTable;
import com.ntnh.craftorio.tileentity.TileEntityDraftingTable;

public class GuiDraftingTable extends GuiContainer {

    // Path to the gui image
    private static final ResourceLocation texture = new ResourceLocation(
        "craftorio",
        "textures/gui/drafting_table.png");
    private TileEntityDraftingTable tileEntity;

    public GuiDraftingTable(net.minecraft.entity.player.InventoryPlayer playerInv, TileEntityDraftingTable tile) {
        super(new ContainerDraftingTable(playerInv, tile));
        this.tileEntity = tile;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
            .bindTexture(texture);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        // Drawing the main gui
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        // Displaying a progress bar (lasts 15 seconds)
        int i1 = this.tileEntity.craftTime * 24 / 300; // 300 ticks = 15 seconds
        if (i1 > 0) {
            this.drawTexturedModalRect(k + 89, l + 34, 176, 0, i1, 16);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = net.minecraft.util.StatCollector.translateToLocal("container.drafting_table");
        this.fontRendererObj.drawString(title, 8, 6, 4210752);
    }
}
