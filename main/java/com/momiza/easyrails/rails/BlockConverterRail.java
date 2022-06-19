package com.momiza.easyrails.rails;

import com.google.common.base.Predicate;
import com.momiza.easyrails.EasyRails;
import com.momiza.easyrails.minecarts.EntityNewMinecart;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockConverterRail extends BlockRailBase implements ISlowdownRail{

    public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.<BlockRailBase.EnumRailDirection>create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>()
    {
        public boolean apply(BlockRailBase.EnumRailDirection p_apply_1_)
        {
            return     p_apply_1_ == BlockRailBase.EnumRailDirection.NORTH_SOUTH ||
                    p_apply_1_ == BlockRailBase.EnumRailDirection.EAST_WEST ;
        }
    });
    public static final PropertyInteger COLOR = PropertyInteger.create("color",0,15);

    public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty()
    {
        return SHAPE;
    }
    public BlockConverterRail()
    {
        super(false);
        setUnlocalizedName("ConverterRail");
        setHardness(0.7f);
        setResistance(0.7f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
    }

    public void onMinecartPass(World world, net.minecraft.entity.item.EntityMinecart cart, BlockPos pos)
    {
        if(cart instanceof EntityNewMinecart){
            if(world.isBlockPowered(pos)){
                ((EntityNewMinecart) cart).color=world.getBlockState(pos).getValue(COLOR);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        playerIn.openGui(EasyRails.INSTANCE, EasyRails.GUI_ID_CONVERTERRAIL, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
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
        return false;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOR, meta).withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOR);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {COLOR,SHAPE});
    }
}
