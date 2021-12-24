package com.momiza.easyrails.rails;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageConverterRail implements IMessage {

    public byte[] data=new byte[10];

    public MessageConverterRail(){}

    //*
    public MessageConverterRail(int x,int y,int z,int color) {
	    for(int i=0;i<4;i++){
	    	this.data[i]=(byte)((x>>(i*8))-(x>>((i+1)*8)<<8));
	    }
	    this.data[4]=(byte)y;
	    for(int i=0;i<4;i++){
	    	this.data[i+5]=(byte)((z>>(i*8))-(z>>((i+1)*8)<<8));
	    }
	    this.data[9]=(byte)color;
	}

    @Override//IMessageのメソッド。ByteBufからデータを読み取る。
    public void fromBytes(ByteBuf buf) {
        buf.readBytes(this.data);
    }

    @Override//IMessageのメソッド。ByteBufにデータを書き込む。
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(this.data);
    }

    public int getColor(){
    	return this.data[9];
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