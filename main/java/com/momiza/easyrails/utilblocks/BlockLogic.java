package com.momiza.easyrails.utilblocks;

import com.momiza.easyrails.EasyRails;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BlockLogic extends BlockI2O1{
    public BlockLogic() {
        super();
        setCreativeTab(CreativeTabs.REDSTONE);
        setSoundType(SoundType.METAL);
        setUnlocalizedName("LogicBlock");
        setHardness(1.0F);
        setResistance(300.0F);
        setLightOpacity(0);
        setLightLevel(0.0F);
    }
    
    @Override
    public boolean calc(TileEntityI2O1 tile, boolean I1, boolean I2) {
        boolean[][] logic = ((TileEntityLogic)tile).getLOGIC();
        return logic[I1?1:0][I2?1:0];
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityLogic();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        ItemStack item=playerIn.getHeldItem(hand);
        if(item!=null){
            if(item.getItem().getRegistryName().equals(new ResourceLocation(EasyRails.MOD_ID,"in_controler"))){
                setIN(worldIn,pos,state,facing);
                return true;
            }else if(item.getItem().getRegistryName().equals(new ResourceLocation(EasyRails.MOD_ID,"out_controler"))){
                setOUT(worldIn,pos,state,facing);
                return true;
            }else/* if(item.getItem().getRegistryName().equals(new ResourceLocation(EasyRails.MOD_ID,"io_controler")))*/{
                playerIn.openGui(EasyRails.INSTANCE, EasyRails.GUI_ID_LOGIC, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }
}