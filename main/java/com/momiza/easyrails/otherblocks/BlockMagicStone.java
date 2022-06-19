package com.momiza.easyrails.otherblocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;


public class BlockMagicStone extends net.minecraft.block.Block {

    public BlockMagicStone() {
        super(Material.IRON);
        setHardness(2F);
        setCreativeTab(CreativeTabs.MATERIALS);
        setUnlocalizedName("MagicStoneBlock");
        //setBlockName("HyperGlass");
        setResistance(10.0F);
    }

    @Override
    public boolean isToolEffective(String tool,IBlockState blockstate){
        return "pickaxe".equals(tool);
    }



}

