package com.momiza.easyrails.utilblocks;

import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class BlockOr extends BlockI2O1{
	public BlockOr() {
		super();
		setCreativeTab(CreativeTabs.REDSTONE);
		setSoundType(SoundType.METAL);
		setUnlocalizedName("OrBlock");
		setHardness(1.0F);
		setResistance(300.0F);
		setLightOpacity(0);
		setLightLevel(0.0F);
	}
	@Override
	public boolean calc(TileEntityI2O1 tile, boolean I1, boolean I2) {
		return I1 || I2;
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityOr();
	}
}