package com.momiza.easyrails.utilitems;



import java.util.Arrays;

import com.momiza.easyrails.EasyRails;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import scala.Console;
public class ItemRailWand extends Item {
	int length=2;
	boolean place=false;
	BlockPos _pos=new BlockPos(0,0,0);
	public ItemRailWand() {
        super();
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
    	if(!worldIn.isRemote){
    		if(EasyRails.rw_enabled){
	        	place=true;
	        	BlockPos pos=playerIn.getPosition();
	        	Console.println(pos);

	    		int 	length		=EasyRails.rw_length;
	    		int 	dy			=EasyRails.rw_dy;
	    		boolean isMultiTrack=EasyRails.rw_isMultiTrack;
	    		boolean isTunnel	=EasyRails.rw_isTunnel;

	        	IBlockState A=Blocks.AIR.getDefaultState();
	        	IBlockState B=EasyRails.rw_block;
	        	IBlockState L=EasyRails.rw_lblock;
	        	IBlockState R=Blocks.RAIL.getDefaultState();

	        	IBlockState[][] mtdata={
	            	{A,A,A,A,A}, //i=3
	        		{A,A,A,A,A}, //i=2
	        		{A,R,A,R,A}, //i=1
	        		{B,B,L,B,B}  //i=0
	        	//j= 4 3 2 1 0
	        	};

	        	IBlockState[][] stdata={
	                	{A,A,A}, //i=3
	            		{A,A,A}, //i=2
	            		{A,R,A}, //i=1
	            		{B,L,B}  //i=0
	            	//j= 2 1 0
	            	};

	        	int iDiffFromDepth=dy;
	        	int jDiffFromDepth=0;
	        	int playerPosI=1;
	        	int playerPosJ=1;
	        	IBlockState[][] data=stdata.clone();
	        	if(isMultiTrack){
	        		data=mtdata.clone();
	            	playerPosI=1;
	            	playerPosJ=2;
	        	}
	        	if(dy!=0){
	        		IBlockState[][] air=new IBlockState[2][data[0].length];
	        		IBlockState[][] con = new IBlockState[data.length+air.length][data[0].length];
	        		for(int i=0;i<air.length;i++) Arrays.fill(air[i],A);
	        		System.arraycopy(air, 0, con, 0, air.length);
	        		System.arraycopy(data, 0, con, air.length, data.length);
	        		data=con.clone();
	        		Console.println(Arrays.toString(air));
	        	}
	        	if(isTunnel){
	        		data=tunnelize(data);
	        		playerPosJ+=1;
	        	}
	        	for(int depth=0;depth<length;depth++){
	            	place(data,playerPosI,playerPosJ,iDiffFromDepth,jDiffFromDepth,pos,playerIn.getHorizontalFacing(),depth,worldIn);
	        	}
	    	}else{
	    		playerIn.sendMessage(new TextComponentString("The RailWand is disabled!\nTo use it,type \"/rw enabled true\""));
	    	}
    	}
    	return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    protected IBlockState[][] tunnelize(IBlockState[][] data){
    	IBlockState T=EasyRails.rw_tblock;

    	IBlockState[][] _data=data.clone();
    	IBlockState[][] newdata=new IBlockState[_data.length+1][_data[0].length+2];
    	for(int i=0;i<newdata.length;i++){
    		for(int j=0;j<newdata[i].length;j++){
    			if(i==0){
    				newdata[i][j]=T;
    			}else if(j==0 || j==newdata[i].length-1){
    				newdata[i][j]=T;
    			}else{
    				newdata[i][j]=_data[i-1][j-1];
    			}
    		}
    	}
    	return newdata;
    }

    protected void placeMultiRail(){
    	//共通部分までレシピ?的な
    }

    protected void place(IBlockState[][] data,int playerPosI,int playerPosJ,int iDiffFromDepth,int jDiffFromDepth,BlockPos pos,EnumFacing facing,int depth,World world){


    	//ここから共通
    	int jtox=facing.getDirectionVec().getZ();
    	int jtoz=-facing.getDirectionVec().getX();
    	for(int i=0  ;  i<data.length ;  i++){
    		for(int j=0  ;  j<data[i].length  ;  j++){
    			int _i=data.length-1-playerPosI-i+iDiffFromDepth*depth;
    			int _j=data[i].length-1-playerPosJ-j+jDiffFromDepth*depth;
    			BlockPos placingPos=pos.add(jtox*_j, _i, jtoz*_j).add(facing.getDirectionVec().getX()*depth,0,facing.getDirectionVec().getZ()*depth);
    			if(!(world.getBlockState(placingPos).getBlock() instanceof net.minecraft.block.BlockRailBase)){
        			//Console.println("i:"+i+",j:"+j);
    				world.setBlockState(placingPos, data[i][j]);
    			}
        	}
    	}
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack){
    	/*if(!place){
    		if(length<6){
    			length+=1;
    		}else{
    			length=2;
    		}
    	}
        stack.setStackDisplayName("RailWand length:"+length);
    	place=false;*/
    	return false;
    }


}