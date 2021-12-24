package com.momiza.easyrails.utilblocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.momiza.easyrails.minecarts.EntityNewMinecartChest;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockUnloader extends BlockHopperPlus
{

    public BlockUnloader()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN).withProperty(ENABLED, Boolean.valueOf(true)));
        this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setTickRandomly(false);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityUnloader();
    }

	@Override
	public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return false;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
		return detect(blockAccess,pos,blockState) ? 15 : 0;
		//return (1==worldIn.getBlockState(pos).getValue(METADATA)) ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
		return detect(blockAccess,pos,blockState) ? 15 : 0;
		//return (1==worldIn.getBlockState(pos).getValue(METADATA)) ? 15 : 0;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side){
		return true;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
		worldIn.updateBlockTick(pos, this, 1, 0);
		TileEntityUnloader tile = (TileEntityUnloader)worldIn.getTileEntity(pos);
		boolean newOutput = this.detect(worldIn,pos,state);
		if(tile.getOutput()!=newOutput){
			tile.setOutput(newOutput);
			worldIn.notifyNeighborsOfStateChange(pos, this, true);
		}
	}

	@Override
	public int tickRate(World worldIn){
		return 1;
	}

	private boolean detect(IBlockAccess worldIn, BlockPos pos, IBlockState state){
		List<Entity> loaded=((World)worldIn).loadedEntityList;
		EnumFacing side=state.getValue(FACING);
		for(Entity _e : loaded){
			if(_e instanceof EntityNewMinecartChest && _e.getPosition().offset(side).equals(pos)){
				EntityNewMinecartChest e = ((EntityNewMinecartChest)_e);
				for(int i=0;i<e.getSizeInventory();i++){
					if(e.getStackInSlot(i).getItem()!=Items.AIR){
						return false;
					}
				}
			}
		}
		return true;
	}
}