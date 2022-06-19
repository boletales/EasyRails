package com.momiza.easyrails.rails;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSwitchRailParent extends BlockRailBase {

    public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.<BlockRailBase.EnumRailDirection>create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>()
    {
        public boolean apply(BlockRailBase.EnumRailDirection p_apply_1_)
        {
            return     p_apply_1_ == BlockRailBase.EnumRailDirection.NORTH_SOUTH||
                    p_apply_1_ == BlockRailBase.EnumRailDirection.EAST_WEST ;
        }
    });
    public static final PropertyBool PLUS = PropertyBool.create("plus");
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    protected BlockSwitchRailParent()
    {
        super(false);
        setHardness(0.7f);
        setResistance(0.7f);
        setUnlocalizedName("SwitchRailParent");
        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH).withProperty(PLUS, false));
    }


    /* ======================================== FORGE START =====================================*/
    /**
     * Return true if the rail can make corners.
     * Used by placement logic.
     * @param world The world.
     * @param pos Block's position in world
     * @return True if the rail can make corners.
     */
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


    public EnumFacing getCartFacing(net.minecraft.entity.item.EntityMinecart cart){
        return EnumFacing.getFacingFromVector((float)cart.motionX, 0, (float)cart.motionZ);
    }

    public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty()
    {
        return SHAPE;
    }




    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(PLUS, (meta&1)>0).withProperty(SHAPE, bmd(meta)).withProperty(POWERED, Boolean.valueOf((meta & 4) > 0));
    }


    private BlockRailBase.EnumRailDirection bmd(int meta){
        if ((meta & 2) ==0){
            return BlockRailBase.EnumRailDirection.NORTH_SOUTH;
        }else{
            return BlockRailBase.EnumRailDirection.EAST_WEST;
        }
    }
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        
        if (((Boolean)state.getValue(PLUS)).booleanValue())
        {
            i |= 1;
        }

        if (state.getValue(SHAPE)==BlockRailBase.EnumRailDirection.EAST_WEST)
        {
            i |= 2;
        }


        if (((Boolean)state.getValue(POWERED)).booleanValue())
        {
            i |= 4;
        }


        return i;
    }
    @Override
    protected BlockStateContainer createBlockState() {
        return (BlockStateContainer)new BlockStateContainer(this, new IProperty[] {SHAPE, PLUS, POWERED});
    }
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        boolean flag = ((Boolean)state.getValue(POWERED)).booleanValue();
        boolean flag1 = worldIn.isBlockPowered(pos);

        if (flag1 != flag)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(flag1)), 3);
        }
    }
    
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(worldIn.isBlockPowered(pos))), 3);
    }
    
    public abstract EnumRailDirection getCurve(IBlockState state);
    
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
    @Override
    public EnumRailDirection getRailDirection(IBlockAccess world, BlockPos pos, IBlockState state, @javax.annotation.Nullable net.minecraft.entity.item.EntityMinecart cart)
    {
        boolean plus = state.getValue(PLUS);
        EnumRailDirection straight = state.getValue(SHAPE);
        EnumRailDirection curve = getCurve(state);
        if(cart!=null){
            EnumFacing facing=getCartFacing(cart);
            
            if(
                ( (facing==EnumFacing.SOUTH|| facing==EnumFacing.NORTH)  && straight==EnumRailDirection.EAST_WEST) ||
                ( (facing==EnumFacing.EAST || facing==EnumFacing.WEST )  && straight==EnumRailDirection.NORTH_SOUTH)
            ){
                return curve;
            }else if(
                ( facing==EnumFacing.NORTH  && straight==EnumRailDirection.NORTH_SOUTH &&  plus) ||
                ( facing==EnumFacing.SOUTH  && straight==EnumRailDirection.NORTH_SOUTH && !plus) ||
                ( facing==EnumFacing.WEST   && straight==EnumRailDirection.EAST_WEST   &&  plus) ||
                ( facing==EnumFacing.EAST   && straight==EnumRailDirection.EAST_WEST   && !plus)
            ){
                return straight;
            }else if((Boolean)state.getValue(POWERED)){
                return curve;
            }else{
                return straight;
            }
        }
        return straight;
    }


    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer.getHorizontalFacing()==EnumFacing.NORTH){
            worldIn.setBlockState(pos,  this.getDefaultState().withProperty(SHAPE, EnumRailDirection.NORTH_SOUTH).withProperty(PLUS, false).withProperty(POWERED, Boolean.valueOf(worldIn.isBlockPowered(pos))));
        }else if(placer.getHorizontalFacing()==EnumFacing.SOUTH){
            worldIn.setBlockState(pos,  this.getDefaultState().withProperty(SHAPE, EnumRailDirection.NORTH_SOUTH).withProperty(PLUS, true) .withProperty(POWERED, Boolean.valueOf(worldIn.isBlockPowered(pos))));
        }else if(placer.getHorizontalFacing()==EnumFacing.WEST){
            worldIn.setBlockState(pos,  this.getDefaultState().withProperty(SHAPE, EnumRailDirection.EAST_WEST)  .withProperty(PLUS, false).withProperty(POWERED, Boolean.valueOf(worldIn.isBlockPowered(pos))));
        }else if(placer.getHorizontalFacing()==EnumFacing.EAST){
            worldIn.setBlockState(pos,  this.getDefaultState().withProperty(SHAPE, EnumRailDirection.EAST_WEST)  .withProperty(PLUS, true) .withProperty(POWERED, Boolean.valueOf(worldIn.isBlockPowered(pos))));
        }else{
            worldIn.setBlockState(pos,  this.getDefaultState().withProperty(POWERED, Boolean.valueOf(worldIn.isBlockPowered(pos))));
        }
    }
}
