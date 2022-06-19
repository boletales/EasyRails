package com.momiza.easyrails.rails;

import com.momiza.easyrails.EasyRails;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageConverterRailHandler implements IMessageHandler<MessageConverterRail, IMessage> {
    public int i=0;
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(MessageConverterRail message, MessageContext ctx) {
        //System.out.println(message.getX()+","+message.getY()+","+message.getZ()+":"+message.getCh());
        World w=FMLClientHandler.instance().getServer().getEntityWorld();
        Block block=w.getBlockState(new BlockPos(message.getX(),message.getY(),message.getZ())).getBlock();
        w.setBlockState(new BlockPos(message.getX(),message.getY(),message.getZ()),EasyRails.ConverterRail.getStateFromMeta(message.getColor()));
        //w.markBlockForUpdate(new BlockPos(message.getX(),message.getY(),message.getZ()));
        return null;//本来は返答用IMessageインスタンスを返すのだが、旧来のパケットの使い方をするなら必要ない。
    }
}