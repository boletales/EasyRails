package com.momiza.easyrails.rails;

import net.minecraft.block.state.IBlockState;

public class BlockSwitchRailR extends BlockSwitchRailParent {

    public BlockSwitchRailR()
    {
        super();
		setUnlocalizedName("SwitchRailR");
    }

    @Override
    public EnumRailDirection getCurve(IBlockState state) {
		if(state.getValue(SHAPE)==EnumRailDirection.NORTH_SOUTH){
			if(state.getValue(PLUS)) {
				return EnumRailDirection.NORTH_WEST;
			}else{
				return EnumRailDirection.SOUTH_EAST;
			}
		}else/* if(state.getValue(SHAPE)==EnumRailDirection.EAST_WEST)*/{
			if(state.getValue(PLUS)) {
				return EnumRailDirection.SOUTH_WEST;
			}else{
				return EnumRailDirection.NORTH_EAST;
			}
		}
    }
    
}
