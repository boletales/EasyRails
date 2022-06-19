package com.momiza.easyrails.utilblocks;

import com.momiza.easyrails.EasyRails;
import com.momiza.easyrails.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public class GuiContainerMinecartConverter extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EasyRails.MOD_ID,"textures/gui/gui.png");
    private TileEntityMinecartConverter tile;
    int convertTo;
    public GuiContainerMinecartConverter(Container inventorySlotsIn,TileEntity t) {
        super(inventorySlotsIn);
        tile=(TileEntityMinecartConverter)t;
        convertTo=tile.convertTo;
    }

    @Override
    public void initGui() {
        super.initGui();
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                int color = y*4+x;
                this.buttonList.add(new GuiButton(color, 50+x*100, 50+y*50, 40, 20, buttonStr(color)));
            }
        }

        refreshButton();
    }

    private String buttonStr(int color){
        String colorStr="§"+Integer.toHexString(color);
        return colorStr+"■ "+color+" ■ §r";
    }


    private void refreshButton(){
        for(GuiButton b:this.buttonList){
            b.enabled=true;
        }
        this.buttonList.get(tile.convertTo).enabled=false;
    }


    public void actionPerformed(GuiButton guibutton) {
        int color=guibutton.id;
        tile.convertTo = color;
        convertTo = color;

        refreshButton();

        BlockPos p=tile.getPos();
        PacketHandler.INSTANCE.sendToServer(new MessageMinecartConverter(p.getX() , p.getY(), p.getZ(),color));
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseZ);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // TODO 自動生成されたメソッド・スタブ
        super.drawDefaultBackground();
        //this.mc.renderEngine.bindTexture(TEXTURE);
        //this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
    }


}
