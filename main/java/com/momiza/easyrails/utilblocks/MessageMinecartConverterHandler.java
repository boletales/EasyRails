package com.momiza.easyrails.utilblocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMinecartConverterHandler implements IMessageHandler<MessageMinecartConverter, IMessage> {
    public int i=0;
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(MessageMinecartConverter message, MessageContext ctx) {
        //System.out.println(message.getX()+","+message.getY()+","+message.getZ()+":"+message.getCh());
    	World w = ctx.getServerHandler().player.getEntityWorld();

        TileEntityMinecartConverter t=((TileEntityMinecartConverter)w.getTileEntity(new BlockPos(message.getX(),message.getY(),message.getZ())));
        t.convertTo=message.getColor();
        //w.markBlockForUpdate(new BlockPos(message.getX(),message.getY(),message.getZ()));
        return null;//本来は返答用IMessageインスタンスを返すのだが、旧来のパケットの使い方をするなら必要ない。
    }
}