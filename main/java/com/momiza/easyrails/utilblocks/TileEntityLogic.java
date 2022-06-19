package com.momiza.easyrails.utilblocks;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityLogic extends TileEntityI2O1{
    boolean[][] LOGIC= {{true,true},{true,true}};

    public void readFromNBT(NBTTagCompound par1NBTTagCompound){
        super.readFromNBT(par1NBTTagCompound);
        this.LOGIC = byteToLogic((byte)par1NBTTagCompound.getInteger("logic"));
    }

    
    public boolean[][] getLOGIC()
    {
        return this.LOGIC;
    }

    public void setLOGIC(boolean[][] LOGIC)
    {
        this.LOGIC = LOGIC;
    }

    public void setLOGIC(int LOGIC)
    {
        this.LOGIC = byteToLogic((byte)LOGIC);
    }

    
    /*
     * こちらはNBTを書き込むメソッド。
     */
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound){
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("logic", logicToByte(this.LOGIC));
        return par1NBTTagCompound;
    }
    
    public static boolean[][] byteToLogic(byte n){
        boolean[][] logic = {{(n&1)>0, (n&2)>0}, {(n&4)>0, (n&8)>0}, };
        return logic;
    }
    
    public static byte logicToByte(boolean[][] logic) {
        return (byte)((logic[0][0]?1:0)|(logic[0][1]?2:0)|(logic[1][0]?4:0)|(logic[1][1]?8:0));
    }
}
