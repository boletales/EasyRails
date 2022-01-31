package com.momiza.easyrails.otherblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
public class ItemBlockCharcoal extends ItemBlock {
    public ItemBlockCharcoal(Block block) {
        super(block);
    }
    
    public int getItemBurnTime(ItemStack itemStack){
        return 16000;
    }
}