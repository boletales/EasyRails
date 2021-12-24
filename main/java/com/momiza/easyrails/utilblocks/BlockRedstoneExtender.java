package com.momiza.easyrails.utilblocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.momiza.easyrails.EasyRails;

import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockRedstoneExtender extends BlockRedstoneRepeater{

    public BlockRedstoneExtender(boolean powered)
    {
        super(powered);
        this.setCreativeTab(CreativeTabs.REDSTONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DELAY, Integer.valueOf(1)).withProperty(LOCKED, Boolean.valueOf(false)));
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
    	return state.getValue(FACING) == side || state.getValue(FACING).getOpposite() == side;
    }
    
	@Override
    protected int getDelay(IBlockState state)
    {
        return this.isPowered(state) ? ((Integer)state.getValue(DELAY)).intValue() * 2 * EasyRails.longrepeaterMult : 0;
    }

	@Override
    protected IBlockState getPoweredState(IBlockState unpoweredState)
    {
        Integer integer = (Integer)unpoweredState.getValue(DELAY);
        Boolean obool = (Boolean)unpoweredState.getValue(LOCKED);
        EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue(FACING);
        return EasyRails.PoweredExtender.getDefaultState().withProperty(FACING, enumfacing).withProperty(DELAY, integer).withProperty(LOCKED, obool);
    }

	@Override
    protected IBlockState getUnpoweredState(IBlockState poweredState)
    {
        Integer integer = (Integer)poweredState.getValue(DELAY);
        Boolean obool = (Boolean)poweredState.getValue(LOCKED);
        EnumFacing enumfacing = (EnumFacing)poweredState.getValue(FACING);
        return EasyRails.UnpoweredExtender.getDefaultState().withProperty(FACING, enumfacing).withProperty(DELAY, integer).withProperty(LOCKED, obool);
    }
    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemBlock(EasyRails.UnpoweredExtender);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(EasyRails.UnpoweredExtender);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, DELAY, LOCKED});
    }
}