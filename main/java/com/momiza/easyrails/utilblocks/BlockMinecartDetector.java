package com.momiza.easyrails.utilblocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.momiza.easyrails.EasyRails;
import com.momiza.easyrails.minecarts.EntityNewMinecart;
import com.momiza.easyrails.minecarts.EntityNewMinecartChest;
import com.momiza.easyrails.minecarts.EntityNewMinecartRidable;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

//レシーバー
public class BlockMinecartDetector extends net.minecraft.block.BlockContainer {
    public static final PropertyInteger METADATA = PropertyInteger.create("output",0,1);
    public BlockMinecartDetector() {
        super(new Material(MapColor.IRON));
        setCreativeTab(CreativeTabs.REDSTONE);
        setUnlocalizedName("MinecartDetector");
        this.setTickRandomly(false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
    }

    @Override
    public boolean requiresUpdates(){
        return true;
    }
    public int getMetaFromState(IBlockState state) {
        return (Integer)state.getValue(METADATA);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(METADATA, meta);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{METADATA});
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
        //worldIn.scheduleBlockUpdate(pos, this, 1, 1);
        worldIn.updateBlockTick(pos, this, 1, 0);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side){
        return side!=null;
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return false;
    }
    
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return detect(blockAccess,pos) ? 15 : 0;
        //return (1==worldIn.getBlockState(pos).getValue(METADATA)) ? 15 : 0;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return detect(blockAccess,pos) ? 15 : 0;
        //return (1==worldIn.getBlockState(pos).getValue(METADATA)) ? 15 : 0;
    }

    protected boolean detect(IBlockAccess worldIn, BlockPos pos){
        TileEntityMinecartDetector tile=(TileEntityMinecartDetector) worldIn.getTileEntity(pos);
        List<Entity> loaded=((World)worldIn).loadedEntityList;
        for(Entity e : loaded){
            if(((tile.detectChest && e instanceof EntityNewMinecartChest)||(tile.detectRidable && e instanceof EntityNewMinecartRidable))){
                if(tile.detect[((EntityNewMinecart)e).color] && e.getPosition().down().equals(pos)){
                    return true;
                }
            }else if((!tile.detectChest && e instanceof EntityNewMinecartChest)||(!tile.detectRidable && e instanceof EntityNewMinecartRidable)) {
                if(e.getPosition().down().equals(pos)){
                    return tile.defaultOut;
                }
            }
        }
        return false;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
        worldIn.updateBlockTick(pos, this, 1, 0);
        int detectResult = detect(worldIn,pos)?1:0;
        if(worldIn.getBlockState(pos).getValue(METADATA) != detectResult){
            NBTTagCompound nbt=new NBTTagCompound();
            worldIn.getTileEntity(pos).writeToNBT(nbt);
            worldIn.setBlockState(pos, getStateFromMeta(detectResult));
            worldIn.getTileEntity(pos).readFromNBT(nbt);
            worldIn.notifyNeighborsOfStateChange(pos, this, true);
        }
    }

    @Override
    public int tickRate(World worldIn){
        return 1;
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        playerIn.openGui(EasyRails.INSTANCE, EasyRails.GUI_ID_DETECTOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMinecartDetector();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}