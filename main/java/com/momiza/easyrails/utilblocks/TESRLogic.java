package com.momiza.easyrails.utilblocks;
import com.momiza.easyrails.EasyRails;

import net.minecraft.util.ResourceLocation;

public class TESRLogic extends TESRI2O1{
    @Override
    public ResourceLocation getNormalTexture() {
        return new ResourceLocation(EasyRails.MOD_ID,"textures/blocks/logic_block.png");
    }
}