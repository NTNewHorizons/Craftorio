package com.ntnh.craftorio.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ntnh.craftorio.tileentity.TileEntityDraftingTable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerDraftingTable extends Container {

    private TileEntityDraftingTable tileEntity;

    public ContainerDraftingTable(InventoryPlayer playerInventory, TileEntityDraftingTable tile) {
        this.tileEntity = tile;

        // Slot 0: Blue Dye (Lapis) - Coordinates: x=44, y=35
        this.addSlotToContainer(new Slot(tile, 0, 44, 35));

        // Slot 1: Paper - Coordinates: x=66, y=35
        this.addSlotToContainer(new Slot(tile, 1, 66, 35));

        // Slot 2: Output (Blueprints) - Coordinates: x=124, y=35[cite: 1]
        // We make it so you can't put items IN, only take OUT
        this.addSlotToContainer(new Slot(tile, 2, 124, 35) {

            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        // Player Main Inventory (3 rows of 9 slots)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Player Hotbar (9 slots)
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.isUseableByPlayer(player);
    }

    // This method handles Shift-Clicking (crucial to prevent crashes)
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 3) { // From TileEntity to Player
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return null;
                }
            } else { // From Player to TileEntity
                if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
                    return null;
                }
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    private int lastCraftTime;

    @Override
    public void addCraftingToCrafters(ICrafting crafter) {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, this.tileEntity.craftTime);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafter = (ICrafting) this.crafters.get(i);

            if (this.lastCraftTime != this.tileEntity.craftTime) {
                crafter.sendProgressBarUpdate(this, 0, this.tileEntity.craftTime);
            }
        }

        this.lastCraftTime = this.tileEntity.craftTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        if (id == 0) {
            this.tileEntity.craftTime = data;
        }
    }
}
