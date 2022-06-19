package com.momiza.easyrails.utilblocks;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.VanillaInventoryCodeHooks;

public class HopperPlusInventoryCodeHooks extends VanillaInventoryCodeHooks {
    @Nullable
    public static Boolean extractHook(IHopper dest,EnumFacing enumfacing)
    {
        BlockPos pos = ((TileEntity)dest).getPos().offset(enumfacing);
        Pair<IItemHandler, Object> itemHandlerResult = getItemHandler(dest.getWorld(), pos.getX(), pos.getY(), pos.getZ(), enumfacing.getOpposite());
        if (itemHandlerResult == null)
            return null;

        IItemHandler handler = itemHandlerResult.getKey();

        for (int i = 0; i < handler.getSlots(); i++)
        {
            ItemStack extractItem = handler.extractItem(i, 1, true);
            if (!extractItem.isEmpty())
            {
                for (int j = 0; j < dest.getSizeInventory(); j++)
                {
                    ItemStack destStack = dest.getStackInSlot(j);
                    if (dest.isItemValidForSlot(j, extractItem) && (destStack.isEmpty() || destStack.getCount() < destStack.getMaxStackSize() && destStack.getCount() < dest.getInventoryStackLimit() && ItemHandlerHelper.canItemStacksStack(extractItem, destStack)))
                    {
                        extractItem = handler.extractItem(i, 1, false);
                        if (destStack.isEmpty())
                            dest.setInventorySlotContents(j, extractItem);
                        else
                        {
                            destStack.grow(1);
                            dest.setInventorySlotContents(j, destStack);
                        }
                        dest.markDirty();
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
