package com.momiza.easyrails;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void inputKey(InputEvent.KeyInputEvent e)
    {
        if (ClientProxy.keyPipeOpposite.isPressed())
        {
            EasyRails.INSTANCE.pipeOpposite=!EasyRails.INSTANCE.pipeOpposite;
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(EasyRails.MOD_ID+".HopperOpposite: "+EasyRails.INSTANCE.pipeOpposite));
        }
    }
}
