package com.momiza.easyrails;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class SimpleContainer extends Container {
    int xCoord, yCoord, zCoord;
    public SimpleContainer(int x, int y, int z) {
        super();
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
