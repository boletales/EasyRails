package com.momiza.easyrails.utilitems;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;


public class ItemMStoneAxe extends ItemAxe {
	public ItemMStoneAxe(ToolMaterial toolMaterial) {
		super(ToolMaterial.DIAMOND);
		this.toolMaterial = toolMaterial;
       this.setMaxDamage(toolMaterial.getMaxUses());
       this.efficiency = toolMaterial.getEfficiency();
		this.setUnlocalizedName("MStoneAxe");
		this.setCreativeTab(CreativeTabs.TOOLS);
	}

}