package com.momiza.easyrails;

import com.momiza.easyrails.utilblocks.*;
import com.momiza.easyrails.utilitems.*;
import com.momiza.easyrails.minecarts.*;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import com.momiza.easyrails.minecarts.RenderNewMinecart;
import com.momiza.easyrails.utilblocks.TESRAnd;
import com.momiza.easyrails.utilblocks.TESRLogic;
import com.momiza.easyrails.utilblocks.TESROr;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;


@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy{
    public static final KeyBinding keyPipeOpposite = new KeyBinding("Invert Sides", Keyboard.KEY_O, "");

    @Override
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnd.class,new TESRAnd());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOr.class,new TESROr());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLogic.class,new TESRLogic());
        
        ClientRegistry.registerKeyBinding(keyPipeOpposite);
    }

    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
        /*RenderingRegistry.registerEntityRenderingHandler(EntityTNTPrimed2.class,
                new IRenderFactory<EntityTNTPrimed2>(){
            @Override
            public Render<? super EntityTNTPrimed2> createRenderFor(RenderManager manager) {
                System.out.println("reg");
                return new RenderTNTPrimed2(manager);
            }
        });*/
        RenderingRegistry.registerEntityRenderingHandler(EntityNewMinecartRidable.class, new RenderNewMinecart<EntityNewMinecartRidable>(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityNewMinecartChest.class, new RenderNewMinecart<EntityNewMinecartChest>(Minecraft.getMinecraft().getRenderManager()));
    }
}