package com.momiza.easyrails;



import com.momiza.easyrails.rails.MessageConverterRail;
import com.momiza.easyrails.rails.MessageConverterRailHandler;
import com.momiza.easyrails.utilblocks.MessageI2O1;
import com.momiza.easyrails.utilblocks.MessageI2O1Handler;
import com.momiza.easyrails.utilblocks.MessageLogic;
import com.momiza.easyrails.utilblocks.MessageLogicHandler;
import com.momiza.easyrails.utilblocks.MessageMinecartConverter;
import com.momiza.easyrails.utilblocks.MessageMinecartConverterHandler;
import com.momiza.easyrails.utilblocks.MessageMinecartDetector;
import com.momiza.easyrails.utilblocks.MessageMinecartDetectorHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


public class PacketHandler {

    //このMOD用のSimpleNetworkWrapperを生成。チャンネルの文字列は固有であれば何でも良い。MODIDの利用を推奨。
    //チャンネル名は20文字以内の文字数制限があるので注意。
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(EasyRails.MOD_ID);


    public static void init() {

        /*IMesssageHandlerクラスとMessageクラスの登録。
        *第三引数：MessageクラスのMOD内での登録ID。256個登録できる
        *第四引数：送り先指定。クライアントかサーバーか、Side.CLIENT Side.SERVER*/
        INSTANCE.registerMessage(MessageMinecartDetectorHandler.class,MessageMinecartDetector.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageI2O1Handler.class,MessageI2O1.class, 1, Side.SERVER);
        INSTANCE.registerMessage(MessageLogicHandler.class,MessageLogic.class, 2, Side.SERVER);
        INSTANCE.registerMessage(MessageMinecartConverterHandler.class,MessageMinecartConverter.class, 3, Side.SERVER);
        INSTANCE.registerMessage(MessageConverterRailHandler.class,MessageConverterRail.class, 4, Side.SERVER);
    }
}