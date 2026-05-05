package com.ntnh.craftorio.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDraftingTable extends TileEntity implements IInventory {

    public int craftTime = 0;

    // 0: Blue Dye, 1: Paper, 2: Output (Blueprints)
    private ItemStack[] inventory = new ItemStack[3];

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return "container.drafting_table";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack;
            if (this.inventory[slot].stackSize <= amount) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(amount);
                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }
                this.markDirty();
                return itemstack;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.craftTime = nbt.getShort("CraftTime");

        NBTTagList list = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound compound = list.getCompoundTagAt(i);
            byte slot = compound.getByte("Slot");

            if (slot >= 0 && slot < this.inventory.length) {
                this.inventory[slot] = ItemStack.loadItemStackFromNBT(compound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setShort("CraftTime", (short) this.craftTime);

        NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(compound);
                list.appendTag(compound);
            }
        }
        nbt.setTag("Items", list);
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {

            if (canCraft()) {
                this.craftTime++;

                if (this.craftTime >= 300) {
                    this.craftTime = 0;
                    this.craftItem();
                }
            } else {
                this.craftTime = 0;
            }
        }
    }

    private boolean canCraft() {
        ItemStack slot0 = this.getStackInSlot(0);
        ItemStack slot1 = this.getStackInSlot(1);
        ItemStack resultSlot = this.getStackInSlot(2);

        if (slot0 == null || slot1 == null) return false;

        boolean s0Paper = slot0.getItem() == net.minecraft.init.Items.paper;
        boolean s0Dye = slot0.getItem() == net.minecraft.init.Items.dye && slot0.getItemDamage() == 4;

        boolean s1Paper = slot1.getItem() == net.minecraft.init.Items.paper;
        boolean s1Dye = slot1.getItem() == net.minecraft.init.Items.dye && slot1.getItemDamage() == 4;

        boolean hasIngredients = (s0Paper && s1Dye) || (s0Dye && s1Paper);
        if (!hasIngredients) return false;

        // Проверка выходного слота (2)
        if (resultSlot == null) return true;

        if (resultSlot.getItem() == com.ntnh.craftorio.proxy.CommonProxy.blueprint) {
            return resultSlot.stackSize < resultSlot.getMaxStackSize();
        }

        return false;
    }

    private void craftItem() {
        this.decrStackSize(0, 1);
        this.decrStackSize(1, 1);

        ItemStack resultStack = this.getStackInSlot(2);

        if (resultStack == null) {
            this.setInventorySlotContents(2, new ItemStack(com.ntnh.craftorio.proxy.CommonProxy.blueprint));
        } else {
            resultStack.stackSize++;
        }
    }
}
