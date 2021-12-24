package com.momiza.easyrails.utilblocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageLogicHandler implements IMessageHandler<MessageLogic, IMessage> {
	public int i=0;
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(MessageLogic message, MessageContext ctx) {
        //System.out.println(message.getX()+","+message.getY()+","+message.getZ()+":"+message.getCh());
    	Side side=FMLClientHandler.instance().getSide();
        World w=FMLClientHandler.instance().getServer().getEntityWorld();
        TileEntityLogic t=((TileEntityLogic)w.getTileEntity(new BlockPos(message.getX(),message.getY(),message.getZ())));
        if(t!=null) {
            t.setIN(message.getInToggle());
            t.setIN1(message.getIn1());
            t.setIN2(message.getIn2());
            t.setOUT(message.getOut());
            t.setLOGIC(message.getLogic());
            t.whenStateChange();
        }
        //w.markBlockForUpdate(new BlockPos(message.getX(),message.getY(),message.getZ()));
        return null;//本来は返答用IMessageインスタンスを返すのだが、旧来のパケットの使い方をするなら必要ない。
    }
}