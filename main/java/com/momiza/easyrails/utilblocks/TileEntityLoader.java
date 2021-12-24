package com.momiza.easyrails.utilblocks;

public class TileEntityLoader extends TileEntityHopperPlus
{
	private boolean output=false;
	public void setOutput(boolean output){
		this.output=output;
	}
	public boolean getOutput(){
		return output;
	}
}