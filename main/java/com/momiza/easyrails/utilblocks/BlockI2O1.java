package com.momiza.easyrails.utilblocks;

import java.util.Random;

import com.momiza.easyrails.EasyRails;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public abstract class BlockI2O1 extends net.minecraft.block.BlockContainer{
    //EnumFacing state.getValue(IN1)=EnumFacing.NORTH;
    //EnumFacing state.getValue(IN2)=EnumFacing.SOUTH;
    //EnumFacing state.getValue(OUT)=EnumFacing.EAST;
    //int state.getValue(in) = 1;
    public static final PropertyDirection IN1 = PropertyDirection.create("in1");
    public static final PropertyDirection IN2 = PropertyDirection.create("in2");
    public static final PropertyEnum<EnumFacing> OUT = PropertyDirection.create("out");
    public static final PropertyInteger IN = PropertyInteger.create("in",0,1);

    public BlockI2O1() {
        super(Material.IRON);
        setHardness(1.0F);
        setResistance(300.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(IN1, EnumFacing.NORTH).withProperty(IN2, EnumFacing.SOUTH).withProperty(OUT, EnumFacing.EAST).withProperty(IN, 0));
        this.setTickRandomly(false);
    }


    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{IN1,IN2,OUT,IN});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 1;
        //return state.getValue(IN1).getIndex()<<8+state.getValue(IN2).getIndex()<<5+state.getValue(OUT).getIndex()<<2+state.getValue(IN);
    }

    public int test(IBlockState state){
        return state.getValue(IN1).getIndex()<<8+state.getValue(IN2).getIndex()<<5+state.getValue(OUT).getIndex()<<2+state.getValue(IN);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
        //return this.getDefaultState().withProperty(IN1, EnumFacing.values()[meta>>>8]).withProperty(IN2, EnumFacing.values()[(meta>>>5)-((meta>>>8)<<3)]).withProperty(OUT, EnumFacing.values()[(meta>>>2)-((meta>>>5)<<3)]).withProperty(IN, (meta-((meta>>>2)<<2)));
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        return false;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
        //worldIn.scheduleBlockUpdate(pos, this, 1, 1);
        worldIn.updateBlockTick(pos, this, 1, 0);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        if(side==null) return false;
        TileEntityI2O1 tile = (TileEntityI2O1)world.getTileEntity(pos);
        if(tile==null) {
            tile = (TileEntityI2O1)world.getTileEntity(pos.offset(side));
            if(tile==null) return false;
            EnumFacing side_opp = side.getOpposite();
            return tile.getIN1()==side_opp || tile.getIN2()==side_opp || tile.getOUT()==side_opp;
        }else {
            EnumFacing side_opp = side.getOpposite();
            return tile.getIN1()==side_opp || tile.getIN2()==side_opp || tile.getOUT()==side_opp;
        }
    }

    @Override
    public boolean canProvidePower(IBlockState state){
        return false;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        //System.out.println(worldIn.getBlockState(pos.offset(state.getValue(IN1))).getBlock().getWeakPower(worldIn, pos.offset(state.getValue(IN1)), state, side));
        //System.state.getValue(OUT).println(state.getValue(IN1)+","+worldIn.getStrongPower(pos.offset(state.getValue(IN1)), state.getValue(IN1).getOpposite())+","+state.getValue(IN2)+","+worldIn.getStrongPower(pos.offset(state.getValue(IN2)), state.getValue(IN2).getOpposite())+","+(((worldIn.getStrongPower(pos.offset(state.getValue(IN1)), state.getValue(IN1).getOpposite())>0 || worldIn.getStrongPower(pos.offset(state.getValue(IN2)), state.getValue(IN2).getOpposite())>0) && true) ? 15 : 0));

        EnumFacing IN1=EnumFacing.NORTH;
        EnumFacing IN2=EnumFacing.SOUTH;
        EnumFacing OUT=EnumFacing.EAST;
        TileEntityI2O1 tile =((TileEntityI2O1)blockAccess.getTileEntity(pos));
        if (tile != null){
            IN1=tile.getIN1();
            IN2=tile.getIN2();
            OUT=tile.getOUT();
        }
        //System.out.println(java.lang.Boolean.toString(tile.getSWICH())+","+IN1+","+IN2+","+((tile.getSWICH() && side.getIndex()-OUT.getIndex()==0) ? 15 : 0));
        return (tile.getSWICH() && side.getOpposite().getIndex()-OUT.getIndex()==0) ? 15 : 0;
        //return (1==worldIn.getBlockState(pos).getValue(METADATA)) ? 15 : 0;
    }


    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){

        //System.state.getValue(OUT).println(state.getValue(IN1)+","+worldIn.getStrongPower(pos.offset(state.getValue(IN1)), state.getValue(IN1).getOpposite())+","+state.getValue(IN2)+","+worldIn.getStrongPower(pos.offset(state.getValue(IN2)), state.getValue(IN2).getOpposite())+","+(((worldIn.getStrongPower(pos.offset(state.getValue(IN1)), state.getValue(IN1).getOpposite())>0 || worldIn.getStrongPower(pos.offset(state.getValue(IN2)), state.getValue(IN2).getOpposite())>0) && true) ? 15 : 0));

        EnumFacing IN1=EnumFacing.NORTH;
        EnumFacing IN2=EnumFacing.SOUTH;
        EnumFacing OUT=EnumFacing.EAST;
        TileEntityI2O1 tile =((TileEntityI2O1)blockAccess.getTileEntity(pos));
        if (tile != null){
            IN1=tile.getIN1();
            IN2=tile.getIN2();
            OUT=tile.getOUT();
        }
        //System.out.println(java.lang.Boolean.toString(tile.getSWICH())+","+IN1+","+IN2+","+((tile.getSWICH() && side.getIndex()-OUT.getIndex()==0) ? 15 : 0));
        return (tile.getSWICH() && side.getOpposite().getIndex()-OUT.getIndex()==0) ? 15 : 0;
        //return (1==worldIn.getBlockState(pos).getValue(METADATA)) ? 15 : 0;
    }
    @Override
    public int tickRate(World worldIn){
        return 1;
    }

    public static void setIN(World worldIn, BlockPos pos,IBlockState state,EnumFacing side){
        TileEntityI2O1 tile =((TileEntityI2O1)worldIn.getTileEntity(pos));
        if (tile != null){
            int _INtoggle  =tile.getIN();
            EnumFacing _IN1=tile.getIN1();
            EnumFacing _IN2=tile.getIN2();
            EnumFacing _OUT=tile.getOUT();
            int new_INtoggle  =_INtoggle;
            EnumFacing new_IN1=_IN1;
            EnumFacing new_IN2=_IN2;
            EnumFacing new_OUT=_OUT;

            if(_INtoggle==0 && side==_IN2){
                new_INtoggle=1;
            }else if(_INtoggle==1 && side==_IN1){
                new_INtoggle=0;
            }else if(_INtoggle==1 && side==_IN2){
            }else if(_INtoggle==0 && side==_IN1){
            }else if(side==_OUT){
                if(_INtoggle==0){
                    new_OUT=_IN2;
                    new_IN2=_OUT;
                    new_INtoggle=1;
                }else{
                    new_OUT=_IN1;
                    new_IN1=_OUT;
                    new_INtoggle=0;
                }
            }else{
                if(_INtoggle==0){
                    new_IN2=side;
                    new_INtoggle=1;
                }else{
                    new_IN1=side;
                    new_INtoggle=0;
                }
            }
            tile.setIN1(new_IN1);
            tile.setIN2(new_IN2);
            tile.setOUT(new_OUT);
            tile.setIN(new_INtoggle);
            tile.whenStateChange();
        }
    }

    public static void setIN1(World worldIn, BlockPos pos,IBlockState state,EnumFacing side){
        TileEntityI2O1 tile =((TileEntityI2O1)worldIn.getTileEntity(pos));
        if (tile != null){
            int _INtoggle  =tile.getIN();
            EnumFacing _IN1=tile.getIN1();
            EnumFacing _IN2=tile.getIN2();
            EnumFacing _OUT=tile.getOUT();
            int new_INtoggle  =0;
            EnumFacing new_IN1=_IN1;
            EnumFacing new_IN2=_IN2;
            EnumFacing new_OUT=_OUT;

            if(side==_OUT){
                new_OUT=_IN1;
                new_IN1=_OUT;
            }else if(side==_IN1){
            }else if(side==_IN2){
                new_IN1=_IN2;
                new_IN2=_IN1;
            }else{
                new_IN1=side;
            }
            tile.setIN1(new_IN1);
            tile.setIN2(new_IN2);
            tile.setOUT(new_OUT);
            tile.setIN(new_INtoggle);
            tile.whenStateChange();
        }
    }

    public static void setIN2(World worldIn, BlockPos pos,IBlockState state,EnumFacing side){
        TileEntityI2O1 tile =((TileEntityI2O1)worldIn.getTileEntity(pos));
        if (tile != null){
            int _INtoggle  =tile.getIN();
            EnumFacing _IN1=tile.getIN1();
            EnumFacing _IN2=tile.getIN2();
            EnumFacing _OUT=tile.getOUT();
            int new_INtoggle  =0;
            EnumFacing new_IN1=_IN1;
            EnumFacing new_IN2=_IN2;
            EnumFacing new_OUT=_OUT;

            if(side==_OUT){
                new_OUT=_IN1;
                new_IN2=_OUT;
            }else if(side==_IN1){
                new_IN2=_IN1;
                new_IN1=_IN2;
            }else if(side==_IN2){
            }else{
                new_IN2=side;
            }
            tile.setIN1(new_IN1);
            tile.setIN2(new_IN2);
            tile.setOUT(new_OUT);
            tile.setIN(new_INtoggle);
            tile.whenStateChange();
        }
    }

    public static void setOUT(World worldIn, BlockPos pos,IBlockState state,EnumFacing side){
        TileEntityI2O1 tile =((TileEntityI2O1)worldIn.getTileEntity(pos));
        if (tile != null){
            int _INtoggle  =tile.getIN();
            EnumFacing _IN1=tile.getIN1();
            EnumFacing _IN2=tile.getIN2();
            EnumFacing _OUT=tile.getOUT();
            int new_INtoggle  =_INtoggle;
            EnumFacing new_IN1=_IN1;
            EnumFacing new_IN2=_IN2;
            EnumFacing new_OUT=_OUT;

            if(side==_OUT){

            }else if(side==_IN1){
                new_OUT=_IN1;
                new_IN1=_OUT;
                new_INtoggle=0;
            }else if(side==_IN2){
                new_OUT=_IN2;
                new_IN2=_OUT;
                new_INtoggle=1;
            }else{
                new_OUT=side;
            }
            tile.setIN1(new_IN1);
            tile.setIN2(new_IN2);
            tile.setOUT(new_OUT);
            tile.setIN(new_INtoggle);
            tile.whenStateChange();
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing _facing, float hitX, float hitY, float hitZ){
        ItemStack item=playerIn.getHeldItem(hand);
        EnumFacing facing = EasyRails.INSTANCE.pipeOpposite ? _facing.getOpposite() : _facing;
        if(item!=null){
            if(item.getItem()==EasyRails.InController){
                setIN(worldIn,pos,state,facing);
                return true;
            }else if(item.getItem()==EasyRails.OutController){
                setOUT(worldIn,pos,state,facing);
                return true;
            }else/* if(item.getItem().getRegistryName().equals(new ResourceLocation(EasyRails.MOD_ID,"io_controler")))*/{
                playerIn.openGui(EasyRails.INSTANCE, EasyRails.GUI_ID_I2O1, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        EnumFacing IN1=EnumFacing.NORTH;
        EnumFacing IN2=EnumFacing.SOUTH;
        EnumFacing OUT=EnumFacing.EAST;
        TileEntityI2O1 tile =((TileEntityI2O1)worldIn.getTileEntity(pos));
        if (tile != null){
            IN1=tile.getIN1();
            IN2=tile.getIN2();
            OUT=tile.getOUT();
        }

        boolean I1=calculateInputStrength(worldIn, pos, state, IN1)>0;
        boolean I2=calculateInputStrength(worldIn, pos, state, IN2)>0;
        //boolean I1=worldIn.getRedstonePower(pos.offset(IN1), IN1.getOpposite())>0;
        //boolean I2=worldIn.getRedstonePower(pos.offset(IN2), IN2.getOpposite())>0;
        
        boolean swich=this.calc(tile,I1,I2);
        tile.setSWICH(swich);
        if(tile.getSWICHED())worldIn.notifyNeighborsOfStateChange(pos, this, true);
        worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 2);
        worldIn.updateBlockTick(pos, this, 1, 0);
    }


    protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        EnumFacing enumfacing = side;
        BlockPos blockpos = pos.offset(enumfacing);
        int i = worldIn.getRedstonePower(blockpos, enumfacing);

        if (i >= 15)
        {
            return i;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            return Math.max(i, iblockstate.getBlock() == Blocks.REDSTONE_WIRE ? ((Integer)iblockstate.getValue(BlockRedstoneWire.POWER)).intValue() : 0);
        }
    }
    
    public abstract boolean calc(TileEntityI2O1 tile,boolean I1,boolean I2);
    
    @Override
    public boolean isTranslucent(IBlockState state)
    {
        return true;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }
    
}