package com.momiza.easyrails.rails;

import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import com.momiza.easyrails.EasyRails;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNewDetector extends BlockRailBase {

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote)
        {
            boolean tmp=false;
            boolean plus=state.getValue(PLUS);
            boolean isX=state.getValue(SHAPE)==EnumRailDirection.EAST_WEST;
            if(isX) {
                if(entityIn.motionX>0 && plus){
                    tmp=true;
                }else if(entityIn.motionX<0 && !plus){
                    tmp=true;
                }
            }else {
                if(entityIn.motionZ>0 && plus){
                    tmp=true;
                }else if(entityIn.motionZ<0 && !plus){
                    tmp=true;
                }
            }

            if (!((Boolean)state.getValue(POWERED)).booleanValue() && tmp)
            {
                this.updatePoweredState(worldIn, pos, state);
            }
        }
    }
    private void updatePoweredState(World worldIn, BlockPos pos, IBlockState state)
    {
        boolean flag = ((Boolean)state.getValue(POWERED)).booleanValue();
        boolean flag1 = false;
        List<EntityMinecart> list = this.<EntityMinecart>findMinecarts(worldIn, pos, EntityMinecart.class);

        if (!list.isEmpty())
        {
            flag1 = true;
        }

        if (flag1 && !flag)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)), 3);
            this.updateConnectedRails(worldIn, pos, state, true);
            worldIn.notifyNeighborsOfStateChange(pos, this, false);
            worldIn.notifyNeighborsOfStateChange(pos.down(), this, false);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
        }

        if (!flag1 && flag)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)), 3);
            this.updateConnectedRails(worldIn, pos, state, false);
            worldIn.notifyNeighborsOfStateChange(pos, this, false);
            worldIn.notifyNeighborsOfStateChange(pos.down(), this, false);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
        }

        if (flag1)
        {
            worldIn.scheduleUpdate(new BlockPos(pos), this, this.tickRate(worldIn));
        }

        worldIn.updateComparatorOutputLevel(pos, this);
    }

    protected void updateConnectedRails(World worldIn, BlockPos pos, IBlockState state, boolean powered)
    {
        BlockRailBase.Rail blockrailbase$rail = new BlockRailBase.Rail(worldIn, pos, state);

        for (BlockPos blockpos : blockrailbase$rail.getConnectedRails())
        {
            IBlockState iblockstate = worldIn.getBlockState(blockpos);

            if (iblockstate != null)
            {
                iblockstate.neighborChanged(worldIn, blockpos, iblockstate.getBlock(), pos);
            }
        }
    }


    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote && ((Boolean)state.getValue(POWERED)).booleanValue())
        {
            this.updatePoweredState(worldIn, pos, state);
        }
    }
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,EnumHand hand,EnumFacing side, float hitX, float hitY, float hitZ) {
        if((playerIn.isSneaking())||(playerIn.getHeldItem(hand)!=null && (playerIn.getHeldItem(hand).getItem()==EasyRails.InController||playerIn.getHeldItem(hand).getItem()==EasyRails.OutController))){
            worldIn.setBlockState(pos, state.withProperty(PLUS, !state.getValue(PLUS)));
            return true;
        }
        return false;
    }
    public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.<BlockRailBase.EnumRailDirection>create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>()
    {
        public boolean apply(BlockRailBase.EnumRailDirection p_apply_1_)
        {
            return p_apply_1_ == BlockRailBase.EnumRailDirection.NORTH_SOUTH || p_apply_1_ == BlockRailBase.EnumRailDirection.EAST_WEST;
        }
    });
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool PLUS = PropertyBool.create("plus");

    public BlockNewDetector() {
        super(false);
        setHardness(0.7f);
        setResistance(0.7f);
        setUnlocalizedName("NewDetectorRail");
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta & 3)).withProperty(POWERED, Boolean.valueOf((meta & 4) > 0)).withProperty(PLUS, Boolean.valueOf((meta & 8) > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE)).getMetadata();

        if (((Boolean)state.getValue(POWERED)).booleanValue())
        {
            i |= 4;
        }

        if (((Boolean)state.getValue(PLUS)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {SHAPE, POWERED,PLUS});
    }


    public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty()
    {
        return SHAPE;
    }

    protected <T extends EntityMinecart> List<T> findMinecarts(World worldIn, BlockPos pos, Class<T> clazz, Predicate<Entity>... filter)
    {
        AxisAlignedBB axisalignedbb = this.getDectectionBox(pos);
        return filter.length != 1 ? worldIn.getEntitiesWithinAABB(clazz, axisalignedbb) : worldIn.getEntitiesWithinAABB(clazz, axisalignedbb, filter[0]);
    }
    private AxisAlignedBB getDectectionBox(BlockPos pos)
    {
        float f = 0.2F;
        return new AxisAlignedBB((double)((float)pos.getX() + 0.2F), (double)pos.getY(), (double)((float)pos.getZ() + 0.2F), (double)((float)(pos.getX() + 1) - 0.2F), (double)((float)(pos.getY() + 1) - 0.2F), (double)((float)(pos.getZ() + 1) - 0.2F));
    }
    
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return ((Boolean)blockState.getValue(POWERED)).booleanValue() ? 15 : 0;
    }
    
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (!((Boolean)blockState.getValue(POWERED)).booleanValue())
        {
            return 0;
        }
        else
        {
            return side == EnumFacing.UP ? 15 : 0;
        }
    }
}
