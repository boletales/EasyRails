package com.momiza.easyrails.utilblocks;

import com.momiza.easyrails.EasyRails;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;

public class TileEntityAutoLumber extends TileEntity implements ITickable {
	public final int lumberingSize = 3;
	/** 伐採のサイズ指定 0:1*1 1:3*3 2:5*5 and more... */
	public final int lumberinHeight = 35;
	/** 伐採の高さ指定 */

	public final int plantingSize = 1;
	/** 植林のサイズ指定 0:1*1 1:3*3 2:5*5 and more... */


	@Override
	public NBTTagCompound serializeNBT() {
        NBTTagCompound ret = new NBTTagCompound();
        this.writeToNBT(ret);
        return ret;
	}


	private boolean isPlantingBlock(ItemStack itemIn) {
		if (itemIn.isEmpty() || itemIn.getCount() <= 0) return false;
		for (int i = 0; i < EasyRails.plantingBlocks.size(); i++) {
			Block block = EasyRails.plantingBlocks.get(i);
			if (block instanceof IPlantable && itemIn.getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		return false;
	}

	public void update() {
		if(!this.getWorld().isRemote)lumber();
	}

	public void lumber(){
		serch_lumber: {

			for (int y = this.getPos().getY() + 1; y <= this.getPos().getY() + this.lumberinHeight; y++) {
				for (int x = this.getPos().getX() - lumberingSize; x <= this.getPos().getX()+ this.lumberingSize; x++) {
					for (int z = this.getPos().getZ() - lumberingSize; z <= this.getPos().getZ()+ this.lumberingSize; z++) {
						IBlockState state=this.getWorld().getBlockState(new BlockPos(x,y,z));
						if(!EasyRails.lumberingBlocks.contains(state.getBlock()))continue;
						NonNullList<ItemStack> drops = NonNullList.create();
						state.getBlock().getDrops(drops, this.getWorld(),new BlockPos(x, y, z),state, 0);
						this.getWorld().destroyBlock(new BlockPos(x, y, z), false);
						for (int i = 0; i < drops.size(); i++) {
							if (isPlantingBlock(drops.get(i)))drops.set(i, plant(drops.get(i)));

							// if(!this.worldObj.isRemote)this.getWorld().spawnEntityInWorld(new
							// EntityItem(this.getWorld(),
							// this.getPos().getX(),
							// this.getPos().getY()+1.5,
							// this.getPos().getZ(), drops.get(i)));
							drop(drops.get(i));
						}
						break serch_lumber;
					}
				}
			}
		}
	}

	public void drop(ItemStack itemstack) {
		if (itemstack != null)
			putting: {
				for (int i = 0; i < EnumFacing.VALUES.length; i++) {
					BlockPos serchingPos = this.getPos().offset(EnumFacing.VALUES[i]);
					TileEntity tile = this.getWorld().getTileEntity(serchingPos);
					if (tile instanceof IInventory) {
						Boolean flag = false;
						IInventory inv = (IInventory) tile;
						for (int index = 0; index < inv.getSizeInventory(); index++) {
							ItemStack invstack = inv.getStackInSlot(index);
							if (invstack.isEmpty()) {
								invstack = itemstack.copy();
								itemstack = null;
								flag = flag || true;
							} else if (invstack.getItem()==itemstack.getItem() && invstack.getItemDamage()==itemstack.getItemDamage() && invstack.isStackable()
									&& invstack.getCount() < invstack.getMaxStackSize()) {
								if (invstack.getCount() + itemstack.getCount() <= invstack.getMaxStackSize()) {
									invstack.setCount(invstack.getCount()+itemstack.getCount());
									itemstack = null;
									flag = flag || true;
								} else {
									itemstack.setCount(invstack.getMaxStackSize() - invstack.getCount());
									invstack.setCount(invstack.getMaxStackSize());
								}
							}
							inv.setInventorySlotContents(index, invstack);
							if (flag)
								inv.markDirty();
							if (itemstack == null)
								break putting;
						}
					}
				}

				if (!this.world.isRemote)
					this.getWorld().spawnEntity(new EntityItem(this.getWorld(), this.getPos().getX(),
							this.getPos().getY() + 1.5, this.getPos().getZ(), itemstack));
			}
	}

	public ItemStack plant(ItemStack plant) {
		if (isPlantingBlock(plant) && !this.getWorld().isRemote) {
			for (int x = this.getPos().getX() - plantingSize; x <= this.getPos().getX() + this.plantingSize; x++) {
				int y = this.getPos().getY() + 1;
				for (int z = this.getPos().getZ() - plantingSize; z <= this.getPos().getZ() + this.plantingSize; z++) {
					BlockPos pos_plant=new BlockPos(x,y,z);
					BlockPos pos_dirt=new BlockPos(x,y-1,z);
					if (!plant.isEmpty() && plant.getCount() > 0 && this.getWorld().isAirBlock(pos_plant) && this.isPlantingBlock(plant)
							&& this.getWorld().getBlockState(pos_dirt).getBlock().canSustainPlant(this.getWorld().getBlockState(pos_dirt), this.getWorld(), pos_dirt,EnumFacing.UP, (IPlantable) ((ItemBlock) plant.getItem()).getBlock())) {
						this.getWorld().setBlockState(new BlockPos(x, y, z), ((ItemBlock) plant.getItem()).getBlock().getStateFromMeta(((ItemBlock) plant.getItem()).getMetadata(plant)));
						plant.setCount(plant.getCount()-1);
					}
				}
			}
		}
		return plant;
	}

	public void grow(){
		if (!this.getWorld().isRemote) serch_growing: {
			for (int x = this.getPos().getX() - lumberingSize; x <= this.getPos().getX() + this.lumberingSize; x++){
					int y = this.getPos().getY() + 1;
					for (int z = this.getPos().getZ() - lumberingSize; z <= this.getPos().getZ()+ this.lumberingSize; z++) {
						IBlockState state=this.getWorld().getBlockState(new BlockPos(x, y, z));
						if (state.getBlock() instanceof IGrowable) {
							if (((IGrowable) state.getBlock()).canUseBonemeal(this.getWorld(), this.getWorld().rand, new BlockPos(x, y, z),state)) {
								((IGrowable)state.getBlock()).grow(this.getWorld(),this.getWorld().rand, new BlockPos(x,y,z),state);
								break serch_growing;
							}
						}
					}
				}
		}
	}
}