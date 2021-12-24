package com.momiza.easyrails.rails;

import net.minecraft.block.state.IBlockState;

public interface ISlowdownRail {
	public default boolean shouldSlowdown(IBlockState state, int speedLevel) {
		return true;
	}
}
