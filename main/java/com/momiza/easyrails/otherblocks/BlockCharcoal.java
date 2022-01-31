package com.momiza.easyrails.otherblocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;


public class BlockCharcoal extends net.minecraft.block.Block {

    public BlockCharcoal() {
        super(Material.ROCK);
        setHardness(5.0F);
        setResistance(10.0F);
        setUnlocalizedName("CharcoalBlock");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
}

