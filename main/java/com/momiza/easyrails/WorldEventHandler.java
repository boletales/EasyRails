package com.momiza.easyrails;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHandler {

    //public List<ChunkCoordIntPair> loading=new ArrayList<ChunkCoordIntPair>();

    @SubscribeEvent
    public void worldUnloadEvent(Unload e) {
        EasyRails.chunkloadTicket = null;
        //loading=new ArrayList<ChunkCoordIntPair>();
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent //@SideOnly(Side.SERVER)
    public void onWorldLoad(Load e) {
        EasyRails.chunkloadTicket = ForgeChunkManager.requestTicket(EasyRails.INSTANCE, e.getWorld(), Type.NORMAL);
        //loading=(List<ChunkCoordIntPair>) RemoteMod.INSTANCE.chunkloadTicket.getChunkList();
    }

}
