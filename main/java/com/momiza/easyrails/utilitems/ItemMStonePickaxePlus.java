package com.momiza.easyrails.utilitems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemMStonePickaxePlus extends ItemPickaxe {

    public ItemMStonePickaxePlus(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName("MStonePickaxePlus");
        this.setCreativeTab(CreativeTabs.TOOLS);
    }


    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return (state.getMaterial() == Material.SAND   ||
                state.getMaterial() == Material.GROUND ||
                state.getMaterial() == Material.GRASS  ||
                state.getMaterial() == Material.IRON   ||
                state.getMaterial() == Material.ANVIL  ||
                state.getMaterial() == Material.ROCK)     ? this.efficiency : super.getDestroySpeed(stack, state);
    }
}