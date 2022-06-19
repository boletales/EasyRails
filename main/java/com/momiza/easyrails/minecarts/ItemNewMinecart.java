package com.momiza.easyrails.minecarts;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNewMinecart extends ItemNewMinecartBase {
    public ItemNewMinecart() {
        super();
    }

    @Override
    public ItemStack genStack(int amount,int color){
        return new ItemStack(this, amount, color).setStackDisplayName("§"+Integer.toHexString(color)+"■■§r NewMinecart "+color+"§"+Integer.toHexString(color)+"■■");
    }

    @Override
    public  EntityNewMinecart genCart(World worldIn,double x,double y,double z,int meta){
        return new EntityNewMinecartRidable(worldIn, x, y, z, meta);
    }
}