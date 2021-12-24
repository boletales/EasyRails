package com.momiza.easyrails.rails;

import net.minecraft.block.state.IBlockState;

public class BlockSwitchRailL extends BlockSwitchRailParent {

    public BlockSwitchRailL()
    {
        super();
		setUnlocalizedName("SwitchRailL");
    }

    @Override
    public EnumRailDirection getCurve(IBlockState state) {
		if(state.getValue(SHAPE)==EnumRailDirection.NORTH_SOUTH){
			if(state.getValue(PLUS)) {
				return EnumRailDirection.NORTH_EAST;
			}else{
				return EnumRailDirection.SOUTH_WEST;
			}
		}else/* if(state.getValue(SHAPE)==EnumRailDirection.EAST_WEST)*/{
			if(state.getValue(PLUS)) {
				return EnumRailDirection.NORTH_WEST;
			}else{
				return EnumRailDirection.SOUTH_EAST;
			}
		}
    }
}
