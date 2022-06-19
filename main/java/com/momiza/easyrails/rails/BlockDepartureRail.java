package com.momiza.easyrails.rails;

import com.google.common.base.Predicate;
import com.momiza.easyrails.EasyRails;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDepartureRail extends BlockRailBase implements ISlowdownRail{
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool PLUS = PropertyBool.create("plus");

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand,EnumFacing side, float hitX, float hitY, float hitZ) {
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

    protected void updateState(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        boolean flag = ((Boolean)state.getValue(POWERED)).booleanValue();
        boolean flag1 = worldIn.isBlockPowered(pos);

        if (flag1 != flag)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(flag1)), 3);
            worldIn.notifyNeighborsOfStateChange(pos.down(), this, false);

            if (((BlockRailBase.EnumRailDirection)state.getValue(SHAPE)).isAscending())
            {
                worldIn.notifyNeighborsOfStateChange(pos.up(), this, false);
            }
        }
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        updateState(worldIn.getBlockState(pos), worldIn, pos, this);
    }

    public BlockDepartureRail() {
        super(false);
        setHardness(0.7f);
        setResistance(0.7f);
        setUnlocalizedName("DepartureRail");
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
}
