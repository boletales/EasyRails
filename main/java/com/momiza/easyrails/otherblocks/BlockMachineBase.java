package com.momiza.easyrails.otherblocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockMachineBase extends net.minecraft.block.Block {

    public BlockMachineBase() {
        super(Material.IRON);
        setHardness(2F);
        setCreativeTab(CreativeTabs.MATERIALS);
        setUnlocalizedName("MachineBase");
        //setBlockName("HyperGlass");
        setResistance(10.0F);
    }

    @Override
    public boolean isToolEffective(String tool,IBlockState blockstate){
        return "pickaxe".equals(tool);
    }


    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
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

}

