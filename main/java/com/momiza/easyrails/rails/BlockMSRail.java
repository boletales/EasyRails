package com.momiza.easyrails.rails;

import com.momiza.easyrails.minecarts.EntityNewMinecart;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMSRail extends BlockHSRail
{
    public BlockMSRail()
    {
        super();
        setUnlocalizedName("MSRail");
    }
    @Override
    public void onMinecartPass(World world, net.minecraft.entity.item.EntityMinecart cart, BlockPos pos)
    {
    	if(cart instanceof EntityNewMinecart){
    		if(world.getBlockState(pos).getValue(POWERED)){
        		((EntityNewMinecart) cart).speedLevel=1;
    		}else{
        		((EntityNewMinecart) cart).speedLevel=0;
    		}
    	}
    }
    
	public boolean shouldSlowdown(IBlockState state, int speedLevel) {
		return state.getValue(POWERED)? speedLevel!=1 : speedLevel!=0;
	}
}