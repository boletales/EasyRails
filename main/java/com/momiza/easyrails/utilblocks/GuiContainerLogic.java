package com.momiza.easyrails.utilblocks;

import com.momiza.easyrails.EasyRails;
import com.momiza.easyrails.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
public class GuiContainerLogic extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EasyRails.MOD_ID,"textures/gui/gui.png");
    private TileEntityLogic tile;
    public GuiContainerLogic(Container inventorySlotsIn,TileEntity t) {
        super(inventorySlotsIn);
        tile=(TileEntityLogic)t;
    }



    @Override
    public void initGui() {
        super.initGui();
        for(EnumFacing f:EnumFacing.values()){
            this.buttonList.add(new GuiButton(f.getIndex(), f.getIndex()*50+70, 40, 40, 20,f.toString()));
        }
        for(EnumFacing f:EnumFacing.values()){
            this.buttonList.add(new GuiButton(f.getIndex()+6, f.getIndex()*50+70, 100, 40, 20,f.toString()));
        }
        for(EnumFacing f:EnumFacing.values()){
            this.buttonList.add(new GuiButton(f.getIndex()+12, f.getIndex()*50+70, 160, 40, 20,f.toString()));
        }
        this.buttonList.add(new GuiButton(18, 150, 190, 40, 20,""));
        this.buttonList.add(new GuiButton(19, 190, 190, 40, 20,""));
        this.buttonList.add(new GuiButton(20, 150, 210, 40, 20,""));
        this.buttonList.add(new GuiButton(21, 190, 210, 40, 20,""));
        refreshButton();
    }
    
    private String boolToStr(boolean bool) {
        return bool?"§a ✓":"§c ✘";
    }
    
    private void refreshButton(){
        for(GuiButton b:this.buttonList){
            b.enabled=true;
        }
        this.buttonList.get(tile.IN1.getIndex()).enabled=false;
        this.buttonList.get(tile.IN2.getIndex()+6).enabled=false;
        this.buttonList.get(tile.OUT.getIndex()+12).enabled=false;
        this.buttonList.get(18+0).displayString = "1-,2- "+boolToStr(tile.LOGIC[0][0]);
        this.buttonList.get(18+1).displayString = "1-,2+ "+boolToStr(tile.LOGIC[0][1]);
        this.buttonList.get(18+2).displayString = "1+,2- "+boolToStr(tile.LOGIC[1][0]);
        this.buttonList.get(18+3).displayString = "1+,2+ "+boolToStr(tile.LOGIC[1][1]);
    }

    public void actionPerformed(GuiButton guibutton) {
        if(guibutton.id<6){
            BlockLogic.setIN1(tile.getWorld(), tile.getPos(),tile.getWorld().getBlockState(tile.getPos()),EnumFacing.values()[guibutton.id]);
        }else if(guibutton.id<12){
            BlockLogic.setIN2(tile.getWorld(), tile.getPos(),tile.getWorld().getBlockState(tile.getPos()),EnumFacing.values()[guibutton.id-6]);
        }else if(guibutton.id<18){
            BlockLogic.setOUT(tile.getWorld(), tile.getPos(),tile.getWorld().getBlockState(tile.getPos()),EnumFacing.values()[guibutton.id-12]);
        }else if(guibutton.id<22){
            tile.setLOGIC(tile.logicToByte(tile.getLOGIC())^(1<<(guibutton.id-18)));
        }

        refreshButton();

        BlockPos p=tile.getPos();
        PacketHandler.INSTANCE.sendToServer(new MessageLogic(p.getX() , p.getY(), p.getZ(),tile.getIN(),tile.getIN1(),tile.getIN2(),tile.getOUT(),tile.getLOGIC()));
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseZ);
        this.drawString(fontRenderer, "↓ IN1 ↓", 50, -20, 0xFFFFFF);
        this.drawString(fontRenderer, "↓ IN2 ↓", 50,  40, 0xFFFFFF);
        this.drawString(fontRenderer, "↓ OUT ↓", 50, 100, 0xFFFFFF);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // TODO 自動生成されたメソッド・スタブ
        super.drawDefaultBackground();
        //this.mc.renderEngine.bindTexture(TEXTURE);
        //this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
    }


}
