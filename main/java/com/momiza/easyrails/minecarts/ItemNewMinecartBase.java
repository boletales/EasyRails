package com.momiza.easyrails.minecarts;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemNewMinecartBase extends Item {
    private static final IBehaviorDispenseItem MINECART_DISPENSER_BEHAVIOR = new BehaviorDefaultDispenseItem()
    {
        private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
        {
            EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING);
            World world = source.getWorld();
            double d0 = source.getX() + (double)enumfacing.getFrontOffsetX() * 1.125D;
            double d1 = Math.floor(source.getY()) + (double)enumfacing.getFrontOffsetY();
            double d2 = source.getZ() + (double)enumfacing.getFrontOffsetZ() * 1.125D;
            BlockPos blockpos = source.getBlockPos().offset(enumfacing);
            IBlockState iblockstate = world.getBlockState(blockpos);
            BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.getBlock() instanceof BlockRailBase ? ((BlockRailBase)iblockstate.getBlock()).getRailDirection(world, blockpos, iblockstate, null) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            double d3;

            if (BlockRailBase.isRailBlock(iblockstate))
            {
                if (blockrailbase$enumraildirection.isAscending())
                {
                    d3 = 0.6D;
                }
                else
                {
                    d3 = 0.1D;
                }
            }
            else
            {
                if (iblockstate.getMaterial() != Material.AIR || !BlockRailBase.isRailBlock(world.getBlockState(blockpos.down())))
                {
                    return this.behaviourDefaultDispenseItem.dispense(source, stack);
                }

                IBlockState iblockstate1 = world.getBlockState(blockpos.down());
                BlockRailBase.EnumRailDirection blockrailbase$enumraildirection1 = iblockstate1.getBlock() instanceof BlockRailBase ? ((BlockRailBase)iblockstate1.getBlock()).getRailDirection(world, blockpos.down(), iblockstate1, null) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;

                if (enumfacing != EnumFacing.DOWN && blockrailbase$enumraildirection1.isAscending())
                {
                    d3 = -0.4D;
                }
                else
                {
                    d3 = -0.9D;
                }
            }

            EntityNewMinecart entityminecart = ((ItemNewMinecartBase)stack.getItem()).genCart(world, d0, d1 + d3, d2, stack.getMetadata());

            if (stack.hasDisplayName())
            {
                entityminecart.setCustomNameTag(stack.getDisplayName());
            }

            world.spawnEntity(entityminecart);
            stack.shrink(1);
            return stack;
        }
        /**
         * Play the dispense sound from the specified block.
         */
        protected void playDispenseSound(IBlockSource source)
        {
            source.getWorld().playEvent(1000, source.getBlockPos(), 0);
        }
    };

    public ItemNewMinecartBase()
    {
        super();
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, MINECART_DISPENSER_BEHAVIOR);
    }
	@Override
	public void getSubItems(CreativeTabs tab,NonNullList<ItemStack> subItems){
        if (this.isInCreativeTab(tab))
        {
    		subItems.add(this.genStack(1, 0 ));
    		subItems.add(this.genStack(1, 1 ));
    		subItems.add(this.genStack(1, 2 ));
    		subItems.add(this.genStack(1, 3 ));
    		subItems.add(this.genStack(1, 4 ));
    		subItems.add(this.genStack(1, 5 ));
    		subItems.add(this.genStack(1, 6 ));
    		subItems.add(this.genStack(1, 7 ));
    		subItems.add(this.genStack(1, 8 ));
    		subItems.add(this.genStack(1, 9 ));
    		subItems.add(this.genStack(1, 10));
    		subItems.add(this.genStack(1, 11));
    		subItems.add(this.genStack(1, 12));
    		subItems.add(this.genStack(1, 13));
    		subItems.add(this.genStack(1, 14));
    		subItems.add(this.genStack(1, 15));
        }
	}
	public ItemStack genStack(int amount,int color){
		return new ItemStack(this, amount, color).setStackDisplayName("§"+Integer.toHexString(color)+"■■§r NewMinecart "+color+"§"+Integer.toHexString(color)+"■■");
	}

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (!BlockRailBase.isRailBlock(iblockstate))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            ItemStack itemstack = player.getHeldItem(hand);

            if (!worldIn.isRemote)
            {
                BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.getBlock() instanceof BlockRailBase ? ((BlockRailBase)iblockstate.getBlock()).getRailDirection(worldIn, pos, iblockstate, null) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
                double d0 = 0.0D;

                if (blockrailbase$enumraildirection.isAscending())
                {
                    d0 = 0.5D;
                }

                EntityNewMinecart entityminecart = genCart(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.0625D + d0, (double)pos.getZ() + 0.5D, itemstack.getMetadata());

                if (itemstack.hasDisplayName())
                {
                    entityminecart.setCustomNameTag(itemstack.getDisplayName());
                }

                worldIn.spawnEntity(entityminecart);
            }

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
    }
    public EntityNewMinecart genCart(World worldIn,BlockPos blockpos,int meta) {
    	return genCart(worldIn, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.0625D , (double)blockpos.getZ() + 0.5D ,meta);
    }
    public abstract EntityNewMinecart genCart(World worldIn,double x,double y,double z,int meta);
}
