package com.momiza.easyrails.minecarts;

import com.momiza.easyrails.EasyRails;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;


public class EntityNewMinecartChest extends EntityNewMinecartContainer
{
	//public static Item newMinecartChest = new ItemNewMinecartChest(EnumMinecart.Type.CHEST);
    /**
     * When set to true, the minecart will drop all items when setDead() is called. When false (such as when travelling
     * dimensions) it preserves its contents.
     */
    public boolean dropContentsWhenDead = true;
    

	public EntityNewMinecartChest(World worldIn,double x,double y,double z,int color)
	{
		super(worldIn,x,y,z,color);
	}

	public EntityNewMinecartChest(World worldIn)
	{
		super(worldIn);

	}
	
    public static void registerFixesMinecartChest(DataFixer fixer)
    {
        EntityMinecartContainer.addDataFixers(fixer, EntityMinecartChest.class);
    }
    
    @Override
    public ItemStack getCartItem() {
    	return ((ItemNewMinecartChest)EasyRails.ItemNewMinecartChest).genStack(1,this.color);
    }
        
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 54;
    }

    public EntityMinecart.Type getType()
    {
        return EntityMinecart.Type.CHEST;
    }

    public IBlockState getDefaultDisplayTile()
    {
        return Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.NORTH);
    }

    public int getDefaultDisplayTileOffset()
    {
        return 8;
    }

    public String getGuiID()
    {
        return "minecraft:large_chest";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.addLoot(playerIn);
        return new ContainerChest(playerInventory, this, playerIn);
    }

    @Override
	public String getShortName(){
		return "§"+Integer.toHexString(this.color)+"□□";
	}
}