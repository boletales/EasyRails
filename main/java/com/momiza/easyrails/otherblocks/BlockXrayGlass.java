package com.momiza.easyrails.otherblocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockXrayGlass extends Block {

    public BlockXrayGlass() {
        super(Material.GLASS);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(0.3F);
        setResistance(1.0F);
        setLightOpacity(0);
        setLightLevel(15.0F);
    }


    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }


    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

}