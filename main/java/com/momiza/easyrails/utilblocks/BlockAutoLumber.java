package com.momiza.easyrails.utilblocks;


import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class BlockAutoLumber extends BlockContainer {

    //public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockAutoLumber()
    {
        super(new Material(MapColor.IRON));
		setHardness(1.0F);
		setResistance(10.0F);
        this.setDefaultState(this.blockState.getBaseState());
		setCreativeTab(CreativeTabs.REDSTONE);
		setSoundType(SoundType.METAL);
    }


	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		//worldIn.scheduleBlockUpdate(pos, this, 1, 1);
		worldIn.updateBlockTick(pos, this, 1, 0);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
		worldIn.updateBlockTick(pos, this, 1, 0);
		((TileEntityAutoLumber)worldIn.getTileEntity(pos)).update();
	}

    @Override
	public boolean canSustainPlant(IBlockState state,IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		return true;
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAutoLumber();
	}


	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}

