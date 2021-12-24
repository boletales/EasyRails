package com.momiza.easyrails.utilitems;



import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
public class ItemOutController extends Item {

	public ItemOutController() {
        super();
        setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

}