package com.ntnh.craftorio.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ntnh.craftorio.inventory.ContainerDraftingTable;
import com.ntnh.craftorio.tileentity.TileEntityDraftingTable;

public class GuiDraftingTable extends GuiContainer {

    // Путь к картинке фона (создадим её позже)
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

        // Рисуем основной фон
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        // Рисуем стрелочку прогресса (длится 15 секунд)
        int i1 = this.tileEntity.craftTime * 24 / 300; // 300 тиков = 15 секунд
        this.drawTexturedModalRect(k + 89, l + 34, 176, 0, i1 + 1, 16);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        // Название окна сверху
        this.fontRendererObj.drawString("Drafting Table", 8, 6, 4210752);
    }
}
