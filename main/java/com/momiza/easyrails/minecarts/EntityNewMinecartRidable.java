package com.momiza.easyrails.minecarts;

import com.momiza.easyrails.EasyRails;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;



public class EntityNewMinecartRidable extends EntityNewMinecart{

    public EntityNewMinecartRidable(World worldIn,double x,double y,double z,int color)
    {
        super(worldIn,x,y,z,color);
    }

    public EntityNewMinecartRidable(World worldIn)
    {
        super(worldIn);

    }

    @Override
    public ItemStack getCartItem() {
        return ((ItemNewMinecart)EasyRails.ItemNewMinecart).genStack(1,this.color);
    }
    
    static void registerFixesMinecartEmpty(DataFixer fixer)
    {
        EntityMinecart.registerFixesMinecart(fixer, EntityMinecartEmpty.class);
    }

    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (super.processInitialInteract(player, hand)) return true;

        if (player.isSneaking())
        {
            return false;
        }
        else if (this.isBeingRidden())
        {
            return true;
        }
        else
        {
            if (!this.world.isRemote)
            {
                player.startRiding(this);
            }

            return true;
        }
    }


    public EntityMinecart.Type getType()
    {
        return EntityMinecart.Type.RIDEABLE;
    }

}