package com.momiza.easyrails.rails;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrossRail extends BlockRailBase {

    public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.<BlockRailBase.EnumRailDirection>create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>()
    {
        public boolean apply(BlockRailBase.EnumRailDirection p_apply_1_)
        {
            return     p_apply_1_ == BlockRailBase.EnumRailDirection.NORTH_SOUTH ||
                    p_apply_1_ == BlockRailBase.EnumRailDirection.EAST_WEST ;
        }
    });

    public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty()
    {
        return SHAPE;
    }
    public BlockCrossRail()
    {
        super(false);
        setHardness(0.7f);
        setResistance(0.7f);
        setUnlocalizedName("CrossRail");
        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
    }
    public boolean isFlexibleRail(IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    /**
     * Returns true if the rail can make up and down slopes.
     * Used by placement logic.
     * @param world The world.
     * @param pos Block's position in world
     * @return True if the rail can make slopes.
     */
    public boolean canMakeSlopes(IBlockAccess world, BlockPos pos)
    {
        return false;
    }


    /**
     * Return the rail's direction.
     * Can be used to make the cart think the rail is a different shape,
     * for example when making diamond junctions or switches.
     * The cart parameter will often be null unless it it called from EntityMinecart.
     *
     * @param world The world.
     * @param pos Block's position in world
     * @param state The BlockState
     * @param cart The cart asking for the metadata, null if it is not called by EntityMinecart.
     * @return The direction.
     */
    public EnumRailDirection getRailDirection(IBlockAccess world, BlockPos pos, IBlockState state, @javax.annotation.Nullable net.minecraft.entity.item.EntityMinecart cart)
    {
        if(cart!=null && (cart.getHorizontalFacing()==EnumFacing.EAST || cart.getHorizontalFacing()==EnumFacing.WEST)){
            return EnumRailDirection.NORTH_SOUTH;
        }else{
            return EnumRailDirection.EAST_WEST;
        }
    }

    /**
     * Rotate the block. For vanilla blocks this rotates around the axis passed in (generally, it should be the "face" that was hit).
     * Note: for mod blocks, this is up to the block and modder to decide. It is not mandated that it be a rotation around the
     * face, but could be a rotation to orient *to* that face, or a visiting of possible rotations.
     * The method should return true if the rotation was successful though.
     *
     * @param world The world
     * @param pos Block position in world
     * @param axis The axis to rotate around
     * @return True if the rotation was successful, False if the rotation failed, or is not possible
     */
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
    {
        /*IBlockState state = world.getBlockState(pos);
        for (IProperty prop : (java.util.Set<IProperty>)state.getProperties().keySet())
        {
            if (prop.getName().equals("shape"))
            {
                //world.setBlockState(pos, state.cycleProperty(prop));
                //return false;
            }
        }*/
        return false;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {SHAPE});
    }
}
