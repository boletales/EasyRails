package com.momiza.easyrails.utilblocks;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

//レシーバー
public class BlockMinecartDetectorInsulated extends BlockMinecartDetector {
	public static final PropertyInteger METADATA = PropertyInteger.create("output",0,1);
	public BlockMinecartDetectorInsulated() {
		super();
		setCreativeTab(CreativeTabs.REDSTONE);
		setUnlocalizedName("MinecartDetectorInsulated");
		this.setTickRandomly(false);
		this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
		return side==EnumFacing.DOWN ? 0 : super.getWeakPower(blockState, blockAccess, pos, side);
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
		return side==EnumFacing.DOWN ? 0 : super.getStrongPower(blockState, blockAccess, pos, side);
	}
}