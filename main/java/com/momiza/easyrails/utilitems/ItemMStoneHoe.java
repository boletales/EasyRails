package com.momiza.easyrails.utilitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;

public class ItemMStoneHoe extends ItemHoe {

    public ItemMStoneHoe(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName("MStoneHoe");
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

}