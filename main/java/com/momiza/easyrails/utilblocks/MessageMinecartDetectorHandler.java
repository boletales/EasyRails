package com.momiza.easyrails.utilblocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMinecartDetectorHandler implements IMessageHandler<MessageMinecartDetector, IMessage> {
	public int i=0;
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(MessageMinecartDetector message, MessageContext ctx) {
        //System.out.println(message.getX()+","+message.getY()+","+message.getZ()+":"+message.getCh());
        World w=FMLClientHandler.instance().getServer().getEntityWorld();
        TileEntityMinecartDetector t=((TileEntityMinecartDetector)w.getTileEntity(new BlockPos(message.getX(),message.getY(),message.getZ())));
        if(!t.getWorld().isRemote){
        	t.setDetect(message.getDetect(), message.getDetectChest(), message.getDetectRidable(), message.getDefaultOut());
        }
        //w.markBlockForUpdate(new BlockPos(message.getX(),message.getY(),message.getZ()));
        return null;//本来は返答用IMessageインスタンスを返すのだが、旧来のパケットの使い方をするなら必要ない。
    }
}