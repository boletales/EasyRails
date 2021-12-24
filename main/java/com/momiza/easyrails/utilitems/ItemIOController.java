package com.momiza.easyrails.utilitems;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
public class ItemIOController extends Item {
	public float efficiencyOnProperMaterial=25;

	public ItemIOController() {
        super();
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return (state.getBlock() instanceof BlockRailBase) ?  this.efficiencyOnProperMaterial : 1.0F;
    }

}