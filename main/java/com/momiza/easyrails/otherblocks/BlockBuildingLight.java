package com.momiza.easyrails.otherblocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;


public class BlockBuildingLight extends net.minecraft.block.Block {

    public BlockBuildingLight() {
        super(Material.SPONGE);
        setHardness(0.2F);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setUnlocalizedName("BuildingLight");
        //setBlockName("HyperGlass");
        setResistance(50.0F);
        setLightOpacity(0);
        setLightLevel(1.0F);
    }

    @Override
    public boolean isToolEffective(String tool,IBlockState blockstate){
        return "pickaxe".equals(tool);
    }

    /*@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(METADATA, meta);
    }

    public int getMetaFromState(IBlockState state) {
        return (Integer)state.getValue(METADATA);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, METADATA);
    }*/

    /*@Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
        super.getSubBlocks(itemIn, tab, list);
        list.add(new ItemStack(itemIn, 1, 1));
    }*/



}

