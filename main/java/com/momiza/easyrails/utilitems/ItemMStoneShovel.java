package com.momiza.easyrails.utilitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;

public class ItemMStoneShovel extends ItemSpade {

    public ItemMStoneShovel(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName("MStoneShovel");
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

}