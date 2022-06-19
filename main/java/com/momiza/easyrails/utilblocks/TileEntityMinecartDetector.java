package com.momiza.easyrails.utilblocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMinecartDetector extends TileEntity{
    public boolean[] detect={false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
    public boolean detectChest=true;
    public boolean detectRidable=true;
    public boolean defaultOut=false;
    public void readFromNBT(NBTTagCompound par1NBTTagCompound){
        super.readFromNBT(par1NBTTagCompound);
        int bits = par1NBTTagCompound.getInteger("detectInt");
        for(int i=0;i<16;i++){
            detect[i] = (((bits>>i)&1)==1);
        }
        detectChest = par1NBTTagCompound.getBoolean("detectChest");
        detectRidable = par1NBTTagCompound.getBoolean("detectRidable");
        defaultOut = par1NBTTagCompound.getBoolean("defaultOut");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound){
        super.writeToNBT(par1NBTTagCompound);
        int bits=0;
        for(int i=0;i<16;i++){
            if(detect[i])bits|=(1<<i);
        }
        par1NBTTagCompound.setInteger("detectInt", bits);
        par1NBTTagCompound.setBoolean("detectChest", detectChest);
        par1NBTTagCompound.setBoolean("detectRidable", detectRidable);
        par1NBTTagCompound.setBoolean("defaultOut", defaultOut);
        return par1NBTTagCompound;
    }
    public void setDetect(int bits, boolean detectChest, boolean detectRidable, boolean defaultOut){
        for(int i=0;i<16;i++){
            detect[i] = ((((bits&65535)>>i)&1)==1);
        }
        this.detectChest=detectChest;
        this.detectRidable=detectRidable;
        this.defaultOut=defaultOut;
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
    public NBTTagCompound getUpdateTag(){
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.readFromNBT(pkt.getNbtCompound());
    }

    public boolean[] getDetect()
    {
        return this.detect;
    }

    /*public void setDetect(boolean[] detect)
    {
        this.detect = detect;
    }*/

}