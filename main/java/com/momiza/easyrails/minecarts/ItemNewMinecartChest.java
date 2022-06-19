package com.momiza.easyrails.minecarts;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNewMinecartChest extends ItemNewMinecartBase {
    public ItemNewMinecartChest() {
        super();
    }

    @Override
    public ItemStack genStack(int amount,int color){
        return new ItemStack(this, amount, color).setStackDisplayName("§"+Integer.toHexString(color)+"■■§r NewMinecartChest "+color+"§"+Integer.toHexString(color)+"■■");
    }


    @Override
    public  EntityNewMinecart genCart(World worldIn,double x,double y,double z,int meta){
        return new EntityNewMinecartChest(worldIn, x, y, z, meta);
    }
}
