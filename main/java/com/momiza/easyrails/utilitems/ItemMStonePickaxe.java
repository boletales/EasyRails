package com.momiza.easyrails.utilitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;

public class ItemMStonePickaxe extends ItemPickaxe {
    public ItemMStonePickaxe(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName("MStonePickaxe");
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

}