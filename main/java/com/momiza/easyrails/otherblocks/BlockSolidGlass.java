package com.momiza.easyrails.otherblocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;


public class BlockSolidGlass extends net.minecraft.block.BlockGlass {

    public BlockSolidGlass() {
        super(Material.ROCK,true);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(0.3F);
        setResistance(10.0F);
        setLightOpacity(0);
        setLightLevel(0.0F);
    }
    
	@Override
    public boolean isTranslucent(IBlockState state)
    {
		return true;
    }
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
		return false;
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }
}

