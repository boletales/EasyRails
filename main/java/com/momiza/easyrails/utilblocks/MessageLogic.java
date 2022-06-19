package com.momiza.easyrails.utilblocks;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageLogic implements IMessage {

    public byte[] data=new byte[14];

    public MessageLogic(){}

    //*
    public MessageLogic(int x,int y,int z,int inToggle,EnumFacing in1,EnumFacing in2,EnumFacing out,boolean[][] logic) {
        for(int i=0;i<4;i++){
            this.data[i]=(byte)((x>>(i*8))-(x>>((i+1)*8)<<8));
        }
        this.data[4]=(byte)y;
        for(int i=0;i<4;i++){
            this.data[i+5]=(byte)((z>>(i*8))-(z>>((i+1)*8)<<8));
        }
        this.data[9]=(byte)inToggle;
        this.data[10]=(byte)in1.getIndex();
        this.data[11]=(byte)in2.getIndex();
        this.data[12]=(byte)out.getIndex();
        this.data[13]=TileEntityLogic.logicToByte(logic);
    }

    @Override//IMessageのメソッド。ByteBufからデータを読み取る。
    public void fromBytes(ByteBuf buf) {
        buf.readBytes(this.data);
    }

    @Override//IMessageのメソッド。ByteBufにデータを書き込む。
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(this.data);
    }

    public int getInToggle(){
        return this.data[9];
    }
    public EnumFacing getIn1(){
        return EnumFacing.values()[this.data[10]];
    }
    public EnumFacing getIn2(){
        return EnumFacing.values()[this.data[11]];
    }
    public EnumFacing getOut(){
        return EnumFacing.values()[this.data[12]];
    }
    public boolean[][] getLogic(){
        return TileEntityLogic.byteToLogic(this.data[13]);
    }

    public int getX(){
        int tmp=0;
        for(int i=0;i<4;i++){
            tmp+=(this.data[i] & 0xff)<<(i*8);
        }

        return tmp;
    }

    public int getY(){
        return (this.data[4]&0xff);
    }

    public int getZ(){
        int tmp=0;
        for(int i=0;i<4;i++){
            tmp+=(this.data[i+5] & 0xff)<<(i*8);
        }

        return tmp;
    }
}