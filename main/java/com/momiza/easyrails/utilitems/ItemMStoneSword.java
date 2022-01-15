package com.momiza.easyrails.utilitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ItemMStoneSword extends ItemSword {

	public ItemMStoneSword(ToolMaterial toolMaterial) {
		super(toolMaterial);
		this.setUnlocalizedName("MStoneSword");
		this.setCreativeTab(CreativeTabs.COMBAT);
	}

}