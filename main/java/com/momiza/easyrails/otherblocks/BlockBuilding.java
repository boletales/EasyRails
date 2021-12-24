package com.momiza.easyrails.otherblocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;


public class BlockBuilding extends net.minecraft.block.Block {

    public BlockBuilding() {
        super(Material.SPONGE);
        setHardness(0.2F);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setUnlocalizedName("BuildingBlock");
        //setBlockName("HyperGlass");
        setResistance(50.0F);
        setLightOpacity(16);
        setLightLevel(0.0F);
    }

    @Override
    public boolean isToolEffective(String tool,IBlockState blockstate){
    	return "pickaxe".equals(tool);
    }



}

