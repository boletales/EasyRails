package com.momiza.easyrails.utilitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
public class ItemInController extends Item {

	public ItemInController() {
		super();
        setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}