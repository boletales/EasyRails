package com.momiza.easyrails.utilblocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityI2O1 extends TileEntity{
	EnumFacing IN1=EnumFacing.NORTH;
	EnumFacing IN2=EnumFacing.SOUTH;
	EnumFacing OUT=EnumFacing.EAST;
	boolean SWICH=false;
	boolean SWICHED=false;
	int IN=0;

	public void readFromNBT(NBTTagCompound par1NBTTagCompound){
		super.readFromNBT(par1NBTTagCompound);
		this.IN1 = EnumFacing.values()[par1NBTTagCompound.getInteger("in1")];
		this.IN2 = EnumFacing.values()[par1NBTTagCompound.getInteger("in2")];
		this.OUT = EnumFacing.values()[par1NBTTagCompound.getInteger("out")];
		this.SWICH = par1NBTTagCompound.getBoolean("swich");
		this.IN = par1NBTTagCompound.getInteger("in");
	}

		/*
		 * こちらはNBTを書き込むメソッド。
		 */
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound){
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("in1", this.IN1.getIndex());
		par1NBTTagCompound.setInteger("in2", this.IN2.getIndex());
		par1NBTTagCompound.setInteger("out", this.OUT.getIndex());
		par1NBTTagCompound.setBoolean("swich", this.SWICH);
		par1NBTTagCompound.setInteger("in", this.IN);
		return par1NBTTagCompound;
	}

	/*
	 * パケットの送信・受信処理。
	 * カスタムパケットは使わず、バニラのパケット送受信処理を使用。
	 */

	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		return new SPacketUpdateTileEntity(this.pos, 1, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}
		
	public EnumFacing getIN1()
	{
		return this.IN1;
	}

	public void setIN1(EnumFacing IN1)
	{
		this.IN1 = IN1;
	}


	public boolean getSWICH()
	{
		return this.SWICH;
	}

	public void setSWICH(boolean SWICH)
	{
		if(this.SWICH==SWICH){
			this.SWICHED=false;
		}else{
			this.getWorld().notifyNeighborsOfStateChange(pos,this.getBlockType(),true);
			this.SWICHED=true;
		}
		this.SWICH = SWICH;
	}


	public EnumFacing getIN2()
	{
		return this.IN2;
	}

	public void setIN2(EnumFacing IN2)
	{
		this.IN2 = IN2;
	}


	public EnumFacing getOUT()
	{
		return this.OUT;
	}

	public void setOUT(EnumFacing OUT)
	{
		this.OUT = OUT;
	}


	public int getIN()
	{
		return this.IN;
	}

	public void setIN(int IN)
	{
		this.IN = IN;
	}

	public boolean getSWICHED(){
		return this.SWICHED;
	}

	public void whenStateChange() {
		/*if(this.world.isRemote) {
			PacketHandler.INSTANCE.sendToServer(new MessageI2O1(this.pos.getX() , this.pos.getY(), this.pos.getZ(),this.IN,this.IN1,this.IN2,this.OUT));
		}else {
			this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 2);
		}*/
		this.markDirty();
		this.world.notifyNeighborsOfStateChange(pos, this.blockType, true);
	}
	
	public int getMetadata()
	{
		return 1;
	}
}
