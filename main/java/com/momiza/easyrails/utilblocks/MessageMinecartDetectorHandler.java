package com.momiza.easyrails.utilblocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMinecartDetectorHandler implements IMessageHandler<MessageMinecartDetector, IMessage> {
    public int i=0;
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(MessageMinecartDetector message, MessageContext ctx) {
        //System.out.println(message.getX()+","+message.getY()+","+message.getZ()+":"+message.getCh());
        if(ctx.side.isServer()) {
        	World w = ctx.getServerHandler().player.getEntityWorld();
        	
            TileEntityMinecartDetector t=((TileEntityMinecartDetector)w.getTileEntity(new BlockPos(message.getX(),message.getY(),message.getZ())));
            t.setDetect(message.getDetect(), message.getDetectChest(), message.getDetectRidable(), message.getDefaultOut());
            //w.markBlockForUpdate(new BlockPos(message.getX(),message.getY(),message.getZ()));
        }
        
        return null;//本来は返答用IMessageインスタンスを返すのだが、旧来のパケットの使い方をするなら必要ない。
    }
}