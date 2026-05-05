package com.ntnh.craftorio.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.ntnh.craftorio.proxy.CommonProxy;
import com.ntnh.craftorio.tileentity.TileEntityDraftingTable;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIDraftingRecipeHandler extends TemplateRecipeHandler {

    public class CachedDraftingRecipe extends CachedRecipe {

        public PositionedStack input1;
        public PositionedStack input2;
        public PositionedStack result;

        public CachedDraftingRecipe(ItemStack in1, ItemStack in2, ItemStack res) {
            this.input1 = new PositionedStack(in1, 48, 34);
            this.input2 = new PositionedStack(in2, 66, 34);
            this.result = new PositionedStack(res, 116, 34);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            List<PositionedStack> stacks = new ArrayList<PositionedStack>();
            stacks.add(input1);
            stacks.add(input2);
            return stacks;
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("container.drafting_table");
    }

    @Override
    public String getGuiTexture() {
        return "craftorio:textures/gui/drafting_table.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return "craftorio";
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(89, 34, 24, 17), "drafting_crafting"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("drafting_crafting") && getClass() == NEIDraftingRecipeHandler.class) {
            addDefaultRecipes();
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result.getItem() == CommonProxy.blueprint) {
            addDefaultRecipes();
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient.getItem() == Items.paper
            || (ingredient.getItem() == Items.dye && ingredient.getItemDamage() == 4)) {
            addDefaultRecipes();
        }
    }

    private void addDefaultRecipes() {
        ItemStack res = new ItemStack(CommonProxy.blueprint);
        ItemStack paper = new ItemStack(Items.paper);
        ItemStack lapis = new ItemStack(Items.dye, 1, 4);

        this.arecipes.add(new CachedDraftingRecipe(paper, lapis, res));
        this.arecipes.add(new CachedDraftingRecipe(lapis, paper, res));
    }

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(89, 34, 176, 0, 24, 16, 48, 0);

        int seconds = TileEntityDraftingTable.BLUEPRINT_CRAFT_TIME / 20;
        String timeText = seconds + " s";

        int color = 0x404040;
        net.minecraft.client.Minecraft.getMinecraft().fontRenderer.drawString(timeText, 93, 24, color);
    }
}
