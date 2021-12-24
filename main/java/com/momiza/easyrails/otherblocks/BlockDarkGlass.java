package com.momiza.easyrails.otherblocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;


public class BlockDarkGlass extends net.minecraft.block.BlockGlass {

    public BlockDarkGlass() {
        super(Material.GLASS,true);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(0.3F);
        setResistance(50.0F);
        setLightOpacity(15);
        setLightLevel(0.0F);
    }
    public int quantityDropped(Random random)
    {
        return 1;
    }
}

