package com.momiza.easyrails;

import com.momiza.easyrails.rails.GuiContainerConverterRail;
import com.momiza.easyrails.utilblocks.GuiContainerDetector;
import com.momiza.easyrails.utilblocks.GuiContainerI2O1;
import com.momiza.easyrails.utilblocks.GuiContainerLogic;
import com.momiza.easyrails.utilblocks.GuiContainerMinecartConverter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new SimpleContainer(x, y, z);
        //return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == EasyRails.GUI_ID_DETECTOR) {
            return new GuiContainerDetector(new SimpleContainer(x, y, z),world.getTileEntity(new BlockPos(x,y,z)));
        }else if (ID == EasyRails.GUI_ID_I2O1) {
            return new GuiContainerI2O1(new SimpleContainer(x, y, z),world.getTileEntity(new BlockPos(x,y,z)));
        }else if (ID == EasyRails.GUI_ID_LOGIC){
            return new GuiContainerLogic(new SimpleContainer(x, y, z),world.getTileEntity(new BlockPos(x,y,z)));
        }else if (ID == EasyRails.GUI_ID_MINECARTCONVERTER){
            return new GuiContainerMinecartConverter(new SimpleContainer(x, y, z),world.getTileEntity(new BlockPos(x,y,z)));
        }else if (ID == EasyRails.GUI_ID_CONVERTERRAIL){
            return new GuiContainerConverterRail(new SimpleContainer(x, y, z),world,new BlockPos(x,y,z));
        }
        return null;
    }

}