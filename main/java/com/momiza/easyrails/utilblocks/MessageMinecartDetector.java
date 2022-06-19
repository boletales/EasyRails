package com.momiza.easyrails.utilblocks;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageMinecartDetector implements IMessage {

    public byte[] data=new byte[12];

    public MessageMinecartDetector(){}

    //*
    public MessageMinecartDetector(boolean[] detectbool,boolean detectChest,boolean detectRidable,boolean defaultOut,int x,int y,int z) {
        int detect=0;
        for(int i=0;i<16;i++){
            if(detectbool[i])detect|=(1<<i);
        }
        this.data[0]= (byte)((detect>>0)-(detect>>8<<8));
        this.data[1]= (byte)((detect>>8)-(detect>>16<<8));
        this.data[2]= (byte)((detectChest?1:0)+(detectRidable?2:0)+(defaultOut?4:0));

        for(int i=0;i<4;i++){
            this.data[i+3]=(byte)((x>>(i*8))-(x>>((i+1)*8)<<8));
        }
        this.data[7]=(byte)y;
        for(int i=0;i<4;i++){
            this.data[i+8]=(byte)((z>>(i*8))-(z>>((i+1)*8)<<8));
        }
    }

    @Override//IMessageのメソッド。ByteBufからデータを読み取る。
    public void fromBytes(ByteBuf buf) {
        buf.readBytes(this.data);
    }

    @Override//IMessageのメソッド。ByteBufにデータを書き込む。
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(this.data);
    }

    public int getDetect(){
        return (this.data[0]&0xff)+((this.data[1]&0xff)<<8);
    }

    public boolean getDetectChest(){
        return (this.data[2]&1)==1;
    }

    public boolean getDetectRidable(){
        return (this.data[2]&2)==2;
    }
    
    public boolean getDefaultOut(){
        return (this.data[2]&4)==4;
    }

    public int getX(){
        int tmp=0;
        for(int i=0;i<4;i++){
            tmp+=(this.data[i+3] & 0xff)<<(i*8);
        }

        return tmp;
    }

    public int getY(){
        return (this.data[7]&0xff);
    }

    public int getZ(){
        int tmp=0;
        for(int i=0;i<4;i++){
            tmp+=(this.data[i+8] & 0xff)<<(i*8);
        }

        return tmp;
    }
}