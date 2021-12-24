package com.momiza.easyrails.utilblocks;

import java.util.List;
import java.util.Random;

import com.momiza.easyrails.minecarts.EntityNewMinecart;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMinecartKiller extends BlockHopperPlus
{

    public BlockMinecartKiller()
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
        return new TileEntityMinecartKiller();
    }

    @Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
		worldIn.updateBlockTick(pos, this, 1, 0);
		TileEntityMinecartKiller tile = (TileEntityMinecartKiller)worldIn.getTileEntity(pos);

		List<Entity> loaded=((World)worldIn).loadedEntityList;
		EnumFacing side=state.getValue(FACING);
		for(int i=0;i<loaded.size();i++){
			Entity e=loaded.get(i);
			if(e instanceof EntityNewMinecart && e.getPosition().offset(EnumFacing.DOWN).equals(pos)){
				((EntityNewMinecart)e).killMinecart(DamageSource.GENERIC);
			}
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		//worldIn.scheduleBlockUpdate(pos, this, 1, 1);
		worldIn.updateBlockTick(pos, this, 1, 0);
	}


	@Override
	public boolean requiresUpdates(){
		return true;
	}
	@Override
	public int tickRate(World worldIn){
		return 1;
	}
}