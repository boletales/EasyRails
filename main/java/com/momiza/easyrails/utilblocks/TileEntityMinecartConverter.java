package com.momiza.easyrails.utilblocks;

import com.momiza.easyrails.minecarts.ItemNewMinecartBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMinecartConverter extends TileEntityHopperPlus
{
	public int convertTo=0;

	@Override
	public void update()
	{
		for(int i=0;i<this.getSizeInventory();i++){
			ItemStack stack = this.getStackInSlot(i);
			if(stack!=null && stack.getItem() instanceof ItemNewMinecartBase){
				this.setInventorySlotContents(i, ((ItemNewMinecartBase)stack.getItem()).genStack(stack.getCount(), this.convertTo));
			}
		}
		super.update();
	}

	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound){
		par1NBTTagCompound.setInteger("convertTo", this.convertTo);
		super.writeToNBT(par1NBTTagCompound);
		return par1NBTTagCompound;
	}
	public void readFromNBT(NBTTagCompound par1NBTTagCompound){
		this.convertTo = par1NBTTagCompound.getInteger("convertTo");
		super.readFromNBT(par1NBTTagCompound);
	}
	
	@Override
	public NBTTagCompound getUpdateTag(){
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}
}